# Image officielle Java 17 légère et maintenue
FROM eclipse-temurin:17-jre-alpine

# Créer un utilisateur non-root (Exigence sécurité Section 5.2)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copier le .jar généré par Maven
COPY target/*.jar app.jar

# Appliquer les permissions à l'utilisateur non-root
RUN chown -R appuser:appgroup /app
USER appuser

# Port par défaut de Spring Boot
EXPOSE 7071
# 8. Démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
