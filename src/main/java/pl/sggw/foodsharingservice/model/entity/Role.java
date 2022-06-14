package pl.sggw.foodsharingservice.model.entity;

import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="ROLES")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Role implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
//    @SequenceGenerator(name = "role_generator", sequenceName = "roles_role_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private long roleId;

    @Column(name = "ROLE_NAME")
    private String roleName;
}
