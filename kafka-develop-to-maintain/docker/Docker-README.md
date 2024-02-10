> 1. docker compose -f docker-compose.yml up -d


> 2. docker compose exec kafka-1 kafka-topics --create --topic test-topic --bootstrap-server kafka-1:9092 --replication-factor 2 --partitions 3