package uz.sudev.rolespermissions.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.sudev.rolespermissions.entity.enums.PermissionName;
import uz.sudev.rolespermissions.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbstractEntity implements UserDetails {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Role role;
    private boolean enabled;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (PermissionName permission : this.role.getPermissions()) {
            grantedAuthorities.add((GrantedAuthority) permission::name);
        }
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (PermissionName permission : this.role.getPermissions()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(permission.name()));
//        } ikkinchi yo'li
        return grantedAuthorities;
    }

    public User(String firstName, String lastName, String username, String password, Role role, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
}
