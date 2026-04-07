const API = 'http://localhost:8080';

async function register() {
  const name     = document.getElementById('name').value.trim();
  const email    = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value;
  const msg      = document.getElementById('message');

  // Basic validation
  if (!name || !email || !password) {
    showMessage('Please fill in all fields', 'error');
    return;
  }

  try {
    const response = await fetch(`${API}/api/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email, password })
    });

    const text = await response.text();

    if (response.ok) {
      showMessage('Account created! Redirecting to login...', 'success');
      setTimeout(() => window.location.href = 'login.html', 1500);
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

// Allow pressing Enter to submit
document.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') register();
});
