package co.com.bancolombia.mq.reqreply;

import co.com.bancolombia.commons.jms.mq.EnableReqReply;
import co.com.bancolombia.model.services.ReqReplyService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
@AllArgsConstructor
@EnableReqReply
public class SampleMQReqReply implements ReqReplyService {
    private final ReqReplyGateway sender;

    public Mono<String> requestReply(String message) {
        return sender.requestReply(message)
                .map(this::extractResponse);
    }

    @SneakyThrows
    private String extractResponse(Message message) {
        TextMessage textMessage = (TextMessage) message;
        return textMessage.getText();
    }
}
