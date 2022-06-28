package uz.sudev.rolespermissions.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class PostDto {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotBlank
    private String url;
}
