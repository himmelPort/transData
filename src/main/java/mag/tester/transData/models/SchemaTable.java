/* project transData
создано 20.09.2021 19:41
*/
package mag.tester.transData.models;

public class SchemaTable {
    private String tableName;
    private Boolean includedTrans;

    public SchemaTable(String tableName) {
        this.tableName = tableName;
        this.includedTrans = false;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Boolean getIncludedTrans() {
        return includedTrans;
    }

    public void setIncludedTrans(Boolean includedTrans) {
        this.includedTrans = includedTrans;
    }

    @Override
    public String toString() {
        return "SchemaTable{" +
                "tableName='" + tableName + '\'' +
                ", includedTrans=" + includedTrans +
                '}';
    }
}
