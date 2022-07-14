package uz.sudev.rolespermissions.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.sudev.rolespermissions.entity.enums.PermissionName;
import uz.sudev.rolespermissions.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbstractEntity{
    @Column(nullable = false,unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY) // ENUM - bo'lgani uchun @ManyToMany qo'ya olmadik, o'rniga @ElementCollection qo'ydik!
    private List<PermissionName> permissions;
    @Column(columnDefinition = "text")
    private String description;
}
