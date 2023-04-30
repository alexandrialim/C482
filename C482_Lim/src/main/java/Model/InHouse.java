package Model;

import javafx.fxml.FXML;

/**
 * This class sets up functions for the InHouse option of the Parts form.
 * @author Alexandria Lim
 */
public class InHouse extends Part{
    @FXML
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name,price,stock,min,max);
        this.machineId = machineId;
    }

    /**
     * Set the inputted number as the machine ID for an In-House part or product.
     * @param machineId the machine ID of a part or product.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets the machine ID associated with an In-House part or product.
     * @return the machine ID of a part or product.
     */
    public int getMachineId() {
        return machineId;
    }
}
