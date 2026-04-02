package cz.kavka.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


@Configuration
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig {


    public static AuthenticationFailureHandler failureHandler() {
        var handler =  new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl("/login?error");
        return handler;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        return http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth    .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().permitAll())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/admin")
                        .failureHandler(failureHandler())
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/login")
                        //consider change
                        .invalidateHttpSession(false)
                        .deleteCookies("JSESSIONID").permitAll())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
