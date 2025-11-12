package projetos.uniClinicas.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlywayConfig {

    @Bean
    @Profile("development") // Ativa esta estratégia apenas quando o perfil "development" estiver ativo
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        // Retorna uma implementação da estratégia.
        // A expressão "flyway -> { ... }" é uma forma curta (lambda) de escrever o código.
        return flyway -> {
            // 1. Limpa o banco de dados, apagando todas as tabelas.
            flyway.clean();

            // 2. Executa as migrações (V1, V2, etc.) para recriar o banco do zero.
            flyway.migrate();
        };
    }
}
