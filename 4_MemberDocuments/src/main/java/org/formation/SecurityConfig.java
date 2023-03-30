package org.formation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.csrf().disable().authorizeHttpRequests()
				.antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**","/actuator/**").permitAll()
				.antMatchers(HttpMethod.GET, "/members/**").hasAnyRole("USER","ADMIN")
				.antMatchers("/members/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().and().logout().and().build();
	}

//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.withUsername("user").authorities("ROLE_USER").password("{noop}secret").build();
//		UserDetails admin = User.withUsername("admin").authorities("ROLE_ADMIN").password("{noop}secret").build();
//
//		return new InMemoryUserDetailsManager(user, admin);
//	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/**/*.css", "/**/*.js");
	}
	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	@Bean
//	UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
//	
////	@Bean
////	PasswordEncoder passwordEncoder() {
////		return new BCryptPasswordEncoder();
////	}
}
