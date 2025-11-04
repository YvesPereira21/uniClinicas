package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class V2__Insert_initials_users extends BaseJavaMigration{

    @Override
    public void migrate(Context context) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // --- Criar Utilizador ADMIN ---
        String adminUsername = "admin";
        // Verifica se o utilizador 'admin' já existe
        Integer adminCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tb_usuario WHERE username = ?", Integer.class, adminUsername);

        if(adminCount == null || adminCount == 0) {
            String adminPassword = bCryptPasswordEncoder.encode("admin123");
            jdbcTemplate.update(
                    "INSERT INTO tb_usuario (username, password, role) VALUES (?, ?, ?)",
                    adminUsername,
                    adminPassword,
                    "ADMIN"
            );
        }

    }
}