package uz.sudev.rolespermissions.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class UserDto {
    @NotNull(message = "Please enter user's first name!")
    @Size(min = 3)
    private String firstName;
    @NotNull(message = "Please enter user's last name!")
    @Size(min = 5)
    private String lastName;
    @NotNull(message = "Please enter user's active email!")
    @Email
    private String email;
    @NotNull(message = "Please enter user's password!")
    @Size(min = 8)
    private String password;
    @NotNull(message = "Please select user's role!")
    private Long roleId;
}
