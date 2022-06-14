package pl.sggw.foodsharingservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_ROLES", uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID","ROLE_ID"}))
@Entity
public class UserRole implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ROLES_ID")
  private Long userRolesId;

  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "ROLE_ID")
  private Long roleId;
}
