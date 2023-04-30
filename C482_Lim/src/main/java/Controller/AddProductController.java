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
 * This controller class provides control logic for the add product form of the application.
 * @author Alexandria Lim
 */
public class AddProductController implements Initializable {
    public TextField autoID;
    public TextField addname;
    public TextField addinv;
    public TextField addprice;
    public TextField addmax;
    public TextField addmin;
    public TableView<Part> partsTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInv;
    public TableColumn partPrice;
    public TableView<Part> partsTable2;
    public TableColumn partID2;
    public TableColumn partName2;
    public TableColumn partInv2;
    public TableColumn partPrice2;
    public TextField searchProductPartsBox;

    /**
     * The list to hold parts that should be associated with a product.
     */
    public ObservableList<Part> relatedParts = FXCollections.observableArrayList();


    /**
     * This method initializes the controller.
     * @param url The location that is used to resolve relative paths for the root object.
     * @param resourceBundle The resources that are used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        partID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());
    }
    

    /**
     * This method saves new inputted product data and returns to the main screen.
     * @param actionEvent click button to save product data and return back to main.
     * @throws IOException
     * FUTURE ENHANCEMENTS: If I were to make additional changes in the future, I would add a try and catch to make
     *                      sure that a user doesn't try to save a new product when nothing was inputted into the form.
     *                      At the moment it throws an error in the console but allows the program to keep running.
     *                      Making the change would catch this human error, and have an alert pop-up to notify the user.
     */
    public void clicktoSaveProduct(ActionEvent actionEvent) throws IOException {
        Alert inputError = new Alert(Alert.AlertType.ERROR);
        int newProductID = 0;
        for(Product product: Inventory.getAllProducts()){
            if(product.getId() > 0){
                Integer tempProductID = product.getId();
                newProductID = tempProductID + 1;
                autoID.setText(String.valueOf(newProductID));
            }
        }
        try{
            String partName = addname.getText();
            Integer partInventory = Integer.valueOf(addinv.getText());
            Double partPrice = Double.valueOf(addprice.getText());
            Integer partMax = Integer.valueOf(addmax.getText());
            Integer partMin = Integer.valueOf(addmin.getText());
            Alert inventoryError = new Alert(Alert.AlertType.ERROR);

            if(partMax < partMin){
                inventoryError.setContentText("Part MAX must be greater than part MIN");
                inventoryError.showAndWait();
            } else if (partInventory >= partMax || partInventory <=  partMin) {
                inventoryError.setContentText("Inventory must be between the part max and min value");
                inventoryError.showAndWait();
            }else {
                Product newproduct = new Product(newProductID, partName, partPrice, partInventory, partMin, partMax);

                //Associate all parts with a product
                for (Part partToAdd : relatedParts) {
                    newproduct.addAssociatedPart(partToAdd);
                }
                Inventory.addProduct(newproduct);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/c482/Views/Main.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                //Create Scene
                Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e){
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
        ObservableList<Part> allProductParts = partsTable.getItems();
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
        partsTable.setItems(productParts);
        if(searchProductPartsBox.getText().isEmpty()){
            partsTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * This method allows a user to add a part to a product.
     */
    public void addPartToProductButton(){
        Part partToAdd = partsTable.getSelectionModel().getSelectedItem();
        relatedParts.add(partToAdd);
        partsTable2.setItems(relatedParts);
    }

    /**
     * This method allows a user to remove an existing part from a product.
     */
    public void removePartFromProductButton(){
        Alert removePartAlert = new Alert(Alert.AlertType.NONE);
        removePartAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        removePartAlert.setContentText("Are you sure you want to remove this part?");
        Optional<ButtonType> confirmationResult = removePartAlert.showAndWait();
        if (confirmationResult.get() == ButtonType.OK){
            Part partToRemove = partsTable2.getSelectionModel().getSelectedItem();
            relatedParts.remove(partToRemove);
            partsTable2.setItems(relatedParts);
        }
    }

    /**
     * This method ignores the users inputted part data and returns back to main.
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
}
