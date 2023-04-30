package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class sets up functions for the product form
 * @author Alexandria Lim
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id the product id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the product name to set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock the product stock value to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the product min value to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the product max value to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the product name
     */
    public String getName(){
        return name;
    }

    /**
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the stock amount of a product
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the min amount of a product
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the max amount of a product
     */
    public int getMax() {
        return max;
    }

    /**
     * @param part the part item to add to the parts list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart select an associated part from the parts list
     * @return true if the selected part was successfully deleted.
     *         return false if not able to delete the selected part.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * @return each part listed in the parts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
