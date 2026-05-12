document.addEventListener('DOMContentLoaded', () => {
  const sliders = document.querySelectorAll('.pro-slider');

  sliders.forEach((slider) => {
    const slides = Array.from(slider.querySelectorAll('.pro-slider__slide'));
    const prev = slider.querySelector('.pro-slider__nav--prev');
    const next = slider.querySelector('.pro-slider__nav--next');
    const dots = Array.from(slider.querySelectorAll('.pro-slider__dot'));
    const intervalMs = Number(slider.dataset.interval || 5000);
    let index = 0;
    let timer = null;

    function render(newIndex) {
      index = (newIndex + slides.length) % slides.length;
      slides.forEach((slide, i) => slide.classList.toggle('is-active', i === index));
      dots.forEach((dot, i) => {
        dot.classList.toggle('is-active', i === index);
        dot.setAttribute('aria-selected', i === index ? 'true' : 'false');
      });
    }

    function start() {
      stop();
      timer = window.setInterval(() => render(index + 1), intervalMs);
    }

    function stop() {
      if (timer) window.clearInterval(timer);
      timer = null;
    }

    if (slides.length <= 1) return;
    prev?.addEventListener('click', () => { render(index - 1); start(); });
    next?.addEventListener('click', () => { render(index + 1); start(); });
    dots.forEach((dot, i) => dot.addEventListener('click', () => { render(i); start(); }));
    slider.addEventListener('mouseenter', stop);
    slider.addEventListener('mouseleave', start);
    slider.addEventListener('focusin', stop);
    slider.addEventListener('focusout', start);
    render(0);
    start();
  });
});
