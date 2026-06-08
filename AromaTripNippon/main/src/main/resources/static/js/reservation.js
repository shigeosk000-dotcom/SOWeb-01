document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("[data-reservation-form]");
  if (!form) return;

  const isEnglish = document.documentElement.lang === "en";
  const visitDateInput = form.querySelector('input[name="visitDate"]');
  const now = new Date();
  const today = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;
  const max = (() => {
    const maxDate = new Date(now.getFullYear(), now.getMonth() + 3, now.getDate());
    return `${maxDate.getFullYear()}-${String(maxDate.getMonth() + 1).padStart(2, "0")}-${String(maxDate.getDate()).padStart(2, "0")}`;
  })();

  const validationFields = Array.from(form.querySelectorAll("[required]"));
  const englishMessages = {
    visitDate: {
      valueMissing: "Please choose a date.",
      tooEarly: "Please choose today or a future date.",
      tooLate: "Please choose a date within 3 months from today.",
    },
    timeSlot: {
      valueMissing: "Please select a time.",
    },
    guestCount: {
      valueMissing: "Please select the number of guests.",
    },
    name: {
      valueMissing: "Please enter your name.",
    },
    email: {
      valueMissing: "Please enter your email address.",
      typeMismatch: "Please enter a valid email address.",
    },
    privacyConsent: {
      valueMissing: "Please agree to the privacy policy.",
    },
  };

  if (visitDateInput) {
    visitDateInput.min = today;
    visitDateInput.max = max;
  }

  const getEnglishMessage = (field) => {
    const messages = englishMessages[field.name];
    if (!messages) return "";

    if (field.name === "visitDate") {
      if (field.validity.valueMissing) return messages.valueMissing;
      if (field.value && field.value < today) return messages.tooEarly;
      if (field.value && field.value > max) return messages.tooLate;
      return "";
    }

    if (field.validity.valueMissing) return messages.valueMissing || "";
    if (field.type === "email" && field.validity.typeMismatch) return messages.typeMismatch || "";
    return "";
  };

  const refreshValidity = (field) => {
    if (!isEnglish || !field) return;
    field.setCustomValidity(getEnglishMessage(field));
  };

  if (isEnglish) {
    form.noValidate = true;
    validationFields.forEach((field) => {
      refreshValidity(field);
      field.addEventListener("input", () => refreshValidity(field));
      field.addEventListener("change", () => refreshValidity(field));
    });
  }

  form.addEventListener("submit", (event) => {
    const data = new FormData(form);

    if (isEnglish) {
      validationFields.forEach((field) => refreshValidity(field));
      if (!form.checkValidity()) {
        event.preventDefault();
        form.reportValidity();
        return;
      }
    }

    const visitDate = data.get("visitDate");
    if (visitDate && visitDate < today) {
      event.preventDefault();
      window.alert(isEnglish ? "Please choose today or a future date." : "\u904e\u53bb\u306e\u65e5\u7a0b\u306f\u9078\u629e\u3067\u304d\u307e\u305b\u3093\u3002");
      return;
    }
    if (visitDate && visitDate > max) {
      event.preventDefault();
      window.alert(isEnglish ? "Please choose a date within 3 months from today." : "\u4eca\u65e5\u304b\u30893\u304b\u6708\u5148\u307e\u3067\u306e\u65e5\u4ed8\u3092\u9078\u3093\u3067\u304f\u3060\u3055\u3044\u3002");
      return;
    }

    const lines = isEnglish
      ? [
          "Would you like to submit this reservation?",
          "",
          `Date: ${data.get("visitDate")}`,
          `Time: ${data.get("timeSlot")}`,
          `Guests: ${data.get("guestCount")}`,
          `Name: ${data.get("name")}`,
          `Email: ${data.get("email")}`,
          `Phone: ${data.get("phone") || "-"}`,
          `Nationality: ${data.get("nationality") || "-"}`,
          "",
          "Please confirm the details before submitting.",
        ]
      : [
          "\u3053\u306e\u5185\u5bb9\u3067\u4e88\u7d04\u3092\u9001\u4fe1\u3057\u307e\u3059\u304b\uff1f",
          "",
          `\u65e5\u7a0b: ${data.get("visitDate")}`,
          `\u6642\u9593: ${data.get("timeSlot")}`,
          `\u4eba\u6570: ${data.get("guestCount")}`,
          `\u304a\u540d\u524d: ${data.get("name")}`,
          `\u30e1\u30fc\u30eb: ${data.get("email")}`,
          `\u96fb\u8a71: ${data.get("phone") || "-"}`,
          `\u56fd\u7c4d: ${data.get("nationality") || "-"}`,
          "",
          "\u9001\u4fe1\u524d\u306b\u5185\u5bb9\u3092\u3054\u78ba\u8a8d\u304f\u3060\u3055\u3044\u3002",
        ];

    if (!window.confirm(lines.join("\n"))) {
      event.preventDefault();
    }
  });
});
