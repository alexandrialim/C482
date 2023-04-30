package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This controller class provides control logic for the Main form of the application.
 * @author Alexandria Lim
 */
public class MainController implements Initializable {
    private static Part partToModify;
    private static Product productToModify;

    //Parts Variables
    @FXML
    private TableView<Part> PartsTable;
    @FXML
    private TableColumn<Part, Integer>  PartID;
    @FXML
    private TableColumn<Part, Integer>  PartName;
    @FXML
    private TableColumn<Part, Integer>  PartInventoryLevel;
    @FXML
    private TableColumn<Part, Integer>  PartCostperUnit;
    public TextField searchPartBox;
    public Button AddPartButton;

    public Button ModifyPartButton;
    public Button DeletePartButton;

    //Products Variables
    @FXML
    private TableView<Product> ProductsTable;
    @FXML
    private TableColumn<Part, Integer>  ProductID;
    @FXML
    private TableColumn<Part, Integer>  ProductName;
    @FXML
    private TableColumn<Part, Integer>  ProductInventoryLevel;
    @FXML
    private TableColumn<Part, Integer>  ProductCostperUnit;
    public TextField searchProductBox;
    public Button AddProductButton;

    public Button ModifyProductButton;
    public Button DeleteProductButton;

    /**
     * This method initializes the controller.
     * @param url The location that is used to resolve relative paths for the root object.
     * @param resourceBundle The resources that are used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartCostperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartsTable.setItems(Inventory.getAllParts());

        ProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductCostperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductsTable.setItems(Inventory.getAllProducts());
    }

    /**
     * This method loads the Add Parts Form.
     * @param actionEvent click button to go to Add Parts Form
     * @throws IOException throws an exception
     */
    public void clicktoAddPartsForm (ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/c482/Views/AddPartForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        //Create Scene
        Scene scene = new Scene(fxmlLoader.load(), 400, 600);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method loads the Add Products Form.
     * @param actionEvent click button to go to Add Products Form
     * @throws IOException throws an exception
     */
    public void clicktoAddProductsForm (ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/c482/Views/AddProductForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        //Create Scene
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method loads the Modify Parts Form.
     * @param actionEvent click button to go to Modify Parts Form
     * @throws IOException throws an exception
     */
    public void clicktomodifypartform (ActionEvent actionEvent) throws IOException {
        partToModify = PartsTable.getSelectionModel().getSelectedItem();
        Alert modifyAlert = new Alert(AlertType.NONE);
        if(partToModify == null){
            modifyAlert.setAlertType(AlertType.ERROR);
            modifyAlert.setContentText("No part was selected, " +
                    "so no part can be modified from the table");
            modifyAlert.showAndWait();
            }else{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/c482/Views/ModifyPartForm.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            //Create Scene
            Scene scene = new Scene(fxmlLoader.load(), 400, 600);
            stage.setTitle("Modify Part Form");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method loads the Modify Products Form.
     * @param actionEvent click button to go to Modify Products Form
     * @throws IOException throws an exception
     */
    public void clicktomodifyproductform (ActionEvent actionEvent) throws IOException {
        productToModify = ProductsTable.getSelectionModel().getSelectedItem();
        Alert modifyproductAlert = new Alert(AlertType.NONE);
        if(productToModify == null){
            modifyproductAlert.setAlertType(AlertType.ERROR);
            modifyproductAlert.setContentText("No product was selected, " +
                    "so no product can be modified from the table");
            modifyproductAlert.showAndWait();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/c482/Views/ModifyProductForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        //Create Scene
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Modify Product Form");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method deletes a part in the main frame.
     * @throws IOException throws an exception
     */
    public void clicktodeletepart () throws IOException {
        Part partToDelete = PartsTable.getSelectionModel().getSelectedItem();
        Alert deleteAlertPart = new Alert(AlertType.ERROR);
        if(partToDelete == null){
            deleteAlertPart.setAlertType(AlertType.ERROR);
            deleteAlertPart.setContentText("No part was selected, " +
                    "so no part can be deleted from the table");
            deleteAlertPart.showAndWait();
        }else {
            deleteAlertPart.setAlertType(AlertType.CONFIRMATION);
            deleteAlertPart.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> confirmationResult = deleteAlertPart.showAndWait();
            if (confirmationResult.get() == ButtonType.OK) {
                Inventory.deletePart(partToDelete);
                PartsTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * This method deletes a product in the main frame.
     * @throws IOException throws an exception
     */
    public void clicktodeleteproduct() throws IOException {
        Product productToDelete = ProductsTable.getSelectionModel().getSelectedItem();
        Alert deleteAlert = new Alert(AlertType.ERROR);
        if(productToDelete == null){
            deleteAlert.setAlertType(AlertType.ERROR);
            deleteAlert.setContentText("No product was selected, " +
                    "so no product can be deleted from the table");
            deleteAlert.showAndWait();
        } else if (!productToDelete.getAllAssociatedParts().isEmpty()) {
            deleteAlert.setContentText("Sorry you can't delete this product since" +
                    " there are one or more parts associated");
            deleteAlert.showAndWait();
        } else{
            deleteAlert.setAlertType(AlertType.CONFIRMATION);
            deleteAlert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> confirmationResult = deleteAlert.showAndWait();
            if (confirmationResult.get() == ButtonType.OK){
                Inventory.deleteProduct(productToDelete);
                ProductsTable.setItems(Inventory.getAllProducts());
            }
        }
    }


    /**
     * This method searches a table for any items that have string of characters or a number that the user
     * has inputted. It compares the user input to names of the parts or the ID number of a part.
     * If the search box is empty, the table loads all values.
     */
    public void getPartResults(KeyEvent enterpressed) throws IOException{
        String partinput = searchPartBox.getText();
        ObservableList<Part> parts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = PartsTable.getItems();
        for(Part partItems: allParts){
            if(String.valueOf(partItems.getId()).contains(partinput) ||
                    partItems.getName().toUpperCase().contains(partinput.toUpperCase())) {
                parts.add(partItems);
            }
        }
        if (parts.toArray().length == 0){
            Alert searchError = new Alert(AlertType.ERROR);
            searchError.setContentText("No such part exists. Please search another part");
            searchError.showAndWait();
        }
        PartsTable.setItems(parts);
        if(searchPartBox.getText().isEmpty()){
            PartsTable.setItems(Inventory.getAllParts());
        }
    }


    /**
     * This method searches a table for any items that have string of characters or a number that the user
     * has inputted. It compares the user input to names of the products or the ID number of a product.
     * If the search box is empty, the table loads all values.
     */
    public void getProductResults(){
        String productinput = searchProductBox.getText();
        ObservableList<Product> products = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = ProductsTable.getItems();

        for(Product productItems: allProducts){
            if(String.valueOf(productItems.getId()).contains(productinput) ||
                    productItems.getName().toLowerCase().contains(productinput.toLowerCase())){
                products.add(productItems);
            }
        }
        if (products.toArray().length == 0) {
            Alert searchError = new Alert(AlertType.ERROR);
            searchError.setContentText("No such product exists. Please search another product");
            searchError.showAndWait();
        }
        ProductsTable.setItems(products);
        if(searchProductBox.getText().isEmpty()){
            ProductsTable.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Exits the program
     */
    @FXML
    private void exitStage(){
        System.exit(0);
    }

    /**
     * This method allows for part data to be pulled from main screen.
     * @return returns the part selected to be modified.
     */
    public static Part returnPartToModify(){
        return partToModify;
    }

    /**
     * This method allows for product data to be pulled from main screen.
     * @return returns the product selected to be modified.
     */
    public static Product returnProductToModify(){
        return productToModify;
    }

}