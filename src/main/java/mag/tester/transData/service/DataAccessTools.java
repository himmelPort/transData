/* project transData
создано 29.09.2021 11:29
*/
package mag.tester.transData.service;

import mag.jdbc.repository.JdbcRepository;
import mag.tester.mapper.config.AccessData;
import mag.tester.transData.models.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@ComponentScan({"mag.tester.mapper.config"})
public class DataAccessTools {
    private final AccessData accessData;
    private String appSchemaTable;

    public DataAccessTools(AccessData accessData) {
        this.accessData = accessData;
    }

    @PostConstruct
    public void DataAccessToolsConfig() {
        accessData.accessDataConfig();
    }

    public void setAppSchemaTable(String appSchemaTable) {
        this.appSchemaTable = appSchemaTable;
    }

    public String transLog() {
        return accessData.transLog();
    }
    public void transLogClear() {
        accessData.transLogClear();
    }

    public List<SchemaTable> schema_table(JdbcRepository jdbcRepository) {
        return jdbcRepository.procedure("schema_table")
                .param(appSchemaTable)
                .jdbcSelect(SchemaTableMapper::new);
    }
    public boolean selectTableData(JdbcRepository jdbcRepository, String tableName) {
        return accessData.selectTableData(jdbcRepository, tableName);
    }
    public boolean insertTableData(JdbcRepository jdbcRepository) {
        return  accessData.insertTableData(jdbcRepository);
    }

}
