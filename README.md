./gradlew clean build -x test
docker build . -t url-shortener:latest
docker-compose up -d

./gradlew clean build -x test && docker build . -t url-shortener:latest && docker-compose up -d
