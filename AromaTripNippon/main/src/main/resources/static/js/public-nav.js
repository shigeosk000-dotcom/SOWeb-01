document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll(".header").forEach((header) => {
    const inner = header.querySelector(".header-inner");
    const nav = header.querySelector(".nav");
    if (!inner || !nav || inner.querySelector(".menu-toggle")) return;

    const button = document.createElement("button");
    button.className = "menu-toggle";
    button.type = "button";
    button.setAttribute("aria-label", document.documentElement.lang === "en" ? "Open menu" : "メニューを開く");
    button.setAttribute("aria-controls", "site-navigation");
    button.setAttribute("aria-expanded", "false");
    button.innerHTML = "<span></span><span></span><span></span>";

    if (!nav.id) nav.id = "site-navigation";
    inner.insertBefore(button, inner.firstChild);

    const setOpen = (open) => {
      header.classList.toggle("is-menu-open", open);
      button.setAttribute("aria-expanded", String(open));
      button.setAttribute(
        "aria-label",
        open
          ? (document.documentElement.lang === "en" ? "Close menu" : "メニューを閉じる")
          : (document.documentElement.lang === "en" ? "Open menu" : "メニューを開く")
      );
    };

    button.addEventListener("click", () => {
      setOpen(!header.classList.contains("is-menu-open"));
    });

    header.querySelectorAll(".nav a, .header-actions a").forEach((link) => {
      link.addEventListener("click", () => setOpen(false));
    });

    document.addEventListener("keydown", (event) => {
      if (event.key === "Escape") setOpen(false);
    });

    window.addEventListener("resize", () => {
      if (window.matchMedia("(min-width: 901px)").matches) setOpen(false);
    });
  });
});
