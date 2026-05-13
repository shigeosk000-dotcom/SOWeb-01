document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("[data-reservation-form]");
  if (!form) return;

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
      window.alert("予約日は本日以降を選択してください。");
      return;
    }

    const lines = [
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
