package by.chegodaev.receipt.controller;

import by.chegodaev.receipt.check.CashReceipt;
import by.chegodaev.receipt.data.InputData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class CashReceiptController {

    @GetMapping("/receipt")
    public String getCheck(HttpServletRequest request, Model model) throws ServletException, IOException {
        String[] itemId = request.getParameterValues("itemId");
        String[] quantity = request.getParameterValues("quantity");

        if (itemId.length!=quantity.length){
            return "error";
        }

        InputData inputData = new InputData(itemId, quantity);
        CashReceipt cashReceipt = new CashReceipt(inputData.getMapInputData());
        model.addAttribute("receipt", cashReceipt.getReceipt());

        return "cashReceipt";
    }
}
