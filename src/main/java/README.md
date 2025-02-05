# SWIFT Codes API
Spring Boot application for storing and retrieving SWIFT codes.
## API Endpoints 
GET /v1/swift-codes/{swiftCode}

GET /v1/swift-codes/country/{countryISO2}

POST /v1/swift-codes

DELETE /v1/swift-codes/{swiftCode}

## Start-up instruction:
Clone repository:
```bash
git clone https://github.com/rmajchrowicz/swiftcodes-api.git
cd swiftcodes-api
```
Build project:
```bash
mvn clean package
docker build -t swiftcodes-app .
```
Run Docker:
```bash
docker-compose up --build
```
The application runs on http://localhost:8080

## Testing
The application includes unit and integration tests. To use them, use the following command:
```bash
./mvnw test
```