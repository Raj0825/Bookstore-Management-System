const EMOJIS = ['📗', '📘', '📙', '📕', '📔', '📒'];

// Load all books when page opens
loadBooks();


// homepage.js
document.addEventListener('DOMContentLoaded', () => {
    if (sessionStorage.getItem('bsToken')) {
        loadBooks();
        updateCartBadge();
    }
});

async function loadBooks() {
  document.getElementById('books-grid').innerHTML = '<p class="loading">Loading books...</p>';
  try {
    const res   = await fetch(`${API}/api/books/all`, { headers });
    const books = await res.json();
    renderBooks(books);
  } catch (e) {
    document.getElementById('books-grid').innerHTML =
      '<p class="loading">❌ Could not load books. Is the server running?</p>';
  }
}

async function searchBooks() {
  const query = document.getElementById('search-input').value.trim();
  if (!query) return loadBooks();
  try {
    const res   = await fetch(`${API}/api/books/search?query=${encodeURIComponent(query)}`, { headers });
    const books = await res.json();
    renderBooks(books);
  } catch (e) {
    alert('Search failed');
  }
}

async function filterGenre(genre, el) {
  // Highlight selected chip
  document.querySelectorAll('.chip').forEach(c => c.classList.remove('active'));
  el.classList.add('active');

  if (!genre) return loadBooks();

  try {
    const res   = await fetch(`${API}/api/books/category/${encodeURIComponent(genre)}`, { headers });
    const books = await res.json();
    renderBooks(books);
  } catch (e) {
    alert('Filter failed');
  }
}

function renderBooks(books) {
  const grid = document.getElementById('books-grid');

  if (!books.length) {
    grid.innerHTML = '<p class="loading">No books found.</p>';
    return;
  }

  grid.innerHTML = books.map((book, i) => `
    <div class="book-card">
      <div class="book-cover cover-${i % 6}">
        ${EMOJIS[i % EMOJIS.length]}
      </div>
      <div class="book-info">
        <div class="book-genre">${book.genre || 'General'}</div>
        <div class="book-title">${book.title}</div>
        <div class="book-author">by ${book.author}</div>
      </div>
      <div class="book-footer">
        <div>
          <div class="book-price">₹${book.price}</div>
          <div class="book-qty">${book.quantity > 0 ? book.quantity + ' left' : 'Out of stock'}</div>
        </div>
        <div class="book-actions">
          <button onclick="addToCart(${book.id})"     title="Add to Cart">🛒</button>
          <button onclick="goToBuyNow(${book.id})"    title="Buy Now">⚡</button>
          <button onclick="addToWishlist(${book.id})" title="Wishlist">♡</button>
        </div>
      </div>
    </div>
  `).join('');
}

async function addToCart(bookId) {
  try {
    const res  = await fetch(`${API}/api/cart/add`, {
      method: 'POST', headers,
      body: JSON.stringify({ bookId, quantity: 1 })
    });
    const text = await res.text();
    if (res.ok) {
      alert('Added to cart! 🛒');
      updateCartBadge();
    } else {
      alert(text);
    }
  } catch (e) { alert('Failed to add to cart'); }
}

async function addToWishlist(bookId) {
  try {
    const res = await fetch(`${API}/api/wishlist/add`, {
      method: 'POST', headers,
      body: JSON.stringify({ bookId })
    });
    if (res.ok) alert('Added to wishlist! ♥');
    else        alert(await res.text());
  } catch (e) { alert('Failed'); }
}

// Go to buy-now page with the book id in URL
function goToBuyNow(bookId) {
  window.location.href = `buynow.html?bookId=${bookId}`;
}
