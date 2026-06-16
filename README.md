# Warehouse DOM MongoDB Project

This is my small Spring Boot project for the document oriented middleware task.
The app stores warehouse product data in MongoDB.

I kept the project close to the original tutorial code. There is still one main
`Application` class, one model class called `ProductData`, and one Mongo
repository. I did not add a big service layer because this is only the GK part.

## What the app does

The app starts with one warehouse and 10 products.
The products are saved in MongoDB. The products have 3 categories:

- `Getraenk`
- `Waschmittel`
- `Tierfutter`

The warehouse id is `1`. For GK I only use one warehouse, because more
warehouses are part of the advanced requirements.

## Technologies

- Java
- Spring Boot
- Spring Web
- Spring Data MongoDB
- MongoDB
- Gradle
- Docker Desktop
- IntelliJ IDEA

## Short MongoDB explanation

MongoDB is a NoSQL database. It stores data as documents. A document looks a bit
like JSON. This is good for this project because warehouse data can be saved in
a simple document structure and checked in the mongo shell.

In SQL I would normally make tables and relations. In MongoDB I can save data
more directly as product documents.

## REST API

Only the GK endpoints are implemented:

| Method | URL | What it does |
| --- | --- | --- |
| `GET` | `/product` | Shows all products |
| `POST` | `/product` | Adds one product |
| `GET` | `/warehouse` | Shows all products from warehouse `1` |

I did not implement the full endpoint list with `GET /warehouse/{id}`,
`DELETE /warehouse/{id}`, `GET /product/{id}` and `DELETE /product/{id}`.
That belongs to the advanced part in the assignment.

## JSON example

This is an example body for `POST /product`:

```json
{
  "warehouseID": "1",
  "productID": "00-999999",
  "productName": "Test Produkt",
  "productCategory": "Getraenk",
  "productQuantity": 50
}
```

The app saves it as a MongoDB document.

## How I started the project

### Docker

1. I opened Docker Desktop on Windows.
2. I waited until Docker Desktop was fully started.
3. I opened a terminal.
4. I downloaded the MongoDB image:

```bash
docker pull mongo
```

5. I started the MongoDB container:

```bash
docker run -d -p 27017:27017 --name mongo mongo
```

6. If the name `mongo` already exists, I can remove the old container first:

```bash
docker rm -f mongo
```

Then I run the `docker run` command again.

7. I checked if the container is running:

```bash
docker ps
```

There should be a container with the name `mongo`.

### IntelliJ IDEA

1. I opened IntelliJ IDEA.
2. I opened this project folder.
3. I waited until Gradle loaded the project.
4. If Gradle did not load, I clicked the Gradle reload button.
5. I checked the JDK in the project settings. I used JDK 18 or newer.
6. I opened `src/main/java/warehouse/Application.java`.
7. I started the `Application` class with the green run button.

### Spring Boot with Gradle

I can also start it in the terminal:

```bash
gradle clean
gradle bootRun
```

On Windows with the included Gradle wrapper:

```bash
.\gradlew.bat clean
.\gradlew.bat bootRun
```

When the app starts, I check the logs. Spring Boot should start on port `8080`.

### MongoDB shell

I open the mongo shell in the Docker container:

```bash
docker exec -it mongo mongosh
```

Then I use the test database:

```javascript
show dbs
use test
db.productData.find()
```

The collection is called `productData`, because the model class is called
`ProductData`.

### API testing

Show all products:

```bash
curl http://localhost:8080/product
```

Show warehouse data:

```bash
curl http://localhost:8080/warehouse
```

Add one product:

```bash
curl -X POST http://localhost:8080/product ^
  -H "Content-Type: application/json" ^
  -d "{\"warehouseID\":\"1\",\"productID\":\"00-999999\",\"productName\":\"Test Produkt\",\"productCategory\":\"Getraenk\",\"productQuantity\":50}"
```

In the browser I can also open:

```text
http://localhost:8080/product
http://localhost:8080/warehouse
```

## Mongo shell CRUD examples

These commands are for the protocol part.

Create one product:

```javascript
db.productData.insertOne({
  warehouseID: "1",
  productID: "00-123456",
  productName: "Traubensaft Rot",
  productCategory: "Getraenk",
  productQuantity: 70
})
```

Read all products:

```javascript
db.productData.find()
```

Read one product:

```javascript
db.productData.find({ productID: "00-123456" })
```

Update one product:

```javascript
db.productData.updateOne(
  { productID: "00-123456" },
  { $set: { productQuantity: 99 } }
)
```

Delete one product:

```javascript
db.productData.deleteOne({ productID: "00-123456" })
```

## Development log

- I checked the original tutorial project structure.
- I kept the simple `Application`, `ProductData`, `WarehouseRepository` setup.
- I changed the start data to one warehouse.
- I added 10 products in 3 categories.
- I added the GK REST endpoints.
- I did not add advanced endpoints or test data generators.

## Problems / issues

- MongoDB must run before starting the Spring Boot app.
- If the Docker container name already exists, the start command fails.
- The app deletes old product data on every start because the tutorial code also
  uses `repository.deleteAll()`.

## What was changed

- `Application.java`: added the simple REST endpoints and changed the demo data.
- `README.md`: added this start guide and the GK documentation.

## Theory questions

4 advantages of NoSQL compared to SQL:

- Documents can be flexible.
- JSON-like data is easy to save.
- It can be easier to change the structure.
- Some NoSQL databases can scale well.

4 disadvantages of NoSQL compared to SQL:

- Relations are not as strict as in SQL.
- Joins are not used in the same way.
- Data can become duplicated.
- Transactions and consistency can be harder, depending on the database.

Difficulties when data is merged:

- Different warehouses can send different formats.
- Product ids must match.
- Old and new stock data can be mixed up.
- Time stamps are important so the newest data is clear.

Types of NoSQL databases and examples:

- Document database: MongoDB
- Key-value database: Redis
- Column database: Cassandra
- Graph database: Neo4j

CAP theorem:

- CA means consistency and availability.
- CP means consistency and partition tolerance.
- AP means availability and partition tolerance.

Command for stock of one product in all warehouses:

```javascript
db.productData.find({ productID: "00-871895" })
```

Command for stock of one product in one warehouse:

```javascript
db.productData.find({ warehouseID: "1", productID: "00-871895" })
```

## GK status

Done:

- MongoDB with Spring Data MongoDB
- JSON document structure with `ProductData`
- One warehouse
- 10 products
- 3 product categories
- `POST /product`
- `GET /product`
- `GET /warehouse`
- Mongo shell commands for CRUD in the README

Not done:

- More than one warehouse
- Full REST API with delete and id endpoints
- Test data generator
- Reporting or AI features

These are not done because they are not needed for GK.
