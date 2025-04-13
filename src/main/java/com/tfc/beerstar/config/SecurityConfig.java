package com.tfc.beerstar.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {

    /*
    private final CorsConfigurationSource corsConfigurationSource;

    SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configurando seguridad HTTP");     
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/beerstar/**").permitAll()
                .anyRequest().permitAll()
                //.requestMatchers("/beerstar/proveedores/**").authenticated() // Para autenticar usuarios con login
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            log.info("Configuración de seguridad HTTP completada");
        return http.build();
    }

    
    // Configuración de CORS

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Configurando CORS");

        CorsConfiguration configuration = new CorsConfiguration();
    
        // ⚠️ ¡Esto es solo para desarrollo!
        String localhostFrontend = "http://localhost:4200";
        String railwayFrontend = "https://tu-frontend-en-railway.app"; // <-- reemplaza esto si tienes frontend
    
        configuration.setAllowedOriginPatterns(Arrays.asList(
            localhostFrontend,
            railwayFrontend,
            "https://*.railway.app" // permite subdominios si es necesario
        ));
    
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
    
        log.debug("CORS configurado para: {}, {}", localhostFrontend, railwayFrontend);
        return source;
    }

     /* 
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
*/
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
