package uz.sudev.rolespermissions.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import uz.sudev.rolespermissions.entity.enums.PermissionName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class RoleDto {
    @NotBlank
    private String name;
    private String description;
    @NotEmpty
    private List<PermissionName> permissionIds;
}
