package co.com.bancolombia.model.statusaccount.gateways;

import co.com.bancolombia.model.statusaccount.StatusAccount;
import reactor.core.publisher.Mono;

public interface StatusAccountGateway {

    Mono<StatusAccount> getStatus(String idInfo);
}
