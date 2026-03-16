# web-crawler
Simple project to crawl web ages through links

## Things to consider
- only crawl links that are in the same domain
- optionally limit depth
- rate limiting
- avoid crawling the same link multiple times (from pages urls or doing cycles)

## Run the app
After compiling with `mvn clean package`, you can run the app the following way:

`java -jar target/web-crawler-1.0-SNAPSHOT.jar <url> <depth optional>`