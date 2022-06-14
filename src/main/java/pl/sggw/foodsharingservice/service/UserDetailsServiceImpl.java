package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.repository.UserRepository;

@Service
@RequiredArgsConstructor
class UserDetailsServiceImpl
        implements UserDetailsService
{

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsernameAndToDeleteFalse(username)
        .map(UserDetailsImpl::new)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
  }
}
