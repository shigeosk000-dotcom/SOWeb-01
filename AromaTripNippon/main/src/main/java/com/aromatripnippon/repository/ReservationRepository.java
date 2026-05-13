package com.aromatripnippon.repository;

import com.aromatripnippon.entity.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  Optional<Reservation> findByIdAndDeletedAtIsNull(Long id);
  List<Reservation> findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc();
  List<Reservation> findByDeletedAtIsNullAndVisitDateOrderByTimeSlotAsc(LocalDate visitDate);
}
