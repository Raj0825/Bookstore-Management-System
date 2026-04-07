// Invoice data is saved in sessionStorage by cart.js after checkout
const inv = JSON.parse(sessionStorage.getItem('lastInvoice'));

if (!inv) {
  // No invoice found — redirect to orders
  window.location.href = 'orders.html';
}

const date = new Date(inv.date).toLocaleDateString('en-IN', {
  day: 'numeric', month: 'long', year: 'numeric'
});

document.getElementById('invoice-box').innerHTML = `
  <div class="invoice-header">
    <div class="logo">📚</div>
    <h2>PageTurn Bookstore</h2>
    <p>Order Invoice</p>
  </div>

  <div class="invoice-row">
    <span class="label">Order ID</span>
    <strong>#${inv.orderId}</strong>
  </div>
  <div class="invoice-row">
    <span class="label">Date</span>
    <span>${date}</span>
  </div>
  <div class="invoice-row">
    <span class="label">Customer</span>
    <span>${inv.customerName}</span>
  </div>
  <div class="invoice-row">
    <span class="label">Address</span>
    <span>${inv.fullAddress}</span>
  </div>
  <div class="invoice-row">
    <span class="label">Payment</span>
    <span>${inv.paymentMethod}</span>
  </div>
  <div class="invoice-row" style="margin-top:8px">
    <span class="label">Subtotal</span>
    <span>₹${inv.subTotal?.toFixed(2)}</span>
  </div>
  <div class="invoice-row">
    <span class="label">Shipping</span>
    <span>₹${inv.shippingCost?.toFixed(2)}</span>
  </div>
  <div class="invoice-total">
    <span>Total Paid</span>
    <span>₹${inv.totalAmount?.toFixed(2)}</span>
  </div>
`;

// Clear invoice from session after showing
sessionStorage.removeItem('lastInvoice');
