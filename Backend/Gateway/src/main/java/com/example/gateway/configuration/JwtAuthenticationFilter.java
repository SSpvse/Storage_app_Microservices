/*package com.example.gateway.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final String AUTH_HEADER = "Authorization";
    private static final String USER_ID_HEADER = "X-User-ID";


    private static final PathPatternParser pathPatternParser = new PathPatternParser();
    private static final PathPattern signupPathPattern = pathPatternParser.parse("/auth/signup");
    private static final PathPattern loginPathPattern = pathPatternParser.parse("/auth/login");

    private static final PathPattern actuatorPathPattern = pathPatternParser.parse("/actuator/**");



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("----- JwtAuthenticationFilter triggered for: " + exchange.getRequest().getPath());
        ServerHttpRequest request = exchange.getRequest();

        System.out.println("------ Incoming Request Headers: " + request.getHeaders());
        System.out.println(" --- -- Incoming request path: " + request.getPath());


        // Bypass authentication for `/auth/signup` and `/auth/login` paths
        if (signupPathPattern.matches(request.getPath().pathWithinApplication()) ||
                loginPathPattern.matches(request.getPath().pathWithinApplication())
                || actuatorPathPattern.matches(request.getPath().pathWithinApplication())
        ) { // Add this line
            System.out.println("FORWARDING CALL WITHOUT AUTHORIZATION");
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(AUTH_HEADER);
        System.out.println("---- Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Reject requests without a valid Authorization header
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extract JWT token and validate it
        String jwt = authHeader.substring(7);
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

            String userId = claims.getSubject(); // Use `subject` claim as user ID

            System.out.println("---- userId: " + userId);
            // Inject `userId` into the request headers for downstream services
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header(USER_ID_HEADER, userId)
                    .build();
            exchange = exchange.mutate().request(modifiedRequest).build();
        } catch (Exception e) {
            System.out.println("---- ERROR: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Allow the request through to downstream services
        return chain.filter(exchange);
    }
}


 */