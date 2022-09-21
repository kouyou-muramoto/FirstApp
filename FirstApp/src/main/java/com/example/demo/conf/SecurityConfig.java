package com.example.demo.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.app.DatabaseUserDetailsService;



@Configuration
public class SecurityConfig {
	@Autowired
	private DatabaseUserDetailsService userDetailsService;
	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 throws Exception {
	 // ここに設定を書く（後述）
		 http.formLogin(login -> login
				 .loginProcessingUrl("/login")
				 .loginPage("/login")
				 .defaultSuccessUrl("/List")
				 .failureUrl("/login?error")
				 .permitAll()
		 ).logout(logout -> logout
				 .logoutUrl("/user/logout")
				 .logoutSuccessUrl("/login")
				 .invalidateHttpSession(true)
				 .deleteCookies("JSESSIONID")
		 ).authorizeHttpRequests(authz -> authz
				 .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
						 .permitAll()
				 .mvcMatchers("/List")
				 		 .permitAll()
		 		.mvcMatchers("/signup")
				 		.permitAll()
		 		.mvcMatchers("/registration")
				 		.permitAll()
				 		.anyRequest()
				 		.authenticated()
		 );
	 return http.build();
	 }
}

