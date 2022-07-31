package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    static final private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * @param newPart
     * RUNTIME ERROR: Null Pointer Exception, can happen if parameter newPart is null, it is corrected by adding an existing Part instance
     * that is not null and passing it to this method, the try-catch will then check for any failures instead of crashing the program.
     */
    public static void addPart(Part newPart){
        try{
            allParts.add(newPart);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        if(newProduct != null){
            allProducts.add(newProduct);
        }


    }

    /**
     *
     * @param partID
     * @return
     */
    public static Part lookupPart(int partID){
        for(int x = 0; x < allParts.size(); x++){
            if(allParts.get(x).getId() == partID){
                return allParts.get(x);
            }
        }
        return null;
    }

    /**
     * @param name
     * @return
     */
    public static Part lookupPart(String name){
        for(int x = 0; x < allParts.size(); x++){
            if(allParts.get(x).getName().equals(name)){
                return allParts.get(x);
            }
        }
        return null;
    }

    /**
     *  RUNTIME ERROR: IndexOutOfBound, happens if we have a negative index, we solve it by using a condition to execute
     * the update if the index is >=0
     * @param index
     * @param selectedPart
     */

    public static void updatePart(int index, Part selectedPart){
        if(selectedPart != null){
            if(index >= 0){
                allParts.set(index, selectedPart);
            }

        }
    }


    /**
     * RUNTIME ERROR: NullPointerException, happens if the parameter is null, solved by checking if the paratemer is null or not,
     * if it is null then it won't execute the looping, if it's not it will execute normally
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart){
        if(selectedPart != null){
            for(int x = 0; x < allParts.size(); x++){
                if(allParts.get(x).equals(selectedPart)){
                    allParts.remove(x);
                    return true;
                }
            }

        }


        return false;
    }


    /**
     *
     * @return the observablelist of all class
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * @param productID
     * @return the product class we're looking
     */

    public static Product lookupProduct(int productID){
        for(int x = 0; x < allProducts.size(); x++){
            if(allProducts.get(x).getId() == productID){
                return allProducts.get(x);

            }
        }
        return null;
    }

    /**
     *
     * @param name
     * @return the Product class we're looking
     */

    public static Product lookupProduct(String name){
        for(int x = 0; x < allProducts.size(); x++){
            if(allProducts.get(x).getName().equals(name)){
                return allProducts.get(x);
            }
        }
        return null;
    }

    /**
     * RUNTIME ERROR: NullPointerException, happens if one of our parameters is null, solved by checking if the parameters are null or not.
     * @param index
     * @param newProduct
     */

    public static void updateProduct(int index, Product newProduct){
        if(index >= 0){
            if(newProduct != null){
                allProducts.set(index, newProduct);
            }
        }
    }

    /**
     *RUNTIME ERROR: NullPointerException, happens if the parameter is null, can be solved by creating a conditio n to see if the param is null or not
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct){
        if(selectedProduct != null){
            for(int x = 0; x < allProducts.size(); x++){
                if(allProducts.get(x).equals(selectedProduct)){
                    if(allProducts.get(x).getAllAssociatedPart().size() > 0){
                        return false;
                    }
                    allProducts.remove(x);
                    return true;
                }
            }

        }



        return true;
    }

    /**
     * @return all inventory products
     */

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }



}
