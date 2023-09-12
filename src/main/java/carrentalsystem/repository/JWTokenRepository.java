package carrentalsystem.repository;

import carrentalsystem.entities.JWToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface JWTokenRepository  extends JpaRepository<JWToken, Integer> {
    void deleteByExpirationDateBefore(Date currentDate);

}
