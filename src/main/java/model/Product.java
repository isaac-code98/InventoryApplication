package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates the Product model.*/
public class Product {

    /**
     * Here we create an ObservableList object which is similar to an ArrayList with the exception that it can be read
     * by FXML, here we can hold all associated parts pertaining to a particular product.
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // Declaring respective Product variables and their types
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Method that creates the Product constructor.
     @param id id
     @param name name
     @param price price
     @param stock stock
     @param min min
     @param max max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This method retrieves the id.
     @return id
     */
    public int getId() {
        return id;
    }

    /** This method sets the id with the id that was passed in as a parameter.
     @param id takes in an id as a parameter
     */
    public void setId(int id) {
        this.id = id;
    }

    /** This method retrieves the name.
     @return name
     */
    public String getName() {
        return name;
    }

    /** This method sets the name with the name that was passed in as a parameter.
     @param name takes in a name as a parameter
     */
    public void setName(String name) {
        this.name = name;
    }

    /** This method retrieves the price.
     @return price
     */
    public double getPrice() {
        return price;
    }

    /** This method sets the price with the price that was passed in as a parameter.
     @param price takes in a price as a parameter
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** This method retrieves the stock.
     @return stock
     */
    public int getStock() {
        return stock;
    }

    /** This method sets the stock with the stock that was passed in as a parameter.
     @param stock takes in a stock as a parameter
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This method retrieves the min.
     @return min
     */
    public int getMin() {
        return min;
    }

    /** This method sets the min with the min that was passed in as a parameter.
     @param min takes in a min as a parameter
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method retrieves the max.
     @return max
     */
    public int getMax() {
        return max;
    }

    /** This method sets the max with the max that was passed in as a parameter.
     @param max takes in a max as a parameter
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method adds the part that was passed in to the associatedParts list.
     @param part takes in a part object
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** This method deletes the selectedAssociatedPart from the associatedParts list.
     @param selectedAssociatedPart selectedAssociatedPart
     @return associatedParts.remove(selectedAssociatedPart)
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /** This method gets and retrieves the ObservableList of associatedParts.
     @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
