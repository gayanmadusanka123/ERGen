spring.datasource.url=jdbc:postgresql://${database.host}:${database.port}/${database.name}
server.port=8081
spring.datasource.username=${database.username}
spring.datasource.password=${database.password}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true