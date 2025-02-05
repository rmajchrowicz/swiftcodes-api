# SWIFT Codes API
Spring Boot application for storing and retrieving SWIFT codes.
## API Endpoints 
**GET /v1/swift-codes/{swiftCode}** - Returns the SWIFT code details

**GET /v1/swift-codes/country/{countryISO2}** - Returns all SWIFT codes for a specific country.

**POST /v1/swift-codes** - Adds a new SWIFT code to the database.

**DELETE /v1/swift-codes/{swiftCode}** - Deletes the SWIFT code record from the database.

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