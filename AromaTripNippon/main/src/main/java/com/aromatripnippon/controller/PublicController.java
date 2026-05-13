package com.aromatripnippon.controller;

import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.form.ReservationRequest;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.ReservationRepository;
import com.aromatripnippon.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PublicController {
  private final ExperienceProgramRepository programs;
  private final ReservationRepository reservations;
  private final ReservationService reservationService;

  public PublicController(ExperienceProgramRepository programs, ReservationRepository reservations,
      ReservationService reservationService) {
    this.programs = programs;
    this.reservations = reservations;
    this.reservationService = reservationService;
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
    return "public/index";
  }

  @GetMapping("/experience")
  public String experience(Model model) {
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
    return "public/experience";
  }

  @GetMapping("/concept")
  public String concept() {
    return "public/concept";
  }

  @GetMapping("/reservation")
  public String reservation(Model model) {
    model.addAttribute("reservationRequest", new ReservationRequest());
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
    return "public/reservation";
  }

  @PostMapping("/reservation")
  public String createReservation(@Valid @ModelAttribute ReservationRequest reservationRequest, BindingResult errors,
      Model model, RedirectAttributes redirectAttributes) {
    if (errors.hasErrors()) {
      model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
      return "public/reservation";
    }
    Customer customer = new Customer();
    customer.setName(reservationRequest.getName());
    customer.setEmail(reservationRequest.getEmail());
    customer.setPreferredLanguage(reservationRequest.getPreferredLanguage());
    Reservation reservation = new Reservation();
    reservation.setVisitDate(reservationRequest.getVisitDate());
    reservation.setTimeSlot(reservationRequest.getTimeSlot());
    reservation.setGuestCount(reservationRequest.getGuestCount());
    reservation.setPreferredLanguage(reservationRequest.getPreferredLanguage());
    reservation.setRequestNote(reservationRequest.getRequestNote());
    Reservation saved;
    try {
      saved = reservationService.createReservation(reservation, customer);
    } catch (IllegalArgumentException ex) {
      model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
      model.addAttribute("errorMessage", ex.getMessage());
      return "public/reservation";
    }
    redirectAttributes.addAttribute("id", saved.getId());
    return "redirect:/reservation/complete/{id}";
  }

  @GetMapping("/reservation/complete/{id}")
  public String complete(@PathVariable Long id, Model model) {
    reservations.findById(id).filter(reservation -> !reservation.isDeleted())
        .ifPresent(reservation -> model.addAttribute("reservation", reservation));
    return "public/reservation-complete";
  }
}
