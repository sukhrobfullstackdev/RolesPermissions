package uz.sudev.rolespermissions.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.sudev.rolespermissions.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends AbstractEntity {
    @Column(nullable = false,columnDefinition = "text")
    private String title;
    @Column(nullable = false,columnDefinition = "text")
    private String text;
    @Column(nullable = false,columnDefinition = "text",unique = true)
    private String url;
}
