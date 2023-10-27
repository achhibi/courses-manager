#OpenJDK 8
FROM openjdk:8-jre-slim

# répertoire de travail
WORKDIR /app

# Copiez le fichier JAR de votre application dans l'image Docker
COPY target/courses-manager.jar /app/courses-manager.jar

# Exposez le port sur lequel votre application écoute
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "courses-manager.jar"]