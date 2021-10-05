/* project transData
создано 20.09.2021 22:48
*/
package mag.tester.transData.service;

import mag.jdbc.repository.JdbcRepository;
import mag.tester.transData.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DataAccessServiceSrc {
    private final DataAccessTools daTools;

    @Autowired
    @Qualifier("jdbcRepositorySrc")
    private JdbcRepository jdbcRepository;

    @Value("${app.schemaTable}")
    private String appSchemaTable;

    public DataAccessServiceSrc(DataAccessTools daTools) {
        this.daTools = daTools;
    }

    @PostConstruct
    public void initJdbcRepository() {
        daTools.setAppSchemaTable(appSchemaTable);
        jdbcRepository.setNameCommand("select * from");
    }

    public void transLogClear() {
        daTools.transLogClear();
    }
    public String transLog() {
        return daTools.transLog();
    }

    @Transactional(value="transactionManagerSrc")
    public List<SchemaTable> schema_table() {
        return daTools.schema_table(jdbcRepository);
    }

    @Transactional(value="transactionManagerSrc")
    public boolean selectTableData(String tableName) {
        return daTools.selectTableData(jdbcRepository, tableName);
    }

}
