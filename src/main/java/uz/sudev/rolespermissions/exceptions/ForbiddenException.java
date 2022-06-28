package uz.sudev.rolespermissions.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.FORBIDDEN)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ForbiddenException extends RuntimeException {
    private String type;
    private String msg;

}
