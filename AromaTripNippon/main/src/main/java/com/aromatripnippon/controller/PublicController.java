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

  @GetMapping({"/en", "/en/"})
  public String indexEn(Model model) {
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
    return "public/en/index";
  }

  @GetMapping("/experience")
  public String experience(Model model) {
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
    return "public/experience";
  }

  @GetMapping("/en/experience")
  public String experienceEn(Model model) {
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
    return "public/en/experience";
  }

  @GetMapping("/concept")
  public String concept() {
    return "public/concept";
  }

  @GetMapping("/en/concept")
  public String conceptEn() {
    return "public/en/concept";
  }

  @GetMapping("/reservation")
  public String reservation(Model model) {
    model.addAttribute("reservationRequest", new ReservationRequest());
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
    return "public/reservation";
  }

  @GetMapping("/en/reservation")
  public String reservationEn(Model model) {
    ReservationRequest reservationRequest = new ReservationRequest();
    reservationRequest.setPreferredLanguage("English");
    model.addAttribute("reservationRequest", reservationRequest);
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
    return "public/en/reservation";
  }

  @PostMapping("/reservation")
  public String createReservation(@Valid @ModelAttribute ReservationRequest reservationRequest, BindingResult errors,
      Model model, RedirectAttributes redirectAttributes) {
    return createReservation(reservationRequest, errors, model, redirectAttributes, false);
  }

  @PostMapping("/en/reservation")
  public String createReservationEn(@Valid @ModelAttribute ReservationRequest reservationRequest, BindingResult errors,
      Model model, RedirectAttributes redirectAttributes) {
    reservationRequest.setPreferredLanguage("English");
    return createReservation(reservationRequest, errors, model, redirectAttributes, true);
  }

  private String createReservation(ReservationRequest reservationRequest, BindingResult errors,
      Model model, RedirectAttributes redirectAttributes, boolean english) {
    if (errors.hasErrors()) {
      model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
      return english ? "public/en/reservation" : "public/reservation";
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
      model.addAttribute("errorMessage",
          english ? "Please choose today or a future date for your reservation." : ex.getMessage());
      return english ? "public/en/reservation" : "public/reservation";
    }
    redirectAttributes.addAttribute("id", saved.getId());
    return english ? "redirect:/en/reservation/complete/{id}" : "redirect:/reservation/complete/{id}";
  }

  @GetMapping("/reservation/complete/{id}")
  public String complete(@PathVariable Long id, Model model) {
    reservations.findById(id).filter(reservation -> !reservation.isDeleted())
        .ifPresent(reservation -> model.addAttribute("reservation", reservation));
    return "public/reservation-complete";
  }

  @GetMapping("/en/reservation/complete/{id}")
  public String completeEn(@PathVariable Long id, Model model) {
    reservations.findById(id).filter(reservation -> !reservation.isDeleted())
        .ifPresent(reservation -> model.addAttribute("reservation", reservation));
    return "public/en/reservation-complete";
  }
}
