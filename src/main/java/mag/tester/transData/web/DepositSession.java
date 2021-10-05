/* project transData
создано 27.09.2021 12:37
*/
package mag.tester.transData.web;

public class DepositSession {
    private boolean depositSchema;

    public DepositSession() {
        this.depositSchema = false;
    }

    public boolean isdepositSchema() {
        return depositSchema;
    }

    public void setdepositSchema(boolean depositSchema) {
        this.depositSchema = depositSchema;
    }
}
