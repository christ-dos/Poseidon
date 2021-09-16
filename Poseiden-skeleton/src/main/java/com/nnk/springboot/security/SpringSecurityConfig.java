package com.nnk.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * A bean that permit crypt the password before recording in the database
     *
     * @return A String containing the password encoded
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Method configure to determine the authentication provider
     * here provide from the database
     *
     * @param auth An {@link AuthenticationManagerBuilder} that create the authentication
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("user123")).authorities("ROLE_USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin123")).authorities("ROLE_ADMIN");
//        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Method that configure the security chain for requests HTTP
     *
     * @param http A incoming request
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/", "/app/error","/css/*").permitAll()
                .antMatchers("/user/list").hasRole("ADMIN")
                .antMatchers("/bidList/list").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/app/error")
                .and()
                .formLogin()
                .defaultSuccessUrl("/app/default",true)
//                .failureUrl("/app/error")
                .and()
                .logout()
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/")
//                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
        ;


    }
}
