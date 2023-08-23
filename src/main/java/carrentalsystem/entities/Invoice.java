package carrentalsystem.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue
    private Integer invoiceId;
    private Long invoiceNumber;
    private String customerName;
    private String customerEmail;
    private Integer reservationId;
    private Long totalAmount;
    private LocalDateTime issueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
