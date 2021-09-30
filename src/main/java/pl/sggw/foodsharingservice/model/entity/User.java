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
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS", indexes = @Index(name = "CTD", columnList = "USERNAME"))
@Entity
public class User implements Serializable {
  // extends RepresentationModel<User>
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "SURNAME")
  private String surname;

  @Column(name = "USERNAME", unique = true)
  private String username;

  @Column(name = "EMAIL", unique = true)
  private String email;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "DATE_OF_BIRTH")
  private LocalDateTime dateOfBirth;

  @Column(name = "ENABLED")
  private boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
  @JoinTable(
      name = "USERS_ROLES",
      joinColumns = @JoinColumn(name = "USERNAME"),
      inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
  private List<Role> roles = new LinkedList<>();
}
