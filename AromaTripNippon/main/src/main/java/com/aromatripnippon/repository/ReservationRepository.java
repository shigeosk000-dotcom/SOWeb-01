package com.aromatripnippon.repository;

import com.aromatripnippon.entity.Reservation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  List<Reservation> findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc();
  List<Reservation> findByDeletedAtIsNullAndVisitDateOrderByTimeSlotAsc(LocalDate visitDate);
}
