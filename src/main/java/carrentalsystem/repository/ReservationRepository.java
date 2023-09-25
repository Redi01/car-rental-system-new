package carrentalsystem.repository;

import carrentalsystem.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByStartDateGreaterThanAndStartDateLessThan(LocalDateTime startDate, LocalDateTime endDate);

    List<Reservation> findByEndDateGreaterThanAndEndDateLessThan(LocalDateTime startDate, LocalDateTime endDate);
}
