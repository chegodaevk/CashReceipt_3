package by.chegodaev.receipt.data;

import java.util.HashMap;
import java.util.Map;

public class InputData {
    String[] itemId;
    String[] quantity;

    public InputData(String[] itemId, String[] quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Map<String, String> getMapInputData()    {
        Map<String, String> mapValue = new HashMap<>();
        for (int i = 0; i < itemId.length; i++){
            mapValue.put(itemId[i], quantity[i]);
        }
        return mapValue;
    }


}
