package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This controller class provides control logic for the modify parts form of the application.
 * @author Alexandria Lim
*/
public class ModifyProductController implements Initializable {
    public TextField autoID;
    public TextField productName;
    public TextField productInv;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;
    public TableView<Part> modifyTable1;
    public TableColumn mID;
    public TableColumn mName;
    public TableColumn mInv;
    public TableColumn mPrice;
    public TableView<Part> modifyTable2;
    public TableColumn mID2;
    public TableColumn mName2;
    public TableColumn mInv2;
    public TableColumn mPrice2;
    public TextField searchProductPartsBox;
    //associated parts with the selected product to modify
    private Product selectedProduct;
    private ObservableList<Part> selectedProductPartstoModify = FXCollections.observableArrayList();
    private int selectedProducttoModifyIndex;

    /**
     * This method initializes the controller.
     * @param url The location that is used to resolve relative paths for the root object.
     * @param resourceBundle The resources that are used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = MainController.returnProductToModify();
        selectedProductPartstoModify = selectedProduct.getAllAssociatedParts();
        selectedProducttoModifyIndex = Inventory.getAllProducts().indexOf(selectedProduct);

        autoID.setText(String.valueOf(selectedProduct.getId()));
        productName.setText(selectedProduct.getName());
        productInv.setText(String.valueOf(selectedProduct.getStock()));
        productPrice.setText(String.valueOf(selectedProduct.getPrice()));
        productMax.setText(String.valueOf(selectedProduct.getMax()));
        productMin.setText(String.valueOf(selectedProduct.getMin()));

        mID.setCellValueFactory(new PropertyValueFactory<>("id"));
        mName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        mID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        mName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        mInv2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyTable1.setItems(Inventory.getAllParts());
        modifyTable2.setItems(selectedProductPartstoModify);

    }

    /**
     * This method ignores the users inputted product data and returns back to main.
     * @param actionEvent click button to cancel and return back to main.
     * @throws IOException
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/c482/Views/Main.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        //Create Scene
        Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method allows a user to add a part to a product.
     */
    public void addPartToModifyProduct() {
        Part partToAddToProduct = modifyTable1.getSelectionModel().getSelectedItem();
        selectedProductPartstoModify.add(partToAddToProduct);
        modifyTable2.setItems(selectedProductPartstoModify);
    }

    /**
     * This method allows a user to remove an existing part from a product.
     */
    public void removePartToModifyProduct() {
        Alert removePartAlert = new Alert(Alert.AlertType.NONE);
        removePartAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        removePartAlert.setContentText("Are you sure you want to remove this part?");
        Optional<ButtonType> confirmationResult = removePartAlert.showAndWait();
        if (confirmationResult.get() == ButtonType.OK) {
            Part partToRemoveFromProduct = modifyTable2.getSelectionModel().getSelectedItem();
            selectedProductPartstoModify.remove(partToRemoveFromProduct);
            modifyTable2.setItems(selectedProductPartstoModify);
        }
    }

    /**
     * This method saves inputted product data and added or removed parts and associates it with the selected product
     * and then returns the user to the main screen.
     * @param actionEvent click button to save product data and return back to main.
     * @throws IOException
     */
    public void saveChangesToProduct(ActionEvent actionEvent) throws IOException{
        Integer ID = Integer.valueOf(autoID.getText());
        Alert inventoryError = new Alert(Alert.AlertType.ERROR);
        Alert inputError = new Alert(Alert.AlertType.ERROR);

        try{
            String productname = productName.getText();
            Integer productInventory = Integer.valueOf(productInv.getText());
            Double productCost = Double.valueOf(productPrice.getText());
            Integer productmax = Integer.valueOf(productMax.getText());
            Integer productmin = Integer.valueOf(productMin.getText());

            if(productmax < productmin){
                inventoryError.setContentText("Product MAX must be greater than product MIN");
                inventoryError.showAndWait();
            }
            if (productInventory >= productmax || productInventory <=  productmin) {
                inventoryError.setContentText("Inventory must be between the product max and min value");
                inventoryError.showAndWait();
            }else{
                Product newproduct = new Product(ID, productname,productCost, productInventory, productmin, productmax);
                for(Part partChangesForProduct: selectedProductPartstoModify){
                    newproduct.addAssociatedPart(partChangesForProduct);
                }
                Inventory.updateProduct(selectedProducttoModifyIndex, newproduct);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/c482/Views/Main.fxml"));
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                //Create Scene
                Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception e){
            inputError.setContentText("The wrong value type has been inputted. Please try again");
            inputError.showAndWait();
        }

    }

    /**
     * This method searches a table for any items that have string of characters or a number that the user
     * has inputted. It compares the user input to names of the parts or the ID number of a part.
     * If the search box is empty, the table loads all values.
     */
    public void getPartResults(){
        String productpartinput = searchProductPartsBox.getText();
        ObservableList<Part> productParts = FXCollections.observableArrayList();
        ObservableList<Part> allProductParts = modifyTable1.getItems();
        for(Part productpartItems: allProductParts){
            if(String.valueOf(productpartItems.getId()).contains(productpartinput) ||
                    productpartItems.getName().toLowerCase().contains(productpartinput.toLowerCase())){
                productParts.add(productpartItems);
            }
        }
        if (productParts.toArray().length == 0) {
            Alert searchError = new Alert(Alert.AlertType.ERROR);
            searchError.setContentText("No such part exists. Please search another part");
            searchError.showAndWait();
        }
        modifyTable1.setItems(productParts);
        if(searchProductPartsBox.getText().isEmpty()){
            modifyTable1.setItems(Inventory.getAllParts());
        }
    }

}
