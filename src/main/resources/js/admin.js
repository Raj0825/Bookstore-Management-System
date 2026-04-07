// Check if user is admin, show content or deny access
if (role === 'ROLE_ADMIN') {
  document.getElementById('admin-content').style.display = 'block';
} else {
  document.getElementById('access-denied').style.display = 'block';
}

async function addBook() {
  const title       = document.getElementById('a-title').value.trim();
  const author      = document.getElementById('a-author').value.trim();
  const price       = parseFloat(document.getElementById('a-price').value);
  const quantity    = parseInt(document.getElementById('a-qty').value);
  const genre       = document.getElementById('a-genre').value;
  const description = document.getElementById('a-desc').value.trim();

  // Validation
  if (!title || !author || !price || !quantity) {
    showMessage('Please fill in all required fields (*)', 'error');
    return;
  }

  try {
    const res = await fetch(`${API}/api/books/add`, {
      method: 'POST',
      headers,
      body: JSON.stringify({ title, author, price, quantity, genre, description })
    });

    if (res.ok) {
      showMessage('✓ Book added to catalog successfully!', 'success');
      // Clear the form
      document.getElementById('a-title').value  = '';
      document.getElementById('a-author').value = '';
      document.getElementById('a-price').value  = '';
      document.getElementById('a-qty').value    = '';
      document.getElementById('a-desc').value   = '';
    } else {
      showMessage('Failed to add book. Check your admin access.', 'error');
    }

  } catch (e) {
    showMessage('Could not connect to server.', 'error');
  }
}

function showMessage(text, type) {
  const msg = document.getElementById('message');
  msg.textContent = text;
  msg.className = type;
}
