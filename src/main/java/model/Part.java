package model;

/** This class creates the abstract Part model.*/
public abstract class Part {

    // Declaring part id
    private int id;

    // Declaring part name
    private String name;

    // Declaring part price
    private double price;

    // Declaring part stock
    private int stock;

    // Declaring part min
    private int min;

    // Declaring part max
    private int max;

    /** Method that creates the Part constructor.
     @param id id
     @param name name
     @param price price
     @param stock stock
     @param min min
     @param max max
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
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

    /** This method sets the id with the id that is passed in as a parameter.
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

    /** This method sets the name with the name that is passed in as a parameter.
     @param name takes in a String name
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

    /** This method sets the price with the price is passed in as a parameter.
     @param price takes in a double price
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

    /** This method sets the stock with the stock that is passed in as a parameter.
     @param stock takes in an int stock
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

    /** This method sets the min with the min that is passed in as a parameter.
     @param min takes in an int min
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

    /** This method sets the max with the max that is passed in as a parameter.
     @param max takes in an int max
     */
    public void setMax(int max) {
        this.max = max;
    }
}
