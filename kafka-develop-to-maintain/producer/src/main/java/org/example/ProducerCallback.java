package org.example;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerCallback implements Callback {
    private ProducerRecord<String, String> record;

    public ProducerCallback(ProducerRecord<String, String> record) {
        this.record = record;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (exception != null) {
            exception.printStackTrace();
        } else {
            System.out.println(
                    String.format("topic: %s, partition: %d, offset: %d",
                            metadata.topic(), metadata.partition(), metadata.offset())
            );
        }
    }
}
