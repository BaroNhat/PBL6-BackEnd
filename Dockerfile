FROM ubuntu:latest
LABEL authors="NhatNguyen"

ENTRYPOINT ["top", "-b"]
# Sử dụng base image JDK 17 (hoặc phiên bản phù hợp với Spring Boot của bạn)
FROM eclipse-temurin:17-jdk

# Tạo thư mục làm việc
WORKDIR /app

# Sao chép file JAR vào container
COPY target/UNIME-0.0.1-SNAPSHOT.jar app.jar

# Expose port (thay 8080 bằng port bạn muốn nếu cần)
EXPOSE 8080

# Chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
