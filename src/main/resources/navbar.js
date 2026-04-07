// Shared across all pages — runs on every page that includes navbar.js
// navbar.js

const token = sessionStorage.getItem('bsToken');

// ✅ FIX: Only redirect if NOT on login.html or register.html
const isAuthPage = window.location.pathname.includes('login.html') ||
                   window.location.pathname.includes('register.html');

if (!token && !isAuthPage) {
    window.location.href = 'login.html';
}
const API   = 'http://localhost:8080';
const token = sessionStorage.getItem('bsToken');
const role  = sessionStorage.getItem('bsRole');
const email = sessionStorage.getItem('bsEmail');

// Redirect to login if not logged in
if (!token) window.location.href = 'login.html';

// Show email in navbar
const navEmail = document.getElementById('nav-email');
if (navEmail) navEmail.textContent = email || '';

// Show Admin link only for ROLE_ADMIN
const adminLink = document.getElementById('admin-link');
if (adminLink && role === 'ROLE_ADMIN') {
  adminLink.style.display = 'inline-block';
}

// Standard headers for all API calls
const headers = {
  'Content-Type': 'application/json',
  'Authorization': 'Basic ' + token
};

// Logout
function logout() {
  sessionStorage.clear();
  window.location.href = 'login.html';
}

// Update cart badge number
async function updateCartBadge() {
  try {
    const res   = await fetch(`${API}/api/cart/my-cart`, { headers });
    const items = await res.json();
    const badge = document.getElementById('cart-count');
    if (badge && items.length > 0) {
      badge.textContent = items.length;
      badge.style.display = 'inline';
    }
  } catch (e) {
    // silently fail
  }
}

updateCartBadge();
