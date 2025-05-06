package ar.com.acme.bootstrap.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.acme.commons.Properties;

@Configuration
public class DatabaseConfig {
    private final String H2_PORT;

    public DatabaseConfig(Properties properties) {
        this.H2_PORT = properties.getDatabase().get("h2_port");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException {
        return Server.createTcpServer(
        "-tcp", "-tcpAllowOthers", "-tcpPort", H2_PORT);
    }
}
