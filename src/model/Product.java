package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock product stock size
     * @param min product min size
     * @param max product max size
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }










    /**
     *
     * @return product id
     */
    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }
    /**
     * @param name product's name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return product name
     */
    public String getName() {
        return name;
    }


    /**
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the total price of product
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return product stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the total stock size
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return product min stock
     */

    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */

    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return product maxStock
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */

    public void setMax(int max) {
        this.max = max;
    }


    /**
     *
     * @return associated parts
     */
    public ObservableList<Part> getAllAssociatedPart(){
        return associatedParts;
    }


    /**
     * RUNTIME ERROR: NullPointerException, happens if the parameter is null, solved by checking if the parameter is null or not
     * @param part
     */

    public void addAssociatedPart(Part part){
        if(part != null){
            associatedParts.add(part);
        }


    }

    /**
     * RUNTIME ERROR: NullPointerException, happens if the parameter is null, solved by checking if the parameter is null or not
     * @param part
     */
    public boolean deleteAssociatedPart(Part part){
        if(part != null){
            for(int x = 0; x < associatedParts.size(); x++){
                if(associatedParts.get(x).equals(part)){
                    associatedParts.remove(x);
                    return true;
                }
            }
        }
        return false;
    }
}
