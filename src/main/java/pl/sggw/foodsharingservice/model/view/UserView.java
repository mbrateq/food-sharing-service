package pl.sggw.foodsharingservice.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserView {

  private long userId;
  private String username;
  private String phone;
  private boolean enabled;
  private boolean toDelete;
  private Set<RoleView> roles;
}
