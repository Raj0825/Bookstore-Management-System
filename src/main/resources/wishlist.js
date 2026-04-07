const EMOJIS = ['📗', '📘', '📙', '📕', '📔', '📒'];

loadWishlist();
loadRecommendations();

async function loadWishlist() {
  const el = document.getElementById('wishlist-grid');
  try {
    const res   = await fetch(`${API}/api/wishlist/my-wishlist`, { headers });
    const items = await res.json();

    if (!items.length) {
      el.innerHTML = `
        <div class="empty">
          <div class="icon">♡</div>
          <h3>Wishlist is empty</h3>
          <p>Click the ♡ button on any book to save it here.</p>
        </div>`;
      return;
    }

    el.innerHTML = items.map((w, i) => `
      <div class="wishlist-card">
        <div class="wl-icon">${EMOJIS[i % EMOJIS.length]}</div>
        <div>
          <div class="wl-title">${w.book.title}</div>
          <div class="wl-author">by ${w.book.author}</div>
          <div class="wl-price">₹${w.book.price}</div>
          <button class="wl-btn" onclick="addToCart(${w.book.id})">🛒 Add to Cart</button>
        </div>
      </div>
    `).join('');

  } catch (e) {
    el.innerHTML = '<p class="loading">❌ Could not load wishlist.</p>';
  }
}

async function loadRecommendations() {
  const el = document.getElementById('reco-grid');
  try {
    const res   = await fetch(`${API}/api/recommendations`, { headers });
    const books = await res.json();

    if (!books.length) {
      el.innerHTML = '<p class="loading">No recommendations yet. Add books to your wishlist first!</p>';
      return;
    }

    el.innerHTML = books.slice(0, 8).map((b, i) => `
      <div class="wishlist-card">
        <div class="wl-icon">${EMOJIS[i % EMOJIS.length]}</div>
        <div>
          <div class="wl-title">${b.title}</div>
          <div class="wl-author">by ${b.author}</div>
          <div class="wl-price">₹${b.price}</div>
          <button class="wl-btn" onclick="addToCart(${b.id})">🛒 Add to Cart</button>
        </div>
      </div>
    `).join('');

  } catch (e) {
    el.innerHTML = '<p class="loading">Recommendations unavailable.</p>';
  }
}

async function addToCart(bookId) {
  try {
    const res = await fetch(`${API}/api/cart/add`, {
      method: 'POST', headers,
      body: JSON.stringify({ bookId, quantity: 1 })
    });
    if (res.ok) {
      alert('Added to cart! 🛒');
      updateCartBadge();
    } else {
      alert(await res.text());
    }
  } catch (e) { alert('Failed to add to cart'); }
}
