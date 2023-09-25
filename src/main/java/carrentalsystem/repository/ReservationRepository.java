package carrentalsystem.repository;

import carrentalsystem.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findByReservationId(Integer id);

    /**
     * TODO
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select * from reservations r where r.end_date >= ? and r.end_date <= ?", nativeQuery = true)
    List<Reservation> findConflictingEndDates(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * TODO
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select r from Reservation r where r.startDate >= ?1 and r.startDate <= ?2")
    List<Reservation> findConflictingStartDates(LocalDateTime startDate, LocalDateTime endDate);

    // Same with findConflictingStartDates but misses the '=' operator
    List<Reservation> findByStartDateGreaterThanAndStartDateLessThan(LocalDateTime startDate, LocalDateTime endDate);
}
