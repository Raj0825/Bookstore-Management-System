loadOrders();

async function loadOrders() {
  const el = document.getElementById('orders-list');
  try {
    const res    = await fetch(`${API}/api/orders/my-history`, { headers });
    const orders = await res.json();

    if (!orders.length) {
      el.innerHTML = `
        <div class="empty">
          <div class="icon">📦</div>
          <h3>No orders yet</h3>
          <p>Place an order and it will show up here.</p>
        </div>`;
      return;
    }

    el.innerHTML = orders.reverse().map(o => `
      <div class="order-card">
        <div class="order-head">
          <span class="order-id">Order #${o.id}</span>
          <span class="order-date">
            ${new Date(o.orderDate).toLocaleDateString('en-IN', { day:'numeric', month:'short', year:'numeric' })}
          </span>
          <span class="order-status status-${(o.orderStatus || 'pending').toLowerCase()}">
            ${o.orderStatus || 'Pending'}
          </span>
        </div>
        <div class="order-body">
          <div class="order-field">
            <div class="label">Total Amount</div>
            <strong>₹${o.totalAmount?.toFixed(2)}</strong>
          </div>
          ${o.shippingAddress ? `
          <div class="order-field">
            <div class="label">Delivered To</div>
            <div>${o.shippingAddress}, ${o.city} - ${o.pinCode}</div>
          </div>` : ''}
          ${o.paymentMethod ? `
          <div class="order-field">
            <div class="label">Payment</div>
            <div>${o.paymentMethod}</div>
          </div>` : ''}
        </div>
      </div>
    `).join('');

  } catch (e) {
    el.innerHTML = '<p class="loading">❌ Could not load orders.</p>';
  }
}
