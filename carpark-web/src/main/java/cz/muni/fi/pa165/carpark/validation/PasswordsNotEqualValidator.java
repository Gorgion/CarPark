/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Passwords validation for equality.
 *
 * @author Tomas Svoboda
 */
public class PasswordsNotEqualValidator implements ConstraintValidator<PasswordsNotEqual, Object>
{
    private String passwordFieldName;
    private String passwordVerificationFieldName;

    @Override
    public void initialize(PasswordsNotEqual constraintAnnotation)
    {
        this.passwordFieldName = constraintAnnotation.passwordFieldName();
        this.passwordVerificationFieldName = constraintAnnotation.passwordVerificationFieldName();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context)
    {
        context.disableDefaultConstraintViolation();
        try
        {
            String password = (String) ValidationUtil.getFieldValue(value, passwordFieldName);
            String passwordVerification = (String) ValidationUtil.getFieldValue(value, passwordVerificationFieldName);

            if (passwordsAreNotEqual(password, passwordVerification))
            {
                ValidationUtil.addValidationError(passwordFieldName, context);
                ValidationUtil.addValidationError(passwordVerificationFieldName, context);

                return false;
            }
        } catch (Exception ex)
        {
            throw new RuntimeException("Exception occurred during validation", ex);
        }

        return true;
    }

    private boolean passwordsAreNotEqual(String password, String passwordVerification)
    {
        return !(password == null ? passwordVerification == null : password.equals(passwordVerification));
    }

}
