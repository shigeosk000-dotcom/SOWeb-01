package com.aromatripnippon.service;

import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.ExperienceProgram;
import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.repository.CustomerRepository;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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

  @Transactional
  public Reservation createReservation(Reservation reservation, Customer customer) {
    Customer savedCustomer = customers.save(customer);
    ExperienceProgram program = programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById()
        .orElseThrow(() -> new IllegalStateException("Active experience program is not configured."));
    reservation.setCustomer(savedCustomer);
    reservation.setExperienceProgram(program);
    reservation.setStatus("RESERVED");
    return reservations.save(reservation);
  }
}
