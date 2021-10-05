/* project transData
создано 15.09.2021 14:55
*/
package mag.tester.transData.configuration;

import com.zaxxer.hikari.HikariDataSource;
import mag.jdbc.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourcesConfigurationSrc {
    @Bean
    @ConfigurationProperties("app.datasource-src")
    public DataSourceProperties dataSourcePropertiesSrc() {
        return new DataSourceProperties();
    }

    @Bean("dataSourceSrc")
    @ConfigurationProperties(prefix = "app.datasource-src")
    public HikariDataSource dataSourceSrc() {
        return dataSourcePropertiesSrc()
                .initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("jdbcTemplateSrc")
    public NamedParameterJdbcTemplate jdbcTemplateSrc()
    {
        HikariDataSource ds = dataSourceSrc();
        return new NamedParameterJdbcTemplate(ds);
    }
    @Bean("jdbcRepositorySrc")
    @Qualifier("jdbcRepositorySrc")
    JdbcRepository jdbcRepositorySrc() {
        NamedParameterJdbcTemplate jdbc = jdbcTemplateSrc();
        return new JdbcRepository(jdbc);
    }

    @Bean
    @Qualifier("transactionManagerSrc")
    DataSourceTransactionManager transactionManagerSrc() {
        HikariDataSource ds = dataSourceSrc();
        return new DataSourceTransactionManager(ds);
    }

}
