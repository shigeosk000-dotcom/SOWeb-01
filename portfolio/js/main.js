// Portfolio sidebar navigation and small UI helpers

function generateNavigation() {
  const sidebar = document.querySelector('.sidebar');
  if (!sidebar) return;

  const navLevel = sidebar.dataset.navLevel || 'root';

  let rootPath = './';
  let docsPath = './docs/';
  let designPath = './design/';
  let promptsPath = './prompts/';

  if (navLevel === 'docs') {
    rootPath = '../';
    docsPath = './';
    designPath = '../design/';
    promptsPath = '../prompts/';
  } else if (navLevel === 'design') {
    rootPath = '../';
    docsPath = '../docs/';
    designPath = './';
    promptsPath = '../prompts/';
  } else if (navLevel === 'prompts') {
    rootPath = '../';
    docsPath = '../docs/';
    designPath = '../design/';
    promptsPath = './';
  }

  const navHTML = `
    <div class="sidebar-header">
      <div class="logo">PROJECT ARCHIVE</div>
      <div class="project-name"><br>\u30D7\u30ED\u30B8\u30A7\u30AF\u30C8\u30A2\u30FC\u30AB\u30A4\u30D6</div>
    </div>
    <nav class="sidebar-nav">
      <div class="nav-group">
        <div class="nav-group-title">\u30D7\u30ED\u30B8\u30A7\u30AF\u30C8</div>
        <a href="${rootPath}index.html"><span class="material-symbols-outlined icon-sm">home</span> \u30C8\u30C3\u30D7\u30DA\u30FC\u30B8</a>
        <a href="${rootPath}about.html"><span class="material-symbols-outlined icon-sm">person</span> \u81EA\u5DF1\u7D39\u4ECB</a>
        <a href="${rootPath}works.html"><span class="material-symbols-outlined icon-sm">work</span> \u5236\u4F5C\u5B9F\u7E3E</a>
        <a href="${rootPath}process.html"><span class="material-symbols-outlined icon-sm">assignment</span> \u5236\u4F5C\u30D7\u30ED\u30BB\u30B9</a>
        <a href="${rootPath}skills.html"><span class="material-symbols-outlined icon-sm">bolt</span> \u30B9\u30AD\u30EB\u30BB\u30C3\u30C8</a>
        <a href="${rootPath}contact.html"><span class="material-symbols-outlined icon-sm">mail</span> \u304A\u554F\u3044\u5408\u308F\u305B</a>
      </div>
      <div class="nav-group">
        <div class="nav-group-title">\u5236\u4F5C\u30C9\u30AD\u30E5\u30E1\u30F3\u30C8</div>
        <a href="${docsPath}01-proposal.html"><span class="nav-number">01</span> \u63D0\u6848\u66F8</a>
        <a href="${docsPath}02-market-research.html"><span class="nav-number">02</span> \u5E02\u5834\u8ABF\u67FB</a>
        <a href="${docsPath}03-persona.html"><span class="nav-number">03</span> \u30DA\u30EB\u30BD\u30CA</a>
        <a href="${docsPath}04-sitemap.html"><span class="nav-number">04</span> \u30B5\u30A4\u30C8\u30DE\u30C3\u30D7</a>
        <a href="${docsPath}05-wireframe.html"><span class="nav-number">05</span> \u30EF\u30A4\u30E4\u30FC\u30D5\u30EC\u30FC\u30E0</a>
        <a href="${docsPath}06-design-guide.html"><span class="nav-number">06</span> \u30C7\u30B6\u30A4\u30F3\u30AC\u30A4\u30C9\u30E9\u30A4\u30F3</a>
        <a href="${docsPath}10-retrospective.html"><span class="nav-number">10</span> \u632F\u308A\u8FD4\u308A\u30FB\u6240\u611F</a>
      </div>
      <div class="nav-group">
        <div class="nav-group-title">\u8A2D\u8A08\u30FB\u4ED5\u69D8</div>
        <a href="${docsPath}07-specification.html"><span class="nav-number">07</span> \u4ED5\u69D8\u66F8</a>
        <a href="${docsPath}08-db-design.html"><span class="nav-number">08</span> DB\u8A2D\u8A08\u66F8</a>
        <a href="${docsPath}09-test-report.html"><span class="nav-number">09</span> \u30C6\u30B9\u30C8\u5831\u544A\u66F8</a>
        <a href="${designPath}system-flow.html"><span class="material-symbols-outlined icon-sm">account_tree</span> \u30B7\u30B9\u30C6\u30E0\u30D5\u30ED\u30FC\u56F3</a>
        <a href="${designPath}class-diagram.html"><span class="material-symbols-outlined icon-sm">lan</span> \u30AF\u30E9\u30B9\u69CB\u6210\u56F3</a>
        <a href="${designPath}method-list.html"><span class="material-symbols-outlined icon-sm">list_alt</span> \u30E1\u30BD\u30C3\u30C9\u4E00\u89A7</a>
        <a href="${designPath}logic-explanation.html"><span class="material-symbols-outlined icon-sm">search</span> \u30ED\u30B8\u30C3\u30AF\u89E3\u8AAC</a>
      </div>
      <div class="nav-group">
        <div class="nav-group-title">\u30D7\u30ED\u30F3\u30D7\u30C8</div>
        <a href="${promptsPath}prompt-step.html"><span class="material-symbols-outlined icon-sm">format_list_numbered</span> \u30B9\u30C6\u30C3\u30D7\u7D39\u4ECB\u30D7\u30ED\u30F3\u30D7\u30C8</a>
        <a href="${promptsPath}prompt-function.html"><span class="material-symbols-outlined icon-sm">extension</span> \u6A5F\u80FD\u7D39\u4ECB\u30D7\u30ED\u30F3\u30D7\u30C8</a>
        <a href="${promptsPath}prompt-log.html"><span class="material-symbols-outlined icon-sm">history</span> \u5B9F\u884C\u30ED\u30B0</a>
      </div>
    </nav>
    <div class="sidebar-footer">
      &copy; Project Archive 2026
    </div>
  `;

  sidebar.innerHTML = navHTML;
}

