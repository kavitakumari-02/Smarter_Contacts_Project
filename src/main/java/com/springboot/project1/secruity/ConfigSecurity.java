package com.springboot.project1.secruity;

import java.beans.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {

    private final UserDetailsServiceImp userDetailsServiceImp;
    private final CustomDefaultOAuth2UserService customDefaultOAuth2UserService;
    public ConfigSecurity(UserDetailsServiceImp userDetailsServiceImp,CustomDefaultOAuth2UserService customDefaultOAuth2UserService) {
        this.userDetailsServiceImp = userDetailsServiceImp;
		this.customDefaultOAuth2UserService = customDefaultOAuth2UserService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsServiceImp);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
    
    @Bean
    public OAuth2AuthorizationRequestResolver authorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository) {

        DefaultOAuth2AuthorizationRequestResolver resolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository,
                        "/oauth2/authorization"
                );

        resolver.setAuthorizationRequestCustomizer(builder ->
            builder.additionalParameters(params ->
                params.put("prompt", "select_account")
            )
        );

        return resolver;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,OAuth2AuthorizationRequestResolver authorizationRequestResolver) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/register","/register_handler","/home","/css/**","/js/**").permitAll()
                    .requestMatchers("/user/**").authenticated()
                    .anyRequest().authenticated()) 
            .formLogin(login -> login  .loginPage("/login") 
				  .loginProcessingUrl("/do_login")  .defaultSuccessUrl("/user/dashboardPage",true)  .permitAll() )
				   
            .oauth2Login(oauth -> oauth
                    .loginPage("/login")
                    .userInfoEndpoint(userInfo -> userInfo
                    		.userService(customDefaultOAuth2UserService))
                    .authorizationEndpoint(endpoint -> endpoint
                        .authorizationRequestResolver(
                            authorizationRequestResolver
                        )
                    )
                    .defaultSuccessUrl("/user/dashboardPage", true)
                    .permitAll()
                )
           
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            
            );

        return http.build();
    }
}