/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception handler.
 *
 * @author Tomas Svoboda
 */
@ControllerAdvice
public class GeneralExceptionAdviser
{
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Data access exception occured")
    @ExceptionHandler(value =
    {
        DataAccessException.class
    })
    public void serverErrorHandler(DataAccessException e)
    {
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Global exception occured")
    @ExceptionHandler(Exception.class)
    public void generalExceptionHandler(Exception e)
    {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex)
    {

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors)
    {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError : fieldErrors)
        {
            String localizedErrorMessage = fieldError.getDefaultMessage();
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }

    public static class ValidationErrorDTO
    {

        private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

        public ValidationErrorDTO()
        {

        }

        public void addFieldError(String path, String message)
        {
            FieldErrorDTO error = new FieldErrorDTO(path, message);
            fieldErrors.add(error);
        }

        public List<FieldErrorDTO> getFieldErrors()
        {
            return fieldErrors;
        }

    }

    public static class FieldErrorDTO
    {

        private String field;

        private String message;

        public FieldErrorDTO(String field, String message)
        {
            this.field = field;
            this.message = message;
        }

        public String getField()
        {
            return field;
        }

        public String getMessage()
        {
            return message;
        }

    }
}
