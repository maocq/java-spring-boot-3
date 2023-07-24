package co.com.bancolombia.config;

import co.com.bancolombia.r2dbc.config.PostgresqlConnectionProperties;
import co.com.bancolombia.secretsmanager.api.GenericManager;
import co.com.bancolombia.secretsmanager.api.GenericManagerAsync;
import co.com.bancolombia.secretsmanager.config.AWSSecretsManagerConfig;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnector;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnectorAsync;
import lombok.SneakyThrows;
import software.amazon.awssdk.regions.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretsConfig {

  @Bean
  public GenericManager getSecretManager(@Value("${aws.region}") String region) {
    return new AWSSecretManagerConnector(region);
  }

  @Bean
  public GenericManagerAsync getSecretManagerAsync(@Value("${aws.region}") String region) {
    return new AWSSecretManagerConnectorAsync(getConfig(region));
  }

  @Bean
  @SneakyThrows
  public PostgresqlConnectionProperties getConnectionProperties(GenericManager genericManager, @Value("${aws.secretName}") String secretName) {
    return genericManager.getSecret(secretName, PostgresqlConnectionProperties.class);
  }

  /*
  @Bean
  @SneakyThrows
  public PostgresqlConnectionProperties getConnectionPropertiesAsync(GenericManagerAsync genericManagerAsync, @Value("${aws.secretName}") String secretName) {
    return genericManagerAsync.getSecret(secretName, PostgresqlConnectionProperties.class).block();
  }
   */

  private AWSSecretsManagerConfig getConfig(String region) {
    return AWSSecretsManagerConfig.builder()
      .region(Region.of(region))
      .cacheSize(5) // TODO Set cache size
      .cacheSeconds(3600) // TODO Set cache seconds
      .build();
  }
}
