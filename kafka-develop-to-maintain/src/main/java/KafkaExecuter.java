import org.example.Consumer;
import org.example.IntegrityVerifier;
import org.example.Producer;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

public class KafkaExecuter {
    public static void main(String[] args) {
        // 1억부터 10억까지 랜덤 난수 발생
        int producedMessageCount = new Random().nextInt(1_000_000_000) + 100_000_000;
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        System.out.println(String.format("발행할 데이터 개수 : %s개", df.format(producedMessageCount)));

        Producer producer = new Producer();
        producer.produce(producedMessageCount);

        Consumer consumer = new Consumer();
        Map<String, String> consumedMessage = consumer.consume();
        IntegrityVerifier.verify(producedMessageCount, consumedMessage);
    }

}
