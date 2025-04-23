package telran.java57.forum.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import telran.java57.forum.posts.dao.PostRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final String CONTROLLER_WITH_ADM_PERMISSIONS = "/account/user/{login}/role/{role}";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/account/register").permitAll()
                .requestMatchers("/forum/posts/**").permitAll()
                .requestMatchers(HttpMethod.DELETE,"account/user/{login}")
                .access(new WebExpressionAuthorizationManager("hasRole('ADMINISTRATOR') or authentication.name == #login"))
                .requestMatchers(HttpMethod.PUT, "/account/user/{login}")
                .access(new WebExpressionAuthorizationManager("authentication.name == #login"))
                .requestMatchers(HttpMethod.PUT, CONTROLLER_WITH_ADM_PERMISSIONS)
                .access(new WebExpressionAuthorizationManager("hasRole('ADMINISTRATOR')"))
                .requestMatchers(HttpMethod.DELETE, CONTROLLER_WITH_ADM_PERMISSIONS)
                .access(new WebExpressionAuthorizationManager("hasRole('ADMINISTRATOR')"))
                .requestMatchers(HttpMethod.POST, "/forum/post/{author}")
                .access(new WebExpressionAuthorizationManager("authentication.name == #author"))
                .requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
                .access(new WebExpressionAuthorizationManager("hasRole('MODERATOR') or @webExpressionMethod.isOwnerOfPost(authentication.name, #id)"))
                .requestMatchers(HttpMethod.PUT, "/forum/post/{id}")
                .access(new WebExpressionAuthorizationManager("@webExpressionMethod.isOwnerOfPost(authentication.name, #id)"))
                .anyRequest().authenticated());
        return http.build();
    }
}
