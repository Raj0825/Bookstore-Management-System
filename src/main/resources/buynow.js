// Get book ID from URL: buynow.html?bookId=5
const urlParams = new URLSearchParams(window.location.search);
const bookId    = urlParams.get('bookId');

if (!bookId) {
  alert('No book selected!');
  window.location.href = 'homepage.html';
}

async function confirmPurchase() {
  const body = {
    bookId:          parseInt(bookId),
    quantity:        parseInt(document.getElementById('quantity').value),
    shippingAddress: document.getElementById('address').value.trim(),
    city:            document.getElementById('city').value.trim(),
    pinCode:         document.getElementById('pin').value.trim(),
    phoneNumber:     document.getElementById('phone').value.trim(),
  };

  if (!body.shippingAddress || !body.city || !body.pinCode || !body.phoneNumber) {
    showMessage('Please fill in all fields', 'error');
    return;
  }

  try {
    const res  = await fetch(`${API}/api/orders/buy-now`, {
      method: 'POST', headers,
      body: JSON.stringify(body)
    });
    const text = await res.text();

    if (res.ok) {
      showMessage('Purchase successful! Redirecting to orders...', 'success');
      setTimeout(() => window.location.href = 'orders.html', 1500);
    } else {
      showMessage(text, 'error');
    }
  } catch (e) {
    showMessage('Purchase failed. Try again.', 'error');
  }
}

function showMessage(text, type) {
  const msg = document.getElementById('message');
  msg.textContent = text;
  msg.className = type;
}
