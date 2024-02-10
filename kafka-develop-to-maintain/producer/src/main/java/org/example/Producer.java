package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;


public class Producer {
    private final String topic;
    private final Properties properties;

    public Producer() {
        this.topic = "test-topic";
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "PLAINTEXT://localhost:29092,PLAINTEXT://localhost:39092,PLAINTEXT://localhost:49092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "32768"); // 32KB
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1000"); // 배치 버퍼 1초
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        this.properties = properties;
    }

    public void produce(final int messageCount) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < messageCount; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, String.format("[toss] %d: hello world!!", i), UUID.randomUUID().toString());
            producer.send(record);

        }
        producer.close();
    }

}
