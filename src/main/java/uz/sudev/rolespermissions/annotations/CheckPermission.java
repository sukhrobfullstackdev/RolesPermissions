package uz.sudev.rolespermissions.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD) // qayerda? metod ustida
@Retention(RetentionPolicy.RUNTIME) // qachon? run bo'ganda
public @interface CheckPermission {
    String value();
}
