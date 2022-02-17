package by.chegodaev.receipt.check;

import by.chegodaev.receipt.data.ProductAndCardData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class CashReceipt {

    private Map<String, String> inputDataMap;
    private ProductAndCardData productAndCardData = new ProductAndCardData();
    private ArrayList<String> arrayListReceipt = new ArrayList<>();
    private int id_product;
    private int quantity;
    private int id_card;
    private double sum;

    public CashReceipt(Map<String, String> inputDataMap) {
        this.inputDataMap = inputDataMap;
    }

    public ArrayList<String> getReceipt(){
        getHeaderReceipt();

        for(String itemId : inputDataMap.keySet()){
            String str_quantity = inputDataMap.get(itemId);
            if (!itemId.equals("card")){
                id_product = stringToInteger(itemId);
                quantity = stringToInteger(str_quantity);
                double price = Double.parseDouble(productAndCardData.productMap.get(id_product)[1]);
                if(quantity <= 5) {
                    arrayListReceipt.add(String.format(" %-2s %-12s %8s %8s", quantity,productAndCardData.productMap.get(id_product)[0],
                            "$" + price, "$" + price * quantity));
                    sum = sum + (price * quantity);
                } else {
                    double priceWithPromo = Math.round((price - price / 10) * 1e2) / 1e2;
                    arrayListReceipt.add(String.format(" %-2s %-12s %8s \n %-2s %-12s %8s %8s", quantity, productAndCardData.productMap.get(id_product)[0],
                            "$" + price, "", "action -10%", "$" + priceWithPromo, "$" + Math.round(priceWithPromo * quantity * 1e2) / 1e2));
                    sum = sum + (Math.round((price * quantity - price * quantity / 10) * 1e2) / 1e2);

                }
            } else {
                id_card = stringToInteger(str_quantity);
            }
        }

        getFooterReception();

        return arrayListReceipt;
    }

    private void getHeaderReceipt (){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        arrayListReceipt.add(String.format("%23s", "CASH RECEIPT"));
        arrayListReceipt.add(String.format("%25s", "supermarket 24/7"));
        arrayListReceipt.add(String.format("%30s", "152, Independence, StarWars"));
        arrayListReceipt.add(String.format("%29s", "phone: +375(44) 753 44 99"));
        arrayListReceipt.add(String.format("%-17s %-14s", "CASHIER №" + (int)(Math.random()*10000),"DATE: " + dateFormat.format(new Date())));
        arrayListReceipt.add(String.format("%17s %-14s", "", "TIME: " + timeFormat.format(new Date())));
        arrayListReceipt.add(String.format("%-3s %-12s %8s %8s","QTI","DESCRIPTION", "PRICE", "TOTAL"));
        arrayListReceipt.add(String.format("----------------------------------"));
    }

    private void getFooterReception(){
        arrayListReceipt.add(String.format("=================================="));
        arrayListReceipt.add(String.format("%-20s %13s","Total amount:","$" + Math.round(sum * 1e2)/1e2));
        if(id_card != 0) {
            arrayListReceipt.add(String.format("%-20s %13s", "Discount:", productAndCardData.discountMap.get(id_card) + "%"));
            double total_cost = sum - (double) sum * (productAndCardData.discountMap.get(id_card)) / 100;
            arrayListReceipt.add(String.format("%-20s %13s", "Total cost:", "$" + Math.round(total_cost * 1e2) / 1e2));
        }else {
            arrayListReceipt.add(String.format("%-20s %13s", "Total cost:", "$" + sum));
        }
    }

    private Integer stringToInteger(String str){
        int id = 0;
        try {
            id = Integer.parseInt(str);
        } catch (NumberFormatException e){
            System.err.println("Неправильный формат строки");
        }
        return id;
    }
}
