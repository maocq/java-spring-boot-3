package co.com.bancolombia.model.account;

import co.com.bancolombia.model.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.ACCOUNT_VALIDATION_ERROR;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Account {

    private final long id;
    private final String name;

    public static Account newAccount(long id, String name) {
        if (name.equals("error"))
            throw new BusinessException(ACCOUNT_VALIDATION_ERROR);

        return new Account(id, name);
    }
}
