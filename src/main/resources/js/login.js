const API = 'http://localhost:8080';

async function login() {
  const email    = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value;

  if (!email || !password) {
    showMessage('Please fill in all fields', 'error');
    return;
  }

  try {
    const response = await fetch(`${API}/api/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });

    const text = await response.text();

    if (response.ok) {
      // Backend returns "Login successful|ROLE_USER" or "Login successful|ROLE_ADMIN"
      const parts = text.split('|');
      const role  = parts[1]; // ROLE_USER or ROLE_ADMIN

      // Save login info for other pages to use
      sessionStorage.setItem('bsEmail', email);
      sessionStorage.setItem('bsToken', btoa(email + ':' + password));
      sessionStorage.setItem('bsRole',  role);

      showMessage('Login successful! Redirecting...', 'success');
      setTimeout(() => window.location.href = 'homepage.html', 1000);

    } else {
      showMessage(text, 'error');
    }

  } catch (error) {
    showMessage('Cannot connect to server. Is it running?', 'error');
  }
}

function showMessage(text, type) {
  const msg = document.getElementById('message');
  msg.textContent = text;
  msg.className = type;
}

// Enter key support
document.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') login();
});
