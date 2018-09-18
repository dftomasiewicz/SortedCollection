package dft;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StockList {
    private final Map<String, StockItem> list;

    public StockList() {
        this.list = new LinkedHashMap<>();
    }

    public int addStock(StockItem item) {
        if (item != null) {
            //sprawdzymy czy mamy jakas ilosc tego itemu
            //getOrDefault jak znajdzie w liscie item pod kluczem nazwy, to przypisze
            // inStock ten obiekt, a jak nie to przypisze inStock item
            StockItem inStock = list.getOrDefault(item.getName(), item);
            //if there are already stocks(zapasy) on this item, adjust quantity
            if (inStock != item) {
                item.adjustStock(inStock.availableQuantity());
            }

            list.put(item.getName(), item);
            return item.availableQuantity();
        }
        return 0;
    }

    public int sellStock(String item, int quantity) {
        StockItem inStock = list.get(item);

        if((inStock != null) && (quantity > 0)){
            return inStock.finaliseStock(quantity);
        }
        return 0;
//        StockItem inStock = list.getOrDefault(item, null);
//
//        if ((inStock != null) && (inStock.availableQuantity() >= quantity) && (quantity > 0)) {
//            inStock.adjustStock(-quantity);
//            return quantity;
//        }
//        return 0;
    }

    public int reserveStock(String item, int quantity){
        StockItem inStick = list.get(item);

        if((inStick != null) && (quantity > 0)){
            return inStick.reserveStock(quantity);
        }
        return 0;
    }

    public int unreserveStock(String item, int quantity){
        StockItem inStick = list.get(item);

        if((inStick != null) && (quantity > 0)){
            return inStick.unreserveStock(quantity);
        }
        return 0;
    }

    public StockItem get(String key) {
        return list.get(key);
    }

    public Map<String, Double> PriceList(){
        Map<String, Double> prices = new LinkedHashMap<>();
        for(Map.Entry<String,StockItem> item : list.entrySet()){
            prices.put(item.getKey(),item.getValue().getPrice());
        }
        return Collections.unmodifiableMap(prices);
    }

    public Map<String, StockItem> Items() {
        return Collections.unmodifiableMap(list);
    }
    //to sie dodaje po to zeby nie moglo byc innej motody na dodanie itemu do basketa


    @Override
    public String toString() {
        String s = "\nStock List\n";
        double totalCost = 0.0;
        for (Map.Entry<String, StockItem> item : list.entrySet()){
            StockItem stockItem = item.getValue();

            double itemValue = stockItem.getPrice() * stockItem.availableQuantity();

            s = s + stockItem + ". There are " +  stockItem.availableQuantity() + " in stock. Value of items: ";
            s = s + String.format("%.2f",itemValue) + "\n";
            //"%.2f" - pokazuje 2 msc po przecinku
            totalCost += itemValue;

        }
        return s + "Total stock value " + totalCost;
    }
}
