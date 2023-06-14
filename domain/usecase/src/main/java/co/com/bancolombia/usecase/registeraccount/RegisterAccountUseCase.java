package co.com.bancolombia.usecase.registeraccount;

import co.com.bancolombia.model.account.Account;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterAccountUseCase {

    public Mono<Account> register(String name) {
        return Mono.fromSupplier(() -> Account.newAccount(0, name))
                .flatMap(this::otherValidation);
    }

    private Mono<Account> otherValidation(Account account) {
        return Mono.just(account);
    }
}
