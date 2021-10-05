/* project transData
создано 21.09.2021 18:20
*/
package mag.tester.transData.service;

import mag.tester.transData.models.SchemaTable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataAccessService {
    private final DataAccessServiceSrc dataAccessServiceSrc;
    private final DataAccessServiceTrg dataAccessServiceTrg;

    private static List<SchemaTable> schemaTableSrc;
    private static List<SchemaTable> schemaTableTrg;

    public DataAccessService(DataAccessServiceSrc dataAccessServiceSrc, DataAccessServiceTrg dataAccessServiceTrg) {
        this.dataAccessServiceSrc = dataAccessServiceSrc;
        this.dataAccessServiceTrg = dataAccessServiceTrg;
    }

    public List<SchemaTable> schema_table_src(boolean depositSchema) {
        if (!depositSchema)
          schemaTableSrc = dataAccessServiceSrc.schema_table();
        return schemaTableSrc;
    }
    public List<SchemaTable> schema_table_trg(boolean depositSchema) {
        if (!depositSchema)
            schemaTableTrg = dataAccessServiceTrg.schema_table();
        return schemaTableTrg;
    }

    public void applyTransform(List<String> nameTables) {
        dataAccessServiceSrc.transLogClear();
        for (String table : nameTables) {
                if (dataAccessServiceSrc.selectTableData(table))
                    dataAccessServiceTrg.insertTableData();
        }
System.out.println(dataAccessServiceSrc.transLog());
    }

    public void markForTransfer(List<String> apply) {
        schemaTableSrc.forEach(s -> s.setIncludedTrans(apply.stream()
                .anyMatch(a -> a.equalsIgnoreCase(s.getTableName()))
        ));
    }

//  candidate to web tools
    public List<String> collectCheck(Map<String, String> params) {
        return params.entrySet().stream()
                .filter(v -> v.getValue().equalsIgnoreCase("on"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
