package co.com.bancolombia.model.services;

import reactor.core.publisher.Mono;

public interface ReqReplyService {

    Mono<String> requestReply(String message);
}
