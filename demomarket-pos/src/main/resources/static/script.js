let allProducts = [];

document.addEventListener("DOMContentLoaded", () => {
  const sessionId = getOrCreateSessionId();

  fetch("http://localhost:8080/api/products")
    .then(res => res.json())
    .then(products => {
      allProducts = products;
      renderProducts(products);
    });

  // Sepet sayacı güncelle
  const savedCount = localStorage.getItem("cartCount") || 0;
  document.getElementById("cart-count").textContent = savedCount;
});

function renderProducts(products) {
  const container = document.getElementById("product-list");
  container.innerHTML = "";
  products.forEach(product => {
    const col = document.createElement("div");
    col.className = "col-md-4 mb-4";
    col.innerHTML = `
      <div class="card product-card shadow-sm h-100">
        <div class="card-body text-center">
          <h5 class="card-title">${product.name}</h5>
          <p class="card-text">
            <span class="price">${product.price} TL</span><br>
            <span class="stock">Stok: ${product.stock}</span>
          </p>
          <button class="btn btn-success w-100" onclick="addToCart(${product.id}, '${product.name}')">
            <i class="fas fa-cart-plus"></i> Sepete Ekle
          </button>
        </div>
      </div>
    `;
    container.appendChild(col);
  });
}

function addToCart(productId, productName) {
  const sessionId = getOrCreateSessionId();

  fetch(`http://localhost:8080/api/cart/add?sessionId=${sessionId}&productId=${productId}&quantity=1`, {
    method: "POST"
  })
  .then(() => {
    let count = parseInt(localStorage.getItem("cartCount") || "0");
    count++;
    localStorage.setItem("cartCount", count);
    document.getElementById("cart-count").textContent = count;
    showToast(`${productName} sepete eklendi!`);
  });
}

function showToast(message) {
  const toast = document.createElement("div");
  toast.className = "toast align-items-center text-bg-success border-0 show";
  toast.setAttribute("role", "alert");
  toast.innerHTML = `
    <div class="d-flex">
      <div class="toast-body">${message}</div>
      <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
    </div>
  `;
  document.getElementById("toast-container").appendChild(toast);

  setTimeout(() => {
    toast.remove();
  }, 3000);
}

function filterCategory(category) {
  if (category === 'Tümü') {
    renderProducts(allProducts);
  } else {
    const filtered = allProducts.filter(p => p.category?.toLowerCase() === category.toLowerCase());
    renderProducts(filtered);
  }
}

function getOrCreateSessionId() {
  let sessionId = localStorage.getItem("sessionId");
  if (!sessionId) {
    sessionId = "user-" + Date.now();
    localStorage.setItem("sessionId", sessionId);
  }
  return sessionId;
}
