loadCart();

async function loadCart() {
  const el = document.getElementById('cart-content');
  try {
    const res   = await fetch(`${API}/api/cart/my-cart`, { headers });
    const items = await res.json();

    if (!items.length) {
      el.innerHTML = `
        <div class="empty">
          <div class="icon">🛒</div>
          <h3>Your cart is empty</h3>
          <p>Go browse some books!</p>
          <br>
          <a href="homepage.html" style="color:var(--brown); font-weight:700">Browse Books →</a>
        </div>`;
      return;
    }

    const total = items.reduce((sum, item) => sum + item.book.price * item.quantity, 0);

    el.innerHTML = `
      <table class="cart-table">
        <thead>
          <tr>
            <th>Book</th>
            <th>Author</th>
            <th>Qty</th>
            <th>Price</th>
            <th>Subtotal</th>
          </tr>
        </thead>
        <tbody>
          ${items.map(item => `
            <tr>
              <td><strong>${item.book.title}</strong></td>
              <td style="color:var(--muted)">${item.book.author}</td>
              <td>${item.quantity}</td>
              <td>₹${item.book.price}</td>
              <td><strong>₹${(item.book.price * item.quantity).toFixed(2)}</strong></td>
            </tr>
          `).join('')}
        </tbody>
      </table>

      <div class="cart-summary">
        <div>
          <div class="total-label">Total (excl. shipping)</div>
          <div class="total-amount">₹${total.toFixed(2)}</div>
        </div>
        <button onclick="showCheckout()">Proceed to Checkout →</button>
      </div>`;

  } catch (e) {
    el.innerHTML = '<p class="loading">❌ Could not load cart.</p>';
  }
}

function showCheckout() {
  document.getElementById('checkout-section').style.display = 'block';
  document.getElementById('checkout-section').scrollIntoView({ behavior: 'smooth' });
}

function hideCheckout() {
  document.getElementById('checkout-section').style.display = 'none';
}

async function placeOrder() {
  const body = {
    shippingAddress: document.getElementById('address').value.trim(),
    city:            document.getElementById('city').value.trim(),
    pinCode:         document.getElementById('pin').value.trim(),
    phoneNumber:     document.getElementById('phone').value.trim(),
    shippingMethod:  document.getElementById('shipping').value,
    paymentMethod:   document.getElementById('payment').value,
  };

  if (!body.shippingAddress || !body.city || !body.pinCode || !body.phoneNumber) {
    alert('Please fill in all shipping details');
    return;
  }

  try {
    const res = await fetch(`${API}/api/cart/checkout`, {
      method: 'POST', headers,
      body: JSON.stringify(body)
    });

    if (res.ok) {
      const invoice = await res.json();
      // Save invoice to pass to invoice page
      sessionStorage.setItem('lastInvoice', JSON.stringify(invoice));
      window.location.href = 'invoice.html';
    } else {
      alert(await res.text());
    }
  } catch (e) {
    alert('Checkout failed');
  }
}
