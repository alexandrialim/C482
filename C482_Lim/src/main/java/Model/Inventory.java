package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 * This class sets up functions to work with the parts and products inventory data.
 * @author Alexandria Lim
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Add a part to the parts list.
     * @param newPart add a new part to the parts list
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Add a new product to the product list.
     * @param newProduct add a new product to the product list
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Look up all the information related to a part by searching the Part ID number.
     * @param partId used for searching the Part ID
     * @return return all the information that is related to the Part ID
     */
    //not sure if this works
    public static Part lookupPart(int partId){
        Part partfound = null;
        for(Part part: allParts) {
            if (part.getId() == partId) {
                partfound = part;
            }
        }
        return partfound;
    }

    /**
     * Look up all the information related to a product by searching the Product ID number.
     * @param productId a product ID number
     * @return all the information that is related to the Product ID number
     */
    public static Product lookupProduct(int productId){
        Product productfound = null;
        for(Product product: allProducts){
            if (product.getId() == productId){
                productfound = product;
            }
        }
        return productfound;
    }

    /**
     * Look up all the information related to a part by searching the Part Name.
     * @param partName used for searching the part name
     * @return return all the information that is related to the Part Name
     */
    //not sure if this works
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partinfofound = FXCollections.observableArrayList();

        for(Part part: allParts){
            if(part.getName().contains(partName)){
                partinfofound.add(part);
            }
        }
        return partinfofound;
    }

    /**
     * Look up all the information related to a product by searching the Product Name.
     * @param productName a product name
     * @return all the information that is related to the Product Name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productinfofound = FXCollections.observableArrayList();

        for(Product product: allProducts){
            if(product.getName().contains(productName)){
                productinfofound.add(product);
            }
        }
        return productinfofound;
    }

    /**
     * Updates the information for a part in the list of parts.
     * @param index The index of the part that needs to be updated
     * @param selectedPart the part that needs to be updated
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * Updates the information for a product in the list of products.
     * @param index The index of the product that needs to be updated
     * @param newProduct the product that needs to be updated
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes the information for a part in the list of parts.
     * @param selectedPart the part that needs to be deleted from the list
     */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * Deletes the information for a product in the list of products.
     * @param selectedProduct the product that needs to be deleted from the list
     */
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * Gets all parts in the parts inventory list.
     * @return all parts in the list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products in the product inventory list.
     * @return all products in the list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    static{
        initalDataSet();
    }
    public static void initalDataSet(){

        Part part1 = new InHouse(1, "Breaks", 15.00, 10, 1, 20, 110);
        Part part2 = new InHouse(2, "Wheel", 11.00, 16, 1, 20, 111);
        Part part3 = new Outsourced(3, "Seat", 15.00, 10, 1, 20, "CarParts.com");

        Product product1 = new Product(100, "Tricycle", 299.99, 5, 1, 20);
        Product product2 = new Product(101, "Giant Bike", 299.99, 5, 1, 20);

        //add data to table
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
    }

}