document.addEventListener('DOMContentLoaded', () => {
  generateNavigation();

  const hamburger = document.querySelector('.hamburger');
  const sidebar = document.querySelector('.sidebar');

  if (hamburger && sidebar) {
    hamburger.addEventListener('click', () => {
      sidebar.classList.toggle('open');
    });

    document.addEventListener('click', (e) => {
      if (sidebar.classList.contains('open') && !sidebar.contains(e.target) && !hamburger.contains(e.target)) {
        sidebar.classList.remove('open');
      }
    });

    sidebar.querySelectorAll('a').forEach((link) => {
      link.addEventListener('click', () => {
        if (window.innerWidth <= 768) {
          sidebar.classList.remove('open');
        }
      });
    });
  }

  const currentPage = window.location.pathname.split('/').pop() || 'index.html';
  document.querySelectorAll('.sidebar-nav a').forEach((link) => {
    const href = link.getAttribute('href');
    if (href && (href.endsWith(currentPage) || (currentPage === 'index.html' && href === './index.html'))) {
      link.classList.add('active');
    }
  });

  document.querySelectorAll('.prompt-box').forEach((box) => {
    const promptText = box.querySelector('.prompt-text');
    if (!promptText) return;

    promptText.style.cursor = 'pointer';
    promptText.title = '\u30AF\u30EA\u30C3\u30AF\u3067\u30B3\u30D4\u30FC';

    promptText.addEventListener('click', () => {
      const text = promptText.textContent;
      navigator.clipboard.writeText(text).then(() => {
        const originalBg = promptText.style.background;
        promptText.style.background = '#d1fae5';
        promptText.style.transition = 'background 0.3s';
        setTimeout(() => {
          promptText.style.background = originalBg || '#fff';
        }, 500);
      });
    });
  });

  document.querySelectorAll('.toc-list a').forEach((link) => {
    link.addEventListener('click', (e) => {
      const href = link.getAttribute('href');
      if (!href || !href.startsWith('#')) return;

      e.preventDefault();
      const target = document.querySelector(href);
      if (target) {
        target.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    });
  });
});
