/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Anotation for validation.
 *
 * @author Tomas Svoboda
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AfterDateValidator.class)
@Documented
public @interface AfterDate
{
    String message() default "{cz.muni.fi.pa165.carpark.validation.AfterDateConstraint}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String fromFieldName();
    String toFieldName();
}
