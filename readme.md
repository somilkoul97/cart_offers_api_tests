Zomato Cart Offer Assignment

Project Overview
This project tests the Zomato cart offers feature using MockServer for mocking user segments and RestAssured for validating cart value post-offer application.

Tech Stack
- Java 17
- RestAssured
- TestNG
- MockServer (Dockerized on port 1080)
- Maven

Setup Instructions

1.Start MockServer:
docker-compose up -d

Verify it's running at:
http://localhost:1080
curl --location 'http://localhost:1080/api/v1/cart/apply_offer' \
--header 'Content-Type: application/json' \
--data '{"cart_value":200,"user_id":1,"restaurant_id":1}'


2.Run Tests:
mvn clean test

Test Coverage
The suite covers:
- Flat and percentage offers.
- Invalid segments.
- Boundary cases (zero cart value).
- Decimal handling.
- Large cart discounts.
- No offers scenario.

Total Test Cases: 10

Mocks
User segments are mocked in `/mockserver/initializerJson.json`:
- `user_id: 1` -> `p1`
- `user_id: 2` -> `p2`
- `user_id: 3` -> `p3`
- `user_id: 999` -> invalid.

Files to Note
- `OfferTests.java` – all the tests.
- `docker-compose.yml` – starts MockServer.
- `initializerJson.json` – mocks user segments.
