package com.jhj.home.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import com.jhj.home.repository.UserRepository;

@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

    SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByUsername(username)
				.map(user -> User.withUsername(user.getUsername()).password(user.getPassword()).roles(user.getRoll()).build())
				.orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("").permitAll()
					.anyRequest().authenticated()
					)
				.formLogin(form -> form
						.loginProcessingUrl("/api/auth/login")
						.defaultSuccessUrl("/api/auth/apicheck", true)
						.permitAll()
						)
				.logout(logout -> logout
						.logoutUrl("/api/auth/logout")
                        .permitAll()
                        )
				.cors(cors -> cors.configurationSource(requset -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowCredentials(true);
					config.setAllowedOrigins(List.of("http://localhost:3000","http://cloudfront-s3-bucket-jhj.s3-website.ap-northeast-2.amazonaws.com"));
					config.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
					config.setAllowedHeaders(List.of("*"));
					return config;
					}
						 ));
		
		return http.build();
	}
	

}
