package mate.academy.cinema.service.impl;

import static org.springframework.security.core.userdetails.User.withUsername;

import mate.academy.cinema.model.User;
import mate.academy.cinema.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Can't find user by email: " + email));
        UserBuilder userBuilder = withUsername(email);
        userBuilder.password(user.getPassword());
        userBuilder.authorities(user.getRoles()
                .stream().map(role -> role.getRoleName().name()).toArray(String[]::new));
        return userBuilder.build();
    }
}
