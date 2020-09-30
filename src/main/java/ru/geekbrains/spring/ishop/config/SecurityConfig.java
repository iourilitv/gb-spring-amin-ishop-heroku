package ru.geekbrains.spring.ishop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.spring.ishop.service.interfaces.IUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final IUserService userService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    //TODO не работает ограничение в admin
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Added to fix the "status": 403, "error": "Forbidden" problem for PUT, POST etc. requests(REST). It helped!
        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/admin/**").hasRole("EMPLOYEE")
                .antMatchers("/admin/user/**").hasRole("ADMIN")
                .antMatchers("/admin/user/reset/password/1/**").hasRole("SUPERADMIN")
                .antMatchers("/admin/user/edit/1/**").hasRole("SUPERADMIN")
                .antMatchers("/admin/user/1/**").hasRole("SUPERADMIN")
                .antMatchers("/admin/user/delete/1/**").hasRole("SUPERADMIN")
                .antMatchers("/profile/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

}
