package com.yuen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import com.yuen.service.FacebookConnectionSignUp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// Web Security
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
	
	
	// Facebook Security
	
	@Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;
 
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
 
    @Autowired
    private FacebookConnectionSignUp facebookConnectionSignUp;
	
    @Bean
    public ProviderSignInController providerSignInController() {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
          .setConnectionSignUp(facebookConnectionSignUp);
         
        return new ProviderSignInController(
        		connectionFactoryLocator, 
        		usersConnectionRepository, 
        		new FacebookSignInAdapter(userDetailsService));
    }
    
    
    // Authentication and Authorization
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        		.antMatchers("/login", "/register", "/signin/**", "/signup/**").permitAll()
        		.antMatchers(
        				"/", "/newsfeed/more", 
        				"/search",
        				"/user/**",  
        				"/upload/**", 
        				"/post/**", 
        				"/comment", 
        				"/like", "/unlike", 
        				"/follow/**", "/unfollow/**")
        			.authenticated()
        		.antMatchers("/admin/**")
        			.hasRole("ADMIN")
        		.and()
    		.formLogin()
            	.loginPage("/login")
            	.usernameParameter("email")
            	.passwordParameter("password")
            	.defaultSuccessUrl("/")
            	.failureUrl("/login?error")
            	.and()
       		.csrf()
       			.disable();
    } 
 
}
