package com.project.test.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= {UniqueEmailAnnotation.class})
public @interface ValidateEmailAnnotation {

	public String message() default "Unique emial required";
	
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
}
