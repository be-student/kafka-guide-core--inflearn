package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.util.Collections.singleton;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

public class Consumer {

    private final String topic;
    private final Properties properties;

    public Consumer() {
        topic = "test-topic";
        Properties properties = new Properties();
        // PLAINTEXT://localhost:29092
        // 192.168.1.100:9091,192.168.1.100:9092,192.168.1.100:9093
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "PLAINTEXT://localhost:29092,PLAINTEXT://localhost:39092,PLAINTEXT://localhost:49092");
        properties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(GROUP_ID_CONFIG, "group-4");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.offset.reset", "latest");
        this.properties = properties;
    }

    public Map<String, String> consume() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(singleton(topic));
        Map<String, String> consumedMessage = new HashMap<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(50000));
            records.forEach(record -> consumedMessage.put(record.key(), record.value()));
            if (records.isEmpty()) {
                break;
            }
        }
        return consumedMessage;
    }

    public void consume1() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(singleton(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(50000));
            records.forEach(record -> System.out.println(
                    String.format("key: %s, value: %s, partition: %d, offset: %d",
                            record.key(), record.value(), record.partition(), record.offset())
            ));
            consumer.commitSync();
        }
    }
}
