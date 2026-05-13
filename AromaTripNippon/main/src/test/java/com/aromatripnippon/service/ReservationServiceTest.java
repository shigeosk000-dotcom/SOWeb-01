package com.aromatripnippon.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.ExperienceProgram;
import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.repository.CustomerRepository;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.ReservationRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(ReservationService.class)
class ReservationServiceTest {
  @Autowired
  private ReservationService reservationService;
  @Autowired
  private ExperienceProgramRepository programs;
  @Autowired
  private ReservationRepository reservations;
  @Autowired
  private CustomerRepository customers;

  @Test
  void createReservation_savesReservationAndCustomerRelation() {
    ExperienceProgram program = new ExperienceProgram();
    program.setName("Program A");
    program.setDescription("desc");
    program.setDurationMinutes(90);
    program.setPrice(new BigDecimal("8800"));
    program.setMaterialsSummary("mat");
    program.setActive(true);
    programs.save(program);

    Customer customer = new Customer();
    customer.setName("Alice");
    customer.setEmail("alice@example.com");
    customer.setPreferredLanguage("English");

    Reservation reservation = new Reservation();
    reservation.setVisitDate(LocalDate.now().plusDays(1));
    reservation.setTimeSlot("13:00");
    reservation.setGuestCount(2);
    reservation.setPreferredLanguage("English");
    reservation.setRequestNote("window seat");

    Reservation saved = reservationService.createReservation(reservation, customer);

    assertThat(saved.getId()).isNotNull();
    assertThat(saved.getCustomer()).isNotNull();
    assertThat(saved.getCustomer().getId()).isNotNull();
    assertThat(saved.getExperienceProgram().getId()).isEqualTo(program.getId());
    assertThat(saved.getStatus()).isEqualTo("RESERVED");
    assertThat(reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc()).hasSize(1);
    assertThat(customers.findByDeletedAtIsNullOrderByIdDesc()).hasSize(1);
  }
}
