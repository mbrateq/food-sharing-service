package pl.sggw.foodsharingservice.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl
        implements UserDetails
{

  private final String userName;
  private final String password;
  private final boolean active;
  private final boolean toDelete;
  private List<GrantedAuthority> authorities;

  public UserDetailsImpl(User user) {
    this.userName = user.getUsername();
    this.password = user.getPassword();
    this.active = user.isEnabled();
    this.toDelete = user.isToDelete();
    this.authorities =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
            .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
    return active;
  }
}
