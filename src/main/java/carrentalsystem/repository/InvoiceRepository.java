package carrentalsystem.repository;

import carrentalsystem.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
//    Optional<Invoice> getInvoicesByVe
}
