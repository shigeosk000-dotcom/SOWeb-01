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

  if (visitDateInput) {
    visitDateInput.min = today;
    visitDateInput.max = max;
  }

  form.addEventListener("submit", (event) => {
    const data = new FormData(form);
    const visitDate = data.get("visitDate");
    if (visitDate && visitDate < today) {
      event.preventDefault();
      window.alert(isEnglish ? "Please choose today or a future date." : "予約日は本日以降を選択してください。");
      return;
    }
    if (visitDate && visitDate > max) {
      event.preventDefault();
      window.alert(isEnglish ? "Please choose a date within 3 months from today." : "予約日は本日から3か月先まで選択できます。");
      return;
    }

    const lines = isEnglish
      ? [
          "Submit this reservation?",
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
          "この内容で予約を送信します。",
          "",
          `日程: ${data.get("visitDate")}`,
          `時間: ${data.get("timeSlot")}`,
          `人数: ${data.get("guestCount")}`,
          `名前: ${data.get("name")}`,
          `メール: ${data.get("email")}`,
          `電話: ${data.get("phone") || "-"}`,
          `国籍: ${data.get("nationality") || "-"}`,
          "",
          "送信前に内容をご確認ください。",
        ];

    if (!window.confirm(lines.join("\n"))) {
      event.preventDefault();
    }
  });
});
