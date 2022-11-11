package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kwong.inventoryapplication.Main;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class creates the AddProductController.*/
public class AddProductController implements Initializable {

    // Text field for the product ID on the AddProductForm
    @FXML
    private TextField addProductIdTxt;

    // Text field for the product name on the AddProductForm
    @FXML
    private TextField addProductNameTxt;

    // Text field for the product inventory on the AddProductForm
    @FXML
    private TextField addProductInvTxt;

    // Text field for the product price on the AddProductForm
    @FXML
    private TextField addProductPriceTxt;

    // Text field for the product max stock on the AddProductForm
    @FXML
    private TextField addProductMaxTxt;

    // Text field for the product min stock on the AddProductForm
    @FXML
    private TextField addProductMinTxt;



    // TableView for all parts in inventory
    @FXML
    private TableView<Part> addProductAllPartsTV;

    // Part ID TableColumn for AllPartsTV
    @FXML
    private TableColumn<Part, Integer> addProductAllPartsColumnId;

    // Part name TableColumn for AllPartsTV
    @FXML
    private TableColumn<Part, String> addProductAllPartsColumnName;

    // Part inventory TableColumn for AllPartsTV
    @FXML
    private TableColumn<Part, Integer> addProductAllPartsColumnInv;

    // Part price TableColumn for AllPartsTV
    @FXML
    private TableColumn<Part, Double> addProductAllPartsColumnPrice;



    // TableView for associated parts in inventory
    @FXML
    private TableView<Part> addProductAssociatedPartsTV;

    // Part ID TableColumn for AssociatedPartsTV
    @FXML
    private TableColumn<Part, Integer> addProductAssociatedColumnId;

    // Part name TableColumn for AssociatedPartsTV
    @FXML
    private TableColumn<Part, String> addProductAssociatedColumnName;

    // Part inventory TableColumn for AssociatedPartsTV
    @FXML
    private TableColumn<Part, Integer> addProductAssociatedColumnInv;

    // Part price TableColumn for AssociatedPartsTV
    @FXML
    private TableColumn<Part, Double> addProductAssociatedColumnPrice;

    // Text field for searching for all parts
    @FXML
    private TextField addProductSearchField;

    // Create associatedParts as an ObservableList to hold Part objects, we then pass this into an observableArrayList.
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    Stage stage;
    Scene scene;


