package com.steveq.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

/**
 * Created by Adam on 2017-05-16.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PastLocalDate {
    String message() default "{javax.validation.constraints.Past.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    class PastValidator implements ConstraintValidator<PastLocalDate, LocalDate> {
        public void initialize(PastLocalDate past){

        }
        public boolean isValid(LocalDate localDate, ConstraintValidatorContext context){
            return localDate == null || localDate.isBefore(LocalDate.now());
        }
    }
}
