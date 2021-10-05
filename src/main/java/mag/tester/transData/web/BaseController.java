/* project transData
создано 15.09.2021 15:10
*/
package mag.tester.transData.web;

import mag.tester.transData.models.SchemaTable;
import mag.tester.transData.service.DataAccessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"depositSession"})
@RequestMapping("")
public class BaseController {
    private final DataAccessService daService;

    public BaseController(DataAccessService daService) {
        this.daService = daService;
    }

    @ModelAttribute("depositSession")
    public DepositSession initDepositSession() {
        return new DepositSession();
    }

    @GetMapping("")
    public String primary(
            Model model) {
        return "primary";
    }
    @GetMapping("/transTables")
    public String transTables(
            @ModelAttribute("depositSession") DepositSession depositSession,
            HttpServletRequest request, Model model) {

        List<SchemaTable> dsSource = daService.schema_table_src(depositSession.isdepositSchema());
        List<SchemaTable> dsTarget = daService.schema_table_trg(depositSession.isdepositSchema());
        model.addAttribute("dsSource", dsSource);
        model.addAttribute("dsTarget", dsTarget);
        depositSession.setdepositSchema(false);
        return "transTables";
    }

    @PostMapping("/transCompleteActual")
    public String transCompleteActual(
            @ModelAttribute("depositSession") DepositSession depositSession,
            @RequestParam Map<String, String> allParams,
            HttpServletRequest request, Model model) throws SQLException {
        String action = allParams.get("action");
        List<String> applyTableTransfer;

        if (action.equalsIgnoreCase("EXEC")) {
//  передаются только установленные checked
            applyTableTransfer = daService.collectCheck(allParams);
            daService.markForTransfer(applyTableTransfer);
            depositSession.setdepositSchema(true);
            daService.applyTransform(applyTableTransfer);
        }
        return "redirect:/transTables";
    }
    @GetMapping("/transCompleteCard")
    public String transCompleteCard(
            HttpServletRequest request, Model model) {


        return "transCompleteCard";
    }

    @PostMapping("/cardxml")
    public String actorStatusModify(
            @RequestParam Map<String, String> allParams,
            HttpServletRequest request) {
        String action = allParams.get("action");

        if (action.equalsIgnoreCase("SES")) {

        }

        return "";
    }

}
