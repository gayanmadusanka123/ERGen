services:
    postgres-db:
        image: postgres:18
        container_name: ${database.name}
        ports:
            - "5433:5432"
        environment:
            POSTGRES_USER: ${database.username}
            POSTGRES_PASSWORD: ${database.password}
            POSTGRES_DB: ${database.name}
        volumes:
            - postgres_data:/var/lib/postgresql

volumes:
    postgres_data:
