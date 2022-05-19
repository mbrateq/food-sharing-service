package pl.sggw.foodsharingservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "USERS"
    //        , indexes = @Index(name = "CTD", columnList = "USERNAME")
    )
@Entity
public class User implements Serializable {
  // extends RepresentationModel<User>
  @Id
  //  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  //  @SequenceGenerator(name = "user_generator", sequenceName = "users_user_id_seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "USERNAME", unique = true)
  private String username;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "ENABLED", columnDefinition = "boolean default false")
  private boolean enabled;

  @Column(name = "TO_DELETE", columnDefinition = "boolean default false")
  @Builder.Default private boolean toDelete = false;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
  @JoinTable(
      name = "USERS_ROLES",
      joinColumns = @JoinColumn(name = "USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
  private Set<Role> roles = new HashSet<>();
}
