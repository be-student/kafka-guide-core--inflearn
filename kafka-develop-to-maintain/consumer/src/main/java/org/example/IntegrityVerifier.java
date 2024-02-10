package org.example;

import java.util.Map;

public class IntegrityVerifier {

    public static void verify(final int producedMessageCount, Map<String, String> consumedMessages) {
        long count = consumedMessages.keySet().stream()
                .filter(key -> consumedMessages.get(key).startsWith("[toss]"))
                .count();

        if (count != producedMessageCount) {
            System.out.println(String.format("발행된 메세지 %d개 != 소비한 메세지 %d개 이므로 데이터 유실이 발생했습니다.", producedMessageCount, count));
            throw new IllegalStateException("[DANGER] NOT INTEGRITY DATA");
        }
    }
}
