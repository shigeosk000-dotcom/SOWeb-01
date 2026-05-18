package com.aromatripnippon.repository;

import com.aromatripnippon.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  Optional<Reservation> findByIdAndDeletedAtIsNull(Long id);
  @EntityGraph(attributePaths = {"customer", "experienceProgram"})
  List<Reservation> findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc();
  List<Reservation> findByDeletedAtIsNullAndVisitDateOrderByTimeSlotAsc(LocalDate visitDate);
  @Query("select (count(r) > 0) from Reservation r where r.deletedAt is null and r.customer.id = :customerId")
  boolean existsActiveByCustomerId(@Param("customerId") Long customerId);
  @Modifying
  @Query("update Reservation r set r.deletedAt = :deletedAt where r.id = :id and r.deletedAt is null")
  int softDeleteById(@Param("id") Long id, @Param("deletedAt") LocalDateTime deletedAt);
}
