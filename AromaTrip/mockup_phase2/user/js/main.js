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
    setupReservationForm();
  });
})();
