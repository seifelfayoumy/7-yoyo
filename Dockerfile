FROM openjdk:25-ea-4-jdk-oraclelinux9

WORKDIR /app

COPY /target/*.jar target/app.jar

VOLUME /data

# Copy all data files to the volume
COPY ./src/main/java/com/example/data/users.json /data/users.json
COPY ./src/main/java/com/example/data/products.json /data/products.json
COPY ./src/main/java/com/example/data/orders.json /data/orders.json
COPY ./src/main/java/com/example/data/carts.json /data/carts.json

# Set environment variables for all data paths
ENV spring.application.userDataPath=/data/users.json
ENV spring.application.productDataPath=/data/products.json
ENV spring.application.orderDataPath=/data/orders.json
ENV spring.application.cartDataPath=/data/carts.json

ENV spring.application.name=MiniProject1-Team-7-yoyo

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/app.jar"]