# 🛒 Mini Market Stok Takip Paneli Uygulaması

Bu proje, Spring Boot ile geliştirilmiş bir **market stok takip paneli sistemidir**. Kullanıcılar ürünleri sepete ekleyebilir, ödeme işlemi yapabilir ve satış geçmişini görebilir.

## 📌 Projenin Amacı

Marketlerdeki temel satış işlemlerini dijital ortama taşımak, ürün stoğunu kontrol etmek ve satışları kaydederek raporlayabilmek.

## 🎯 Özellikler

- 📦 Ürün listeleme
- ➕ Sepete ürün ekleme
- 🧮 Sepet içeriğini görüntüleme ve silme
- 💳 Ödeme işlemi (stoktan düşme ve satış kaydı)
- 📊 Satış raporu listeleme (sales.html)
- 🎨 Bootstrap ile responsive frontend

## 🛠️ Kullanılan Teknolojiler

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 / MySQL Veritabanı
- HTML / CSS / JavaScript
- Bootstrap 5
- Postman (testler için)

## 🧱 Mimarisi

Proje **MVC (Model-View-Controller)** mimarisi ile yapılandırılmıştır.

- **Model:** Product, Sale, Cart, CartItem
- **Controller:** REST API’leri (`/api/products`, `/api/cart`, `/api/sales`)
- **Service:** İş mantığı (`SaleService`, `CartService`)
- **Repository:** JPA repository'ler (`ProductRepository`, `SaleRepository`)

## 🔄 Dependency Injection

Spring’in bağımlılık enjeksiyonu sayesinde controller ve servis sınıfları arasında gevşek bağlılık sağlanmıştır:

```java
public class SaleController {
  private final SaleService saleService;
  public SaleController(SaleService saleService) {
    this.saleService = saleService;
  }
}
