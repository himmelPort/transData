/* project transData
создано 20.09.2021 19:57
*/
package mag.tester.transData.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemaTableMapper implements RowMapper<SchemaTable> {
    @Override
    public SchemaTable mapRow(ResultSet resultSet, int i) throws SQLException {
        final String tableName = resultSet.getString("tableName");
        return new SchemaTable(tableName);
    }
}
