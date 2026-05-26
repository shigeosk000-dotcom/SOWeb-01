package com.aromatripnippon.service;

import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.ExperienceProgram;
import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.repository.CustomerRepository;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.ReservationRepository;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ReservationService {
  private final CustomerRepository customers;
  private final ExperienceProgramRepository programs;
  private final ReservationRepository reservations;

  public ReservationService(CustomerRepository customers, ExperienceProgramRepository programs,
      ReservationRepository reservations) {
    this.customers = customers;
    this.programs = programs;
    this.reservations = reservations;
  }

  public List<Reservation> findActiveReservations() {
    return reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc();
  }

  public Reservation findActive(Long id) {
    return reservations.findByIdAndDeletedAtIsNull(id).orElseThrow();
  }

  @Transactional
  public Reservation createReservation(Reservation reservation, @Valid Customer customer) {
    LocalDate today = LocalDate.now();
    LocalDate maxDate = today.plusMonths(3);
    if (reservation.getVisitDate() == null || reservation.getVisitDate().isBefore(today)) {
      throw new IllegalArgumentException("\u4E88\u7D04\u65E5\u306F\u672C\u65E5\u4EE5\u964D\u3092\u9078\u629E\u3057\u3066\u304F\u3060\u3055\u3044\u3002");
    }
    if (reservation.getVisitDate().isAfter(maxDate)) {
      throw new IllegalArgumentException("\u4E88\u7D04\u65E5\u306F\u672C\u65E5\u304B\u30893\u304B\u6708\u5148\u307E\u3067\u9078\u629E\u3067\u304D\u307E\u3059\u3002");
    }

    Customer savedCustomer = customers.save(customer);
    ExperienceProgram program = programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById()
        .orElseThrow(() -> new IllegalStateException("Active experience program is not configured."));
    reservation.setCustomer(savedCustomer);
    reservation.setExperienceProgram(program);
    reservation.setStatus("RESERVED");
    return reservations.save(reservation);
  }

  @Transactional
  public Reservation save(@Valid Reservation reservation) {
    return reservations.save(reservation);
  }

  @Transactional
  public void softDelete(Long id) {
    Reservation reservation = findActive(id);
    reservation.softDelete();
    reservations.save(reservation);
  }
}
