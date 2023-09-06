package carrentalsystem.auth;

import carrentalsystem.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRefresh {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Scheduled(fixedRate = 600000) // Run every 10 minutes
    public void RefreshToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newToken = jwtService.generateToken(userDetails);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, newToken, userDetails.getAuthorities()));
        }

    }
}