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
      // Split "Login successful|ROLE_USER" into parts
      const parts = text.split('|');
      const role  = parts[1];   // "ROLE_USER" or "ROLE_ADMIN"

      // Save everything to session
      sessionStorage.setItem('bsEmail', email);
      sessionStorage.setItem('bsToken', btoa(email + ':' + password));
      sessionStorage.setItem('bsRole',  role);

      showMessage('Login successful! Redirecting...', 'success');

      // ✅ This is the fix — redirect after short delay
      setTimeout(() => {
        window.location.href = 'Homepage.html';
      }, 1);

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

document.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') login();
});