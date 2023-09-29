package carrentalsystem.repository;

import carrentalsystem.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findByReservationId(Integer id);

    /**
     * This method finds reservations in the database with conflicting end dates.
     *
     * @param startDate The start date of the date range to check for conflicts.
     * @param endDate   The end date of the date range to check for conflicts.
     * @return A list of reservations with end dates that conflict with the provided date range.
     */
    @Query(value = "select * from reservations r where r.end_date >= ? and r.end_date <= ?", nativeQuery = true)
    List<Reservation> findConflictingEndDates(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * This method finds reservations in the database with conflicting start dates.
     *
     * @param startDate The start date of the date range to check for conflicts.
     * @param endDate   The end date of the date range to check for conflicts.
     * @return A list of reservations with start dates that conflict with the provided date range.
     */
    @Query(value = "select r from Reservation r where r.startDate >= ?1 and r.startDate <= ?2")
    List<Reservation> findConflictingStartDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r FROM Reservation r WHERE r.vehicle.vehicleId = :vehicleId AND ((r.startDate >= :startDate AND r.startDate <= :endDate) OR (r.endDate >= :startDate AND r.endDate <= :endDate))")
    List<Reservation> findConflictingReservations(
            @Param("vehicleId") Integer vehicleId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    //    Same with findConflictingStartDates but misses the '=' operator
    List<Reservation> findByStartDateGreaterThanAndStartDateLessThan(LocalDateTime startDate, LocalDateTime endDate);
}
