document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("[data-reservation-form]");
  if (!form) return;

  form.addEventListener("submit", (event) => {
    const data = new FormData(form);
    const lines = [
      "以下の内容で予約を登録します。",
      "",
      `来店日: ${data.get("visitDate")}`,
      `時間帯: ${data.get("timeSlot")}`,
      `人数: ${data.get("guestCount")}`,
      `氏名: ${data.get("name")}`,
      `メール: ${data.get("email")}`,
      "",
      "登録してよろしいですか？"
    ];

    if (!window.confirm(lines.join("\n"))) {
      event.preventDefault();
    }
  });
});
