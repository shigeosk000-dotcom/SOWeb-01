document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("[data-reservation-form]");
  if (!form) return;

  const isEnglish = document.documentElement.lang === "en";
  const visitDateInput = form.querySelector('input[name="visitDate"]');
  const now = new Date();
  const today = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;

  if (visitDateInput) {
    visitDateInput.min = today;
  }

  form.addEventListener("submit", (event) => {
    const data = new FormData(form);
    const visitDate = data.get("visitDate");
    if (visitDate && visitDate < today) {
      event.preventDefault();
      window.alert(isEnglish ? "Please choose today or a future date." : "予約日は本日以降を選択してください。");
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
          "",
          "Please confirm the details before submitting.",
        ]
      : [
          "以下の内容で予約を登録します。",
          "",
          `来店日: ${data.get("visitDate")}`,
          `時間帯: ${data.get("timeSlot")}`,
          `人数: ${data.get("guestCount")}`,
          `氏名: ${data.get("name")}`,
          `メール: ${data.get("email")}`,
          "",
          "登録してよろしいですか？",
        ];

    if (!window.confirm(lines.join("\n"))) {
      event.preventDefault();
    }
  });
});
