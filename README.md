# jvsync-javaApi

## Getting started

* You need an installed java 8 sdk

* Build with installed maven `mvn clean install`
* or maven wrapper `./mvnw clean install`
* Run jar with `java -jar target/syncpoc-0.0.1-SNAPSHOT.jar`

## Swagger-UI

* Is only active when you start with the spring profile 'local'
* Start your application with '-Dspring.profiles.active=local'
* Can be accesses via [http://localhost:8080/swagger-ui.html]

## Integration-Tests

* Run with `mvn clean install -Pit`

## Some nice to know things

* PCP-SSO classes or Wiki-Pages:
    * [https://as-wiki.axelspringer.de/display/OCB/45.01+Definition+der+SSO+Felder]
    * For Mapping-Examples
    * Password related:`de.axelspringer.sso.service.util.GenericPasswordEncoder`
    * Password rehashing: `de.axelspringer.sso.service.AccountServiceImpl#authenticateEmailCredentials`
    * Notifications for Login and Registration: `de.axelspringer.sso.flow.action.NotificationAction`

* CAM-Import
    * cf. de.axelspringer.sso.esb.esbrs.endpoint.NotificationRestEndpoint in PCP-SSO