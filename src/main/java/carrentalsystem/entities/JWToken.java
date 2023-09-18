package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "jwtoken")
@Data
@NoArgsConstructor
public class JWToken {
    @Id
    @GeneratedValue
    private Integer tokenId;
    @Column(name = "expiration_date")
    private Date expirationDate;
    String token;
}
