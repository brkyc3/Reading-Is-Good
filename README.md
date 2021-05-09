# RISGOOD

Kitap icin siparis girilen, siparislerin listelenebildigi bu islemlere paralel kitap stoklarinin guncellendigi **OrderService** ,

Musterilerin kaydedildigi **CustomerService** seklinde 2 servis iceren bir uygulamadir.

## Gereksinimler
* java11+
* mongoDB4+ (stok sayilarini siparise gore azaltirken transactionlar kullanildigi replika set ile calistirilmalidir)

## Kullanilan Araclar

* Spring Boot, Spring Web, Spring Security (Kullanici tabanli Basic Auth), Spring Data MongoDB
* Lombok
* MapStruct
* Swagger 2


## Ayaga kaldirma
`mongod   --replSet rs0` ve ardindan `mongo --eval "rs.initiate()"` ile replika set olusturulur ve mongoDB baslatilir.

Baglanti bilgisi gerekirse **application.properties** altindan alttaki property ile degistirilebilir.

`spring.data.mongodb.uri=mongodb://127.0.0.1:27017/readingisgood?replicaSet=rs0`

Servisler maven olarak herhangi bir ideye cekilebilir, ya da

`mvn clean install` calistirilarak target altinda olusan jarlar `java -jar ...` seklinde ayaga kaldirilabilir.

`http://localhost:8080/api/orders` order service

`http://localhost:8081/api/customers` customer service

istenirse application.properties altindan bu portlar `server.port=xxxx` seklinde guncellenebilir

Order servis swagger ui [OrderService](http://localhost:8080/swagger-ui.html)

Customer servis swagger ui [CustomerService](http://localhost:8081/swagger-ui.html)


## Kullanim

Postman collectioni import edilerek oncelikle ornek musteri kaydetme islemi yapilabilir, musteri kaydetme authentication gerektirmiyor.Devaminda yine postman ornek requestleri kullanilarak kaydedilen musterinin emaili ve sifresi ile basic authenticationla order servisi cagrilabilir.


Musteri kaydetme islemi `http://localhost:8081/api/customers` **saveCustomerRequest** ile yapilir.

Sonrasinda `http://localhost:8080/api/orders/stock` **saveStock** ile kitap stoklari kaydedilmelidir. Id alani yeni kayitlarda bos, guncellemelerde dolu gonderilir. 

Kaydedilen tum kitaplar `http://localhost:8080/api/orders/stock` **retrieveAllBooks** **GET** istegi ile listenebilir.

Buradaki id ve kaydedilen musteriden alinan idler ile `http://localhost:8080/api/orders` **newOrderRequest** siparis giris islemi yapilabilir.

*(Siparis girisinde stoktaki kitaplari duserken tutarsizlik olusmamasi icin bu islem transaction icersinde gerceklestirildi.)*

Kaydedilen siparis idsi ile detaylari `http://localhost:8080/api/orders/xxxxxxxxx` seklinde **getOrderDetails** ile sorgulanabilir.Burada siparis statusu stok yeterli ise **STOCK_OK** yetersiz ise **STOCK_CONTROL** olarak kaydedilir.

Musteriye ait tum siparisler ozet olarak `http://localhost:8080/api/orders/customer/xxxxxxxxxxxxxx` seklinde musterinin idsi ile sorgulanabilir.

MongoDB'den kaydedilen datalar **readingisgood** DB altinda **books**,**orders** ve **customers** collection'larindan ayrica incelenebilir. 

Uygulamalarin bastigi loglar, uygulama path'i altindaki app.log dosyalarindan takip edilebilir.

## Responselar
Basarili ekleme islemleri eklenen kaydin idsini donuyor, sistemsel hatalar **RISGOOD_SYSTEM** , business hatalar ise alttaki kodlardan biriyle atiliyor.Yeni bir hata icin hangi http status ile donulmesi isteniyorsa ErrorCode enuma tanimlanarak kullanilabilir.
```java
   
        RISGOOD_STOCK_NOT_ENOUGH(HttpStatus.NOT_FOUND),
        RISGOOD_CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND),
        RISGOOD_BOOK_NOT_FOUND(HttpStatus.NOT_FOUND),
        RISGOOD_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND),
        RISGOOD_SYSTEM(HttpStatus.INTERNAL_SERVER_ERROR),
        RISGOOD_VALIDATION(HttpStatus.BAD_REQUEST);
  ```
  Ornek hatalar
  
  (404)
   
  ```
  {
    "code": "RISGOOD_STOCK_NOT_ENOUGH",
    "name": "NotFoundResponse",
    "errorMessage": "Not enough stock for bookId 609673f3e6cec26ee1910660",
    "time": "2021-05-09T08:59:47.0899165"
}
```

(400)

```
{
    "code": "RISGOOD_VALIDATION",
    "name": "InputNotValidResponse",
    "errorMessage": "[Field error in object 'orderRequest' on field 'orderItems[0].bookId': rejected value [null]]",
    "time": "2021-05-09T09:06:42.378364"
}
  ```
