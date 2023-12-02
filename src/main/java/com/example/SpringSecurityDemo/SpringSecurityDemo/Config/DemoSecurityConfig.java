package com.example.SpringSecurityDemo.SpringSecurityDemo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class DemoSecurityConfig {

//    @Bean
//    public UserDetailsManager configure(){
//        UserDetails users = User.withDefaultPasswordEncoder()
//                .username("john").password("test1234").roles("EMPLOYEE")
//                .username("mary").password("test1").roles("MANAGER")
//                .username("susan").password("test12").roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(users);
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/").permitAll().anyRequest().authenticated()).httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults());
//        return http.build();
//    }



    @Bean
    public PasswordEncoder encoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user1 = User.builder().username("samaan").password(encoder().encode("samaan")).roles("ADMIN","EMPLOYEE").build();
        UserDetails john = User.builder()
                .username("john")
                .password(encoder().encode("test123"))
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password(encoder().encode("test123"))
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password(encoder().encode("test123"))
                .roles("EMPLOYEE", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1,john,mary,susan);
    }
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http.authorizeHttpRequests(configurer -> configurer
//                        .requestMatchers("/").permitAll()
                         .requestMatchers("/leaders/**").hasRole("MANAGER")
                         .requestMatchers("/systems/**").hasRole("ADMIN").anyRequest().authenticated())

                 .formLogin(configurer -> configurer
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser").permitAll())
                .logout((logout) -> logout.logoutSuccessUrl("/showMyLoginPage?logout").permitAll())
                .exceptionHandling((exceptions) -> exceptions.accessDeniedPage("/access-denied"))
        .build();
    }
}
