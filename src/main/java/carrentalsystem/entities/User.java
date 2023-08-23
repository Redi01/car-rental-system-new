package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Integer userId;
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private Integer phone;
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "rentedBy")
    private List<Vehicle> rentedVehicles = new ArrayList<>();

    @OneToMany(mappedBy = "user" )
    private List<Review> reviews = new ArrayList<>();
//    @Enumerated(EnumType.STRING)
//    private UserRole role;


}
