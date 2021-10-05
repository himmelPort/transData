/* project transData
создано 20.09.2021 22:48
*/
package mag.tester.transData.service;

import mag.jdbc.repository.JdbcRepository;
import mag.tester.transData.models.SchemaTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.List;

@Service
public class DataAccessServiceTrg {
    private final DataAccessTools daTools;

    @Autowired
    @Qualifier("jdbcRepositoryTrg")
    private JdbcRepository jdbcRepository;

    @Value("${app.schemaTable}")
    private String appSchemaTable;

    public DataAccessServiceTrg(DataAccessTools daTools) {
        this.daTools = daTools;
    }

    @PostConstruct
    public void initJdbcRepository() {
        daTools.setAppSchemaTable(appSchemaTable);
        jdbcRepository.setNameCommand("call");
    }

    @Transactional(value="transactionManagerTrg")
    public List<SchemaTable> schema_table() {
        return daTools.schema_table(jdbcRepository);
    }

    @Transactional(value="transactionManagerTrg")
    public boolean insertTableData() {
        return daTools.insertTableData(jdbcRepository);
    }
}
