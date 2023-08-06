package cinema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/cinema-halls",
                        "/movies",
                        "/movie-sessions"
                        ).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/movie-sessions/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/movie-sessions/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,
                        "/cinema-halls",
                        "/movies",
                        "/movie-sessions/available"
                        ).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET,
                        "/orders",
                        "/shopping-carts/by-user",
                        "/users/by-email").hasRole("USER")
                .antMatchers(HttpMethod.POST,
                        "/orders/complete").hasRole("USER")
                .antMatchers(HttpMethod.PUT,
                        "/shopping-carts/movie-sessions").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
