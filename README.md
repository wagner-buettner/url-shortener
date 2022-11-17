./gradlew clean build -x test
docker build . -t url-shortener:latest
docker-compose up -d

./gradlew clean build -x test && docker build . -t url-shortener:latest && docker-compose up -d
psql -U url-shortener-usr -d url-shortener-db
