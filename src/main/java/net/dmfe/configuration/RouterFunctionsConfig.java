package net.dmfe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;

@Configuration
public class RouterFunctionsConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route().GET("/api/v4/greeting", request -> {
            var userDetails = request.principal()
                    .map(Authentication.class::cast)
                    .map(Authentication::getPrincipal)
                    .map(UserDetails.class::cast).orElseThrow();

            return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("greeting", "(V4) Hello, %s!".formatted(userDetails.getUsername())));
        }).build();
    }

}
