package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.request.RegisterAccountRequest;
import co.com.bancolombia.model.account.gateways.AccountRepository;
import co.com.bancolombia.model.services.ReqReplyService;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.services.ReqReplyServiceFixedQueue;
import co.com.bancolombia.usecase.registeraccount.RegisterAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.INVALID_REQUEST;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterAccountUseCase registerAccountUseCase;

    private final ReqReplyService reqReplyService;
    private final ReqReplyServiceFixedQueue reqReplyServiceFixedQueue;
    private final AccountRepository accountRepository;

    public Mono<ServerResponse> listenRegisterAccount(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RegisterAccountRequest.class)
                .switchIfEmpty(Mono.error(() -> new BusinessException(INVALID_REQUEST)))
                .flatMap(request ->
                        registerAccountUseCase.register(request.getName(), request.getStatusId()))
                .flatMap(account -> ServerResponse.ok().bodyValue(account));
    }

    public Mono<ServerResponse> listenGETRequestReply(ServerRequest serverRequest) {
        var message = serverRequest.queryParam("m").orElse("Hello");

        return reqReplyService.requestReply(message)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> listenGETRequestReplyFixedQueue(ServerRequest serverRequest) {
        var message = serverRequest.queryParam("m").orElse("Hello");

        return reqReplyServiceFixedQueue.requestReply(message)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> listenDbUseCase(ServerRequest serverRequest) {
        var id = serverRequest.queryParam("id").orElse("4000");

        return Mono.fromSupplier(() -> Long.valueOf(id))
                .flatMap(accountRepository::findById)
                .flatMap(account -> ServerResponse.ok().bodyValue(account))
                .switchIfEmpty(Mono.defer(() -> ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // usecase.logic();
        return ServerResponse.ok().bodyValue("");
    }
}
