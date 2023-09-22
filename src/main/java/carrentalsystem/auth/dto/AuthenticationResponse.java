package carrentalsystem.auth.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    @Value("${app.security.jwt.expiration}")
    private long expirationTime;
}
