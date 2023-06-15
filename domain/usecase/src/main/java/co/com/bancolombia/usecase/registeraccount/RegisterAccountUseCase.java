package co.com.bancolombia.usecase.registeraccount;

import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.statusaccount.gateways.StatusAccountGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
public class RegisterAccountUseCase {

    private final StatusAccountGateway statusGateway;

    public Mono<Account> register(String name, String statusId) {
        /*
        return statusGateway.getStatus(statusId)
                .map(status -> Account.newAccount(0, name, status.getStatus()))
                .flatMap(this::finalValidation)
                .flatMap(this::saveAccount);
         */

        return statusGateway.getStatus(statusId)
                .flatMap(status -> Mono.zip(legalValidation(name), disponibilityValidation())
                .map(legal -> Account.newAccount(0, name, status.getStatus())))
                .flatMap(this::finalValidation)
                .flatMap(this::saveAccount);
    }

    private Mono<String> legalValidation(String accountName) {
        return Mono.just(accountName).delayElement(Duration.ofSeconds(2));
    }

    private Mono<Integer> disponibilityValidation() {
        return Mono.just(9).delayElement(Duration.ofSeconds(2));
    }

    private Mono<Account> finalValidation(Account account) {
        return Mono.just(account);
    }

    private Mono<Account> saveAccount(Account account) {
        return Mono.just(account);
    }
}
