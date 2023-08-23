package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Integer reviewID;

//    private User user;

    private Integer rating;
    private String description;
    private LocalDateTime datePosted;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
