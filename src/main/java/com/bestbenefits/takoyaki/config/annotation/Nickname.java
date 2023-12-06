package com.bestbenefits.takoyaki.config.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@NotBlank(message = "공백은 허용되지 않습니다.")
@Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "숫자, 영어, 한글만 허용됩니다.")
@Constraint(validatedBy = {})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nickname {
    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}