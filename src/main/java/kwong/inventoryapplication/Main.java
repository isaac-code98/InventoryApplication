package kwong.inventoryapplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import java.io.IOException;
import java.util.Objects;

// Author: Isaac Kwong
// Student ID: 001230140
// Course: C482 - Software I
// Javadoc files location: C482 - Software I folder

// RUNTIME ERROR location: Documented comments in header of selectRBtnInHouse method in AddPartController.java

/* FUTURE ENHANCEMENT: A future enhancement that I think would be particularly useful would be to create a separate
   method or a function that could set the stage, scene, and then just call that function wherever need be, instead of
   repeating the same 6 lines of code every time we needed to transition to a particular screen */

/* FUTURE ENHANCEMENT: Another future enhancement that would add a bit more dynamic functionality is with the search
   field on the AddProductController.java, basically it is set up in a way so that when the field has a blank value
   it ALWAYS displays an error the user that they need to enter something within the field. However, I think it would be
   more beneficial to have this conditional only fire off the first time they enter the screen, and then when they
   actually enter appropriate values for the next tries it would display the respective values, BUT afterwards any time
   they enter a blank value it would reset the AllPartsTV to display all the parts again. Since, when the user conducts
   a search the function spits out the respective parts it finds, but doesn't "reset" or display all the parts after the
   user is done with the search, so this could be a very neat functionality. */

/** This class creates the main Inventory Application.*/
public class Main extends Application {

    /** Method that sets the stage and scene and loads up the MainForm.
     @param stage stage object
     @throws IOException exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /** Main method that adds sample car parts/products to the inventory.
     @param args arguments
     */
    public static void main(String[] args) {

        // Adding sample car parts
        Inventory.addPart(new InHouse(1, "Transmission", 3000.89, 10, 1, 20, 1298));
        Inventory.addPart(new InHouse(2, "Catalytic Converter", 750.89, 5, 1, 20, 1298));
        Inventory.addPart(new InHouse(3, "Headlights", 250.89, 7, 1, 20, 1298));
        Inventory.addPart(new Outsourced(4, "Brakes", 245.89, 3, 1, 25, "Toyota"));

        // Adding sample car products
        Inventory.addProduct(new Product(1, "Toyota Supra", 50000.99, 2, 1, 8));
        Inventory.addProduct(new Product(2, "Ford F150", 25000.99, 3, 1, 10));

        // Adding sample associated parts
        Objects.requireNonNull(Inventory.lookupProduct(2)).addAssociatedPart(Inventory.lookupPart(1));

        launch(args);
    }
}
