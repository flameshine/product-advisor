# product-advisor

An implementation of a simple voice assistant, which recognizes the input from the microphone and returns matched products from the database to purchase. In addition, the system provides support for user authentication and registration.

Basically, the project was created for *educational* purposes.

Technologies involved:

- WEB, authentication, main backend logic/structure, persistence, validation: Java 17 & Spring Framework
- Voice recording part: JS with Media Recording API
- Media file conversion (WebM -> WAV): AWS CDK, AWS Lambda, AWS API Gateway
- Speech recognition: GCP Speech-to-Text & Sphinx4 (deprecated)
- Storage: MySQL
- Front-end: HTML, CSS, Bootstrap 5, Thymeleaf
- Auxiliary: Guava, Lombok

The CDK part description can be found here: [source](cdk-conversion-lambda/README.md)
