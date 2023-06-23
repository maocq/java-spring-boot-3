package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.repository.account.data.AccountDataDAO;
import co.com.bancolombia.r2dbc.repository.account.data.AccountDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    AccountDataRepository repositoryAdapter;

    @Mock
    AccountDataDAO repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {
        /*

        when(repository.findById("1")).thenReturn(Mono.just("test"));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<Object> result = repositoryAdapter.findById("1");

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
         */
    }
}
