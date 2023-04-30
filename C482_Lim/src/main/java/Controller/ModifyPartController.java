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
 * This controller class provides control logic for the modify parts form of the application.
 * @author Alexandria Lim
 */
public class ModifyPartController implements Initializable {

    private Part selectedParttoModify;
    private int selectedParttoModifyIndex;

    public RadioButton inhouse;
    public RadioButton outsourced;
    public Label lastLabel;
    public TextField autoID;
    public TextField getName;
    public TextField getInv;
    public TextField getPrice;
    public TextField getMax;
    public TextField getMin;
    public TextField getLastLabel;


    /**
     * This method initializes the Modify Part Controller.
     * When the stage is loaded, the data for the selected part is also loaded.
     * @param url The location that is used to resolve relative paths for the root object.
     * @param resourceBundle The resources that are used to localize the root object.
     *                       write about how importing data onto modify part form was an issue and how you solved it
     * RUNTIME ERROR: When initializing the data for modify parts, simply calling the initialized data from the
     *                       main table doesn't include every thing that is associated with a part.
     *                       To fix this, I created a method in the main controller file that returns a part that
     *                       was selected. And in this controller, I was able to call the function from main to grab
     *                       the selected part. Once the selected part was identified I was able to call various
     *                       'get()' methods to retrieve each bit of data that was needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedParttoModify = MainController.returnPartToModify();
        selectedParttoModifyIndex = Inventory.getAllParts().indexOf(selectedParttoModify);
        autoID.setText(String.valueOf(selectedParttoModify.getId()));
        getName.setText(selectedParttoModify.getName());
        getInv.setText(String.valueOf(selectedParttoModify.getStock()));
        getPrice.setText(String.valueOf(selectedParttoModify.getPrice()));
        getMax.setText(String.valueOf(selectedParttoModify.getMax()));
        getMin.setText(String.valueOf(selectedParttoModify.getMin()));

        if(selectedParttoModify instanceof InHouse){
            getLastLabel.setText(String.valueOf(((InHouse) selectedParttoModify).getMachineId()));
            inhouse.setSelected(true);
            setSource();
        }
        else{
            getLastLabel.setText(((Outsourced) selectedParttoModify).getCompanyName());
            outsourced.setSelected(true);
            setSource();
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
     * This method allows a user to save their changes that were made
     * to a part and returns them back to the main screen.
     * @param actionEvent when the save button is clicked, this takes the user back to the main screen.
     * @throws IOException
     */
    public void saveModifiedData(ActionEvent actionEvent) throws IOException{
        Integer ID = Integer.valueOf(autoID.getText());
        Alert inventoryError = new Alert(Alert.AlertType.ERROR);
        Alert inputError = new Alert(Alert.AlertType.ERROR);

        try{
            String partName = getName.getText();
            Integer partInventory = Integer.valueOf(getInv.getText());
            Double partPrice = Double.valueOf(getPrice.getText());
            Integer partMax = Integer.valueOf(getMax.getText());
            Integer partMin = Integer.valueOf(getMin.getText());
            if(partMax < partMin){
                inventoryError.setContentText("Part MAX must be greater than part MIN");
                inventoryError.showAndWait();
            } else if (partInventory >= partMax || partInventory <= partMin) {
                inventoryError.setContentText("Inventory must be between the part max and min value");
                inventoryError.showAndWait();
            }else{
                if(inhouse.isSelected()) {
                    setSource();
                    Integer machineID = Integer.parseInt(getLastLabel.getText());
                    InHouse updated_inhouse = new InHouse(ID, partName, partPrice, partInventory, partMin, partMax, machineID);
                    Inventory.updatePart(selectedParttoModifyIndex,updated_inhouse);

                }else if(outsourced.isSelected()) {
                    setSource();
                    String companyName = getLastLabel.getText();
                    Outsourced updated_outsourced = new Outsourced(ID, partName, partPrice, partInventory, partMin, partMax, companyName);
                    Inventory.updatePart(selectedParttoModifyIndex,updated_outsourced);
                }

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
}
