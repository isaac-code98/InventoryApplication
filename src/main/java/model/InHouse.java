package model;

/** This class creates the InHouse model that inherits props from Part.*/
public class InHouse extends Part {

    // Declaring machineId
    private int machineId;

    /** Method that creates the InHouse constructor.
     @param id id
     @param name name
     @param price price
     @param stock stock
     @param min min
     @param max max
     @param machineId machineId
     */
    public InHouse (int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Method that retrieves the machineId.
     @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /** Method that sets the machineId with the machineId that is passed in as a parameter.
     @param machineId int machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