    /** This method will add a part from all parts in inventory to be associated with the product.
     Here we first attempt to get the highlighted/selected part from the AllPartsTV, if the user did not select a part,
     we display a warning to them, if they did select a part, we will add the selectedPart to the associatedParts list
     and set the AssociatedPartsTV to the list of associatedParts which will include that selectedPart.
     */
    @FXML
    public void addProductOnActionAddPart() {
        Part selectedPart = addProductAllPartsTV.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select a part to add to your new product, Please Try again");
            alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
            addProductAssociatedPartsTV.setItems(associatedParts);
        }
    }


    /** This method will remove an associated part from the current product.
     Here we first attempt to get the highlighted/selected part from the AssociatedPartsTV, if the user did not select
     a part, we display a warning to them indicating them that they need to select one, otherwise, we can remove that
     selected part from the associatedParts list as well as update/set the AssociatedPartsTV items accordingly to update
     the TableView.
     */
    @FXML
    public void addProductOnActionRmvAssociatedPart() {
        Part selectedPart = addProductAssociatedPartsTV.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select a part to add to your new product, Please Try again");
            alert.show();
        } else {
            associatedParts.remove(selectedPart);
            addProductAssociatedPartsTV.setItems(associatedParts);
        }
    }


    /** This method will save a product to Inventory, granted the required conditions are met.
     The below method has multiple if statements to check that all fields are filled out, accounts for non-numeric cases
     as well as makes sure that min, max, price, and inventory bound cases are covered.
     @param actionEvent actionEvent
     @throws IOException IOException
     */
    @FXML
    public void addProductOnActionSave(ActionEvent actionEvent) throws IOException {

        /* If statement to check if any fields are blank, if so, require the user to fill them out. Also checks
        to see that numeric fields do not have characters in them */
        if (addProductNameTxt.getText().equals("")
                || addProductInvTxt.getText().equals("")
                || addProductPriceTxt.getText().equals("")
                || addProductMinTxt.getText().equals("")
                || addProductMaxTxt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill out all of the fields and try again");
            alert.showAndWait();
            return;
        } else if (addProductInvTxt.getText().matches(".*[a-z].*")
                || addProductPriceTxt.getText().matches(".*[a-z].*")
                || addProductMinTxt.getText().matches(".*[a-z].*")
                || addProductMaxTxt.getText().matches(".*[a-z].*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You entered letters for one or more fields that require numbers, try again");
            alert.showAndWait();
            return;
        }

        String name = addProductNameTxt.getText();
        double price = Double.parseDouble(addProductPriceTxt.getText());
        int inventory = Integer.parseInt(addProductInvTxt.getText());
        int min = Integer.parseInt(addProductMinTxt.getText());
        int max = Integer.parseInt(addProductMaxTxt.getText());

        /* If statements to check that no numeric fields can have values less than zero and also that max fields must
        be greater than min and vice versa. Also check to see that the inventory can't be greater than max or less
        than min. */
        if (min < 0 || max < 0 || price < 0 || inventory < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter values greater than 0 for min, max, price, inventory");
            alert.showAndWait();
            return;
        }

        if (max < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Max value must be greater than min");
            alert.showAndWait();
            return;
        } else if (inventory > max || inventory < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory must be between max and min bounds");
            alert.showAndWait();
            return;
        }

        /* Here we instantiate a newProduct object from Product class with the required props we want, we then loop
        through a part instantiation in our associatedParts list that will allow us to add any added associatedParts
        to the newProduct list, finally, we add and save that product to the Inventory. */
        Product newProduct = new Product(generateNextId(), name, price, inventory, min, max);
        for (Part part : associatedParts) {
            newProduct.addAssociatedPart(part);
        }
        Inventory.addProduct(newProduct);

        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /** This method will return a user to the MainForm and won't save any fields that were populated.
     Here we conduct a simple if statement that checks to see if the user populated any of the text fields, if so, we
     ask for confirmation if they want to cancel and return to the MainForm, likewise, if they haven't entered any
     values in any fields we can just return them back to the MainForm.
     @param actionEvent actionEvent
     @throws IOException IOException
     */
    @FXML
    public void addProductOnActionCancel(ActionEvent actionEvent) throws IOException {

        if (addProductNameTxt.getText().equals("")
                && addProductInvTxt.getText().equals("")
                && addProductPriceTxt.getText().equals("")
                && addProductMinTxt.getText().equals("")
                && addProductMaxTxt.getText().equals("")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root).getRoot().getScene();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Main Menu");
            alert.setContentText("You have entered data for one or more fields, your data will not be saved, are you sure you want to go back?");
            Optional<ButtonType> results = alert.showAndWait();
            if(results.orElse(null) == ButtonType.OK){
                Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
                stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root).getRoot().getScene();
                stage.setScene(scene);
                stage.show();
            }
        }
    }


    /** This method will filter and search for a specific part in all Inventory.
     Within this method, we first check to see if the search field is blank, if so, display the respective error
     message, if not, we then set up a conditional to check if the user entered characters or integers, if they entered
     characters we simply loop through the allPartsList to find the searched name to the part name and return the
     newPartsList, if we reached the end of the loop, we display a message that says so. Likewise for integers, we use
     the lookupPart function to see if the searched IDs to match to any part and return them accordingly.
     */
    @FXML
    void addProductOnActionSearch() {
        String partSearch = addProductSearchField.getText();

        if (partSearch.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not enter anything in the search field, Please Try again");
            alert.show();

        } else if (!partSearch.isBlank() && partSearch.matches(".*[a-z].*")) {
            ObservableList<Part> allPartsList = Inventory.getAllParts();
            ObservableList<Part> newPartsList = FXCollections.observableArrayList();
            Part part;

            if(allPartsList.size() > 0){
                for (Part value : allPartsList) {
                    part = value;
                    if (part.getName().toLowerCase().contains(partSearch.toLowerCase())) {
                        newPartsList.add(part);
                    }
                }

                if (newPartsList.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Part Not Found");
                    alert.setContentText("Part does not exist by the name you searched");
                    alert.show();
                } else {
                    addProductAllPartsTV.setItems(newPartsList);
                }
            }
        } else {
            Part givenPart = Inventory.lookupPart(Integer.parseInt(partSearch));
            if (givenPart != null) {
                addProductAllPartsTV.getSelectionModel().select(givenPart);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Not Found");
                alert.setContentText("Part does not exist by the id you searched");
                alert.show();
            }
        }

    }

    /** This method will generate the next ID based upon the Inventory size of all products.
     @return id
     */
    public int generateNextId() {
        int id = 1;
        for (int i = 0; i < Inventory.getAllProduct().size(); i++) {
            id++;
        }
        return id;
    }


    /** This method initializes the resourceBundle.
     Within this method we create a new ObservableList of all products, disable the product ID text field and instead
     set it to the generated ID accordingly, then we set up the AllPartsTV as well as the AssociatedPartsTV
     respectively, we have to set each cell value factory to a new property value factory that is able to reference the
     appropriate value that we pass in, whether that be from AllParts or AssociatedParts.
     @param url url
     @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Product> newList = Inventory.getAllProduct();
        addProductIdTxt.setDisable(true);
        int newId = newList.size() + 1;
        addProductIdTxt.setText(String.valueOf(newId));

        addProductAllPartsTV.setItems(Inventory.getAllParts());
        addProductAllPartsColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAllPartsColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAllPartsColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAllPartsColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductAssociatedPartsTV.setItems(associatedParts);
        addProductAssociatedColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAssociatedColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAssociatedColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAssociatedColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
