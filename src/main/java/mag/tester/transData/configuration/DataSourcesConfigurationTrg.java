/* project transData
создано 15.09.2021 14:48
*/
package mag.tester.transData.configuration;

import com.zaxxer.hikari.HikariDataSource;
import mag.jdbc.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourcesConfigurationTrg {
    @Primary
    @Bean
    @ConfigurationProperties("app.datasource-trg")
    public DataSourceProperties dataSourcePropertiesTrg() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("dataSourceTrg")
    @ConfigurationProperties(prefix = "app.datasource-trg")
    public HikariDataSource dataSourceTrg() {
        return dataSourcePropertiesTrg()
                .initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public NamedParameterJdbcTemplate jdbcTemplateTrg()
    {
        HikariDataSource ds = dataSourceTrg();
        return new NamedParameterJdbcTemplate(ds);
    }
//    @Primary
    @Bean
    @Qualifier("jdbcRepositoryTrg")
    JdbcRepository jdbcRepositoryTrg() {
        NamedParameterJdbcTemplate jdbc = jdbcTemplateTrg();
        return new JdbcRepository(jdbc);
    };

    @Primary
    @Bean
    @Qualifier("transactionManagerTrg")
    DataSourceTransactionManager transactionManagerTrg() {
        HikariDataSource ds = dataSourceTrg();
        return new DataSourceTransactionManager(ds);
    }
}
