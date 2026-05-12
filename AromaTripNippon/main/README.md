# AromaTripNippon Phase1

Spring Boot implementation for the Phase1 scope under `AromaTripNippon/main`.

## Scope

- Public pages: top, experience detail, concept, reservation form, reservation complete
- Management pages: login, dashboard, reservations, customers, fragrance recipes, products, inventory, account settings
- Database tables: customers, experience_programs, reservations, fragrance_recipes, fragrance_recipe_materials, inventory_items, inventory_transactions, products, admin_users, audit_logs
- Excluded: EC order flow, cart, my page, reviews, gallery, localization management, image uploads

## Run

```bash
mvn spring-boot:run
```

The default profile uses H2:

- App: http://localhost:8080/
- Management: http://localhost:8080/management/login
- H2 console: http://localhost:8080/h2-console
- Initial admin: `Adm01 / password`

For MySQL, update `src/main/resources/application-mysql.properties` and run with:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```
