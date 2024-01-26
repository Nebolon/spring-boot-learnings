<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paketstruktur - Ein Leitfaden</title>
</head>
<body>
    <h1>Package Struktur - Fangen wir einmal oben an.</h1>
    <ul>
        <li>config - Wir wollen hier Konfigurationen lagern.</li>
        <li>controller - Hier kommen unsere Endpunkte rein.</li>
        <li>dto - Data Transfer Objects</li>
        <li>entity - Entitäten</li>
        <li>repository - Hier kommen die Interfaces rein, damit können wir auf die Datenbank zugreifen.</li>
        <li>service - Hier kommen Services rein, mit dieser Logik kann man vieles, aber auch wirklich sehr vieles machen.</li>
    </ul>
    <h2>API Dokumentation</h2>
    <p><a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a></p>
    <h2>Links zu lesen:</h2>
    <ul>
        <li><a href="https://projectlombok.org/features/Data">Lombok</a></li>
        <li><a href="https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html">Spring Boot Query</a></li>
    </ul>
    <h2>Interessante Links: (Optional)</h2>
    <ul>
        <li><a href="https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d">
            Best Practices: Entity Class Design with JPA and Spring Boot
        </a></li>
        <li><a href="https://medium.com/javarevisited/10-spring-boot-best-practices-to-learn-in-10-minutes-b4ea307b2a84">
            10 Spring Boot Best Practices to Learn in 10 Minutes
        </a></li>
        <li><a href="https://www.baeldung.com/spring-rest-openapi-documentation">
            Documenting a Spring REST API Using OpenAPI 3.0
        </a></li>
    </ul>
    <h1>Wer bis hierhin gelesen hat, der erfährt ein Geheimnis:</h1>
    <p>Wir können mal darüber nachdenken unsere Tests mit Hilfe von ChatGPT zu machen, dabei kann uns ChatGPT 90 % der Arbeit abnehmen.</p>
</body>
</html>