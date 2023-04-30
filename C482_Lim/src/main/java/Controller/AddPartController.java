package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller class provides control logic for the add part form of the application.
 * @author Alexandria Lim
 */
public class AddPartController implements Initializable {

    public RadioButton inhouse;
    public RadioButton outsourced;
    public Label lastLabel;
    public TextField autoID;
    public TextField addname;
    public TextField addinventory;

    public TextField addprice;
    public TextField addmax;
    public TextField addmin;
    public TextField addLast;


    /**
     * This method initializes the controller.
     * @param url The location that is used to resolve relative paths for the root object.
     * @param resourceBundle The resources that are used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("I am initialized");
    }
    /**
     * This method saves new inputted part data and returns to the main screen.
     * @param actionEvent click button to save part data and return back to main.
     * @throws IOException
     */
    public void clicktoSavePart(ActionEvent actionEvent) throws IOException {
        Alert inputError = new Alert(Alert.AlertType.ERROR);
        int newPartID = 0;
        for(Part part: Inventory.getAllParts()){
            if(part.getId() > 0){
                Integer partID = part.getId();
                newPartID = partID + 1;
                autoID.setText(String.valueOf(newPartID));
            }
        }
        try{
            String partName = addname.getText();
            Integer partInventory = Integer.valueOf(addinventory.getText());
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
            } else {
                if(inhouse.isSelected()){
                    Integer machineID = Integer.valueOf(addLast.getText());
                    InHouse newpart_inhouse = new InHouse(newPartID, partName, partPrice, partInventory, partMin, partMax, machineID);
                    Inventory.addPart(newpart_inhouse);
                } else if(outsourced.isSelected()){
                    String companyName = addLast.getText();
                    Outsourced newpart_outsourced = new Outsourced(newPartID, partName, partPrice, partInventory, partMin, partMax, companyName);
                    Inventory.addPart(newpart_outsourced);
                }

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/c482/Views/Main.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                //Create Scene
                Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e){
            inputError.setContentText("The wrong value type has been inputted. Please try again");
            inputError.showAndWait();
        }



    }

    /** This method sets the creation source of a part.
     * if it was manufactured in-house, the selected source will be "In-House"
     * and will set the last label as "Machine ID".
     * if it was manufactured outside, the selected source will be "Out Sourced"
     * and will set the last label as "Company Name".
     */
    public void setSource(){
        if(inhouse.isSelected()){
            lastLabel.setText("Machine ID");
        } else if (outsourced.isSelected()) {
            lastLabel.setText("Company Name");
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
