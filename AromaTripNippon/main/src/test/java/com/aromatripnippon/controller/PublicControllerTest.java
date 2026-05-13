package com.aromatripnippon.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.ReservationRepository;
import com.aromatripnippon.service.ReservationService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PublicController.class)
@AutoConfigureMockMvc(addFilters = false)
class PublicControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ExperienceProgramRepository programs;
  @MockBean
  private ReservationRepository reservations;
  @MockBean
  private ReservationService reservationService;

  @Test
  void postReservation_redirectsToCompleteWhenValid() throws Exception {
    Reservation saved = new Reservation();
    saved.setId(123L);
    when(programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById()).thenReturn(Optional.empty());
    when(reservationService.createReservation(any(), any())).thenReturn(saved);

    mockMvc.perform(post("/reservation")
            .param("visitDate", LocalDate.now().plusDays(1).toString())
            .param("timeSlot", "13:00")
            .param("guestCount", "2")
            .param("name", "Alice")
            .param("email", "alice@example.com")
            .param("preferredLanguage", "English")
            .param("requestNote", "test"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("/reservation/complete/*"));
  }

  @Test
  void postReservation_returnsFormWhenVisitDateIsPast() throws Exception {
    when(programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById()).thenReturn(Optional.empty());

    mockMvc.perform(post("/reservation")
            .param("visitDate", LocalDate.now().minusDays(1).toString())
            .param("timeSlot", "13:00")
            .param("guestCount", "2")
            .param("name", "Alice")
            .param("email", "alice@example.com")
            .param("preferredLanguage", "English")
            .param("requestNote", "test"))
        .andExpect(status().isOk())
        .andExpect(view().name("public/reservation"))
        .andExpect(model().attributeHasFieldErrors("reservationRequest", "visitDate"));

    verifyNoInteractions(reservationService);
  }
}
