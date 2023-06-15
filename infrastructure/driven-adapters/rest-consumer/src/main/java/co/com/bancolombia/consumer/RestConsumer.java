package co.com.bancolombia.consumer;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.statusaccount.StatusAccount;
import co.com.bancolombia.model.statusaccount.gateways.StatusAccountGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.ACCOUNT_FIND_ERROR;
import static co.com.bancolombia.model.exceptions.message.TechnicalErrorMessage.TECHNICAL_RESTCLIENT_ERROR;

@Service
@RequiredArgsConstructor
public class RestConsumer implements StatusAccountGateway {

    private final WebClient client;

    public Mono<StatusAccount> getStatus(String id) {
        return client
                .get()
                .uri("/v3/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new BusinessException(ACCOUNT_FIND_ERROR)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new TechnicalException(TECHNICAL_RESTCLIENT_ERROR)))
                .bodyToMono(StatusAccountDto.class)
                .map(dto -> StatusAccount.builder().status(dto.getStatus()).build())
                .onErrorMap(WebClientRequestException.class,
                        ex -> new TechnicalException(ex, TECHNICAL_RESTCLIENT_ERROR));
    }

    /*
    public Mono<ObjectResponse> testPost() {

        ObjectRequest request = ObjectRequest.builder()
            .val1("exampleval1")
            .val2("exampleval2")
            .build();

        return client
            .post()
            .body(Mono.just(request), ObjectRequest.class)
            .retrieve()
            .bodyToMono(ObjectResponse.class);
    }
     */
}