(() => {
  const RESERVATION_KEY = "aromaTripReservation";

  const todayValue = () => {
    const now = new Date();
    return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;
  };

  const formatDate = (value) => {
    if (!value) return "";
    const date = new Date(`${value}T00:00:00`);
    if (Number.isNaN(date.getTime())) return value;
    return new Intl.DateTimeFormat("ja-JP", {
      year: "numeric",
      month: "long",
      day: "numeric",
      weekday: "short",
    }).format(date);
  };


  const setupProSliders = () => {
    const sliders = document.querySelectorAll(".pro-slider");

    sliders.forEach((slider) => {
      const slides = Array.from(slider.querySelectorAll(".pro-slider__slide"));
      const prev = slider.querySelector(".pro-slider__nav--prev");
      const next = slider.querySelector(".pro-slider__nav--next");
      const dots = Array.from(slider.querySelectorAll(".pro-slider__dot"));
      const intervalMs = Number(slider.dataset.interval || 5000);
      let index = 0;
      let timer = null;

      const render = (newIndex) => {
        index = (newIndex + slides.length) % slides.length;
        slides.forEach((slide, i) => slide.classList.toggle("is-active", i === index));
        dots.forEach((dot, i) => {
          dot.classList.toggle("is-active", i === index);
          dot.setAttribute("aria-selected", i === index ? "true" : "false");
        });
      };

      const stop = () => {
        if (timer) window.clearInterval(timer);
        timer = null;
      };

      const start = () => {
        stop();
        timer = window.setInterval(() => render(index + 1), intervalMs);
      };

      if (slides.length <= 1) return;

      prev?.addEventListener("click", () => {
        render(index - 1);
        start();
      });

      next?.addEventListener("click", () => {
        render(index + 1);
        start();
      });

      dots.forEach((dot, i) => {
        dot.addEventListener("click", () => {
          render(i);
          start();
        });
      });

      slider.addEventListener("mouseenter", stop);
      slider.addEventListener("mouseleave", start);
      slider.addEventListener("focusin", stop);
      slider.addEventListener("focusout", start);

      render(0);
      start();
    });
  };


  const setupMobileNav = () => {
    const header = document.querySelector(".header");
    const toggle = document.querySelector(".menu-toggle");
    const nav = document.querySelector(".mobile-nav");
    if (!header || !toggle || !nav) return;

    const setOpen = (isOpen) => {
      toggle.setAttribute("aria-expanded", String(isOpen));
      toggle.setAttribute("aria-label", isOpen ? "メニューを閉じる" : "メニューを開く");
      nav.hidden = !isOpen;
      document.body.classList.toggle("nav-open", isOpen);
    };

    toggle.addEventListener("click", () => {
      setOpen(toggle.getAttribute("aria-expanded") !== "true");
    });

    nav.addEventListener("click", (event) => {
      if (event.target.closest("a")) setOpen(false);
    });

    document.addEventListener("click", (event) => {
      if (nav.hidden || header.contains(event.target)) return;
      setOpen(false);
    });

    document.addEventListener("keydown", (event) => {
      if (event.key === "Escape") setOpen(false);
    });

    window.addEventListener("resize", () => {
      if (window.matchMedia("(min-width: 901px)").matches) setOpen(false);
    });
  };

  const setupReservationForm = () => {
    const form = document.querySelector('form[action="reservation-complete.html"]');
    if (!form) return;

    const date = form.querySelector("#date");
    const time = form.querySelector("#time");
    const people = form.querySelector("#people");
    const name = form.querySelector("#name");
    const email = form.querySelector("#email");
    const lang = form.querySelector("#lang");
    const request = form.querySelector("#request");

    if (date) date.min = todayValue();

    [date, name, email].forEach((field) => {
      if (field) field.required = true;
    });

    form.addEventListener("submit", (event) => {
      const selectedDate = date?.value || "";
      const emailValue = email?.value.trim() || "";
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      date?.setCustomValidity("");
      email?.setCustomValidity("");

      if (selectedDate && selectedDate < todayValue()) {
        date.setCustomValidity("過去の日程は選択できません。");
      }

      if (emailValue && !emailPattern.test(emailValue)) {
        email.setCustomValidity("メールアドレスの形式を確認してください。");
      }

      if (!form.reportValidity()) {
        event.preventDefault();
        return;
      }

      const reservation = {
        date: selectedDate,
        dateLabel: formatDate(selectedDate),
        time: time?.value || "",
        people: people?.value || "",
        name: name?.value.trim() || "",
        email: emailValue,
        language: lang?.value || "",
        request: request?.value.trim() || "",
        createdAt: new Date().toISOString(),
      };

      window.localStorage.setItem(RESERVATION_KEY, JSON.stringify(reservation));
    });
  };

  document.addEventListener("DOMContentLoaded", () => {
    setupMobileNav();
    setupProSliders();
    setupReservationForm();
  });
})();
