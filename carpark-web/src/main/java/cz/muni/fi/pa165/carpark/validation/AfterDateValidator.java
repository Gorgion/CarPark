/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.validation;

import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * Validator that checks if from date is before to date.
 *
 * @author Tomas Svoboda
 */
@Component
public class AfterDateValidator implements ConstraintValidator<AfterDate, Object>
{

    private String fromFieldName;
    private String toFieldName;

    @Override
    public void initialize(final AfterDate constraintAnnotation)
    {
        fromFieldName = constraintAnnotation.fromFieldName();
        toFieldName = constraintAnnotation.toFieldName();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext ctx)
    {
        try
        {
            final Date from = (Date) ValidationUtil.getFieldValue(value, fromFieldName);
            final Date to = (Date) ValidationUtil.getFieldValue(value, toFieldName);

            boolean isValid = from.before(to);

            if (!isValid)
            {
                ValidationUtil.addValidationError(fromFieldName, ctx);
                ValidationUtil.addValidationError(toFieldName, ctx);
            }
            return isValid;
        } catch (final NoSuchFieldException | IllegalAccessException ignore)
        {
        } catch (Exception ignore)
        {
        }

        return true;
    }
}
