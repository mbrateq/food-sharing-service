package pl.sggw.foodsharingservice.model.dto.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PasswordValid {
  String message() default "{error.address}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
