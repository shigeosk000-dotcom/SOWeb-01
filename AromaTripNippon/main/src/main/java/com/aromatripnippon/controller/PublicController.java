package com.aromatripnippon.controller;

import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.form.ReservationRequest;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.ReservationRepository;
import com.aromatripnippon.service.ReservationService;
import jakarta.validation.Valid;
import java.time.LocalDate;
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
  private static final String LANGUAGE_JAPANESE = "\u65E5\u672C\u8A9E";
  private static final String LANGUAGE_ENGLISH = "\u82F1\u8A9E";
  private static final String MESSAGE_PAST_DATE_JA = "\u4E88\u7D04\u65E5\u306F\u672C\u65E5\u4EE5\u964D\u3092\u9078\u629E\u3057\u3066\u304F\u3060\u3055\u3044\u3002";
  private static final String MESSAGE_MAX_DATE_JA = "\u4E88\u7D04\u65E5\u306F\u672C\u65E5\u304B\u30893\u304B\u6708\u5148\u307E\u3067\u9078\u629E\u3067\u304D\u307E\u3059\u3002";

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
    ReservationRequest reservationRequest = new ReservationRequest();
    reservationRequest.setPreferredLanguage(LANGUAGE_JAPANESE);
    model.addAttribute("reservationRequest", reservationRequest);
    populateReservationPageModel(model);
    return "public/reservation";
  }

  @GetMapping("/en/reservation")
  public String reservationEn(Model model) {
    ReservationRequest reservationRequest = new ReservationRequest();
    reservationRequest.setPreferredLanguage("English");
    model.addAttribute("reservationRequest", reservationRequest);
    populateReservationPageModel(model);
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
    return createReservation(reservationRequest, errors, model, redirectAttributes, true);
  }

  private String createReservation(ReservationRequest reservationRequest, BindingResult errors,
      Model model, RedirectAttributes redirectAttributes, boolean english) {
    reservationRequest.setPreferredLanguage(normalizePreferredLanguage(reservationRequest.getPreferredLanguage()));
    if (errors.hasErrors()) {
      populateReservationPageModel(model);
      return english ? "public/en/reservation" : "public/reservation";
    }

    Customer customer = new Customer();
    customer.setName(reservationRequest.getName());
    customer.setEmail(reservationRequest.getEmail());
    customer.setPhone(reservationRequest.getPhone());
    customer.setNationality(reservationRequest.getNationality());
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
      populateReservationPageModel(model);
      model.addAttribute("errorMessage", english ? toEnglishValidationMessage(ex.getMessage()) : ex.getMessage());
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

  private void populateReservationPageModel(Model model) {
    LocalDate today = LocalDate.now();
    model.addAttribute("minDate", today);
    model.addAttribute("maxDate", today.plusMonths(3));
    model.addAttribute("program", programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElse(null));
  }

  private String normalizePreferredLanguage(String value) {
    if (value == null) return LANGUAGE_ENGLISH;
    String normalized = value.trim();
    if ("Japanese".equalsIgnoreCase(normalized) || LANGUAGE_JAPANESE.equals(normalized)) return LANGUAGE_JAPANESE;
    if ("English".equalsIgnoreCase(normalized) || LANGUAGE_ENGLISH.equals(normalized)) return LANGUAGE_ENGLISH;
    return LANGUAGE_ENGLISH;
  }

  private String toEnglishValidationMessage(String jaMessage) {
    if (MESSAGE_MAX_DATE_JA.equals(jaMessage)) {
      return "Please choose a date within 3 months from today.";
    }
    return "Please choose today or a future date for your reservation.";
  }
}
