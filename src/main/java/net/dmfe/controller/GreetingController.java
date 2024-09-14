package net.dmfe.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GreetingController {

    @GetMapping("/v1/greeting")
    public ResponseEntity<Map<String, String>> handleGreetingUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("greeting", "Hello, %s!".formatted(userDetails.getUsername())));
    }

    @GetMapping("/v2/greeting")
    public ResponseEntity<Map<String, String>> handleGreetingUserV2(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) ((Authentication) request.getUserPrincipal()).getPrincipal();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("greeting", "Hello, %s!".formatted(userDetails.getUsername())));
    }

    @GetMapping("/v3/greeting")
    public ResponseEntity<Map<String, String>> handleGreetingUserV3(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("greeting", "Hello, %s!".formatted(userDetails.getUsername())));
    }

}
