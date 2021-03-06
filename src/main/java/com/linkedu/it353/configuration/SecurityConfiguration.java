package com.linkedu.it353.configuration;

/**
 * Created by Kaiser Ahmed on 3/8/2017.
 */
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/registration-student", "/verifyStudent/**").permitAll()
                	.antMatchers("/registration-recruiter", "/verifyRecruiter/**").permitAll()
                	.antMatchers("/forget-password", "/resetPassword/**", "/reset-password").permitAll()
                .antMatchers("/contact-us", "/about-us", "/terms", "/policies").permitAll()
                .antMatchers("/upload", "/file/**", "/image/**").hasAnyAuthority("STUDENT","RECRUITER")
                    .antMatchers("/student/**").hasAuthority("STUDENT")
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/recruiter/**").hasAuthority("RECRUITER")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                    .defaultSuccessUrl("/default")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/").and().exceptionHandling()
                    .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/assets/**","/Image/**", "/css/**", "/js/**", "/images/**");
    }

}