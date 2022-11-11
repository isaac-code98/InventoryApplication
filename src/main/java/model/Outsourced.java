package model;

/** This class creates the Outsourced model that inherits from Part.*/
public class Outsourced extends Part{

    // Declaring companyName
    private String companyName;

    /** Method that creates the Outsourced constructor.
     @param id id
     @param name name
     @param price price
     @param stock stock
     @param min min
     @param max max
     @param companyName companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method retrieves the companyName.
     @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Method that sets the companyName with the companyName that is passed in as a parameter.
     @param companyName String companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
