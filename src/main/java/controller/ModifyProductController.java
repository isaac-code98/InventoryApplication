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

/** This class creates the ModifyProductController.*/
public class ModifyProductController implements Initializable {

    // Text field for the product ID on the ModifyProductForm
    @FXML
    private TextField modifyProductIdTxt;

    // Text field for the product name on the ModifyProductForm
    @FXML
    private TextField modifyProductNameTxt;

    // Text field for the product inventory on the ModifyProductForm
    @FXML
    private TextField modifyProductInvTxt;

    // Text field for the product price on the ModifyProductForm
    @FXML
    private TextField modifyProductPriceTxt;

    // Text field for the product max on the ModifyProductForm
    @FXML
    private TextField modifyProductMaxTxt;

    // Text field for the product min on the ModifyProductForm
    @FXML
    private TextField modifyProductMinTxt;


    // TableView for all parts in inventory
    @FXML
    private TableView<Part> modifyProductAllPartsTV;

    // Part ID TableColumn for AllPartsTV
    @FXML
    private TableColumn<Part, Integer> modifyProductAllPartsColumnId;

    // Part name TableColumn for AllPartsTV
    @FXML
    private TableColumn<Part, String> modifyProductAllPartsColumnName;

    // Part inventory TableColumn for AllPartsTV
    @FXML
    private TableColumn<Part, Integer> modifyProductAllPartsColumnInv;

    // Part price TableColumn for AllPartsTV
    @FXML
    private TableColumn<Part, Double> modifyProductAllPartsColumnPrice;


    // TableView for associated parts pertaining to the current product
    @FXML
    private TableView<Part> modifyProductAssociatedPartsTV;

    // Part ID TableColumn for AssociatedPartsTV
    @FXML
    private TableColumn<Part, Integer> modifyProductAssociatedPartsColumnId;

    // Part name TableColumn for AssociatedPartsTV
    @FXML
    private TableColumn<Part, String> modifyProductAssociatedPartsColumnName;

    // Part inventory TableColumn for AssociatedPartsTV
    @FXML
    private TableColumn<Part, Integer> modifyProductAssociatedPartsColumnInv;

    // Part price TableColumn for AssociatedPartsTV
    @FXML
    private TableColumn<Part, Double> modifyProductAssociatedPartsColumnPrice;


    // Text field for searching for all parts
    @FXML
    private TextField modifyProductSearchTxtField;

    // Create associatedParts as an ObservableList to hold Part objects, we then pass this into an observableArrayList.
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // Create object selectedProduct instance of Product class
    private Product selectedProduct;



    /** This method will add a part from all parts in inventory to be associated with the product.
     Here we first attempt to get the highlighted/selected part from the AllPartsTV, if the user did not select a part,
     we display a warning to them, if they did select a part, we will add the selectedPart to the associatedParts list
     and set the AssociatedPartsTV to the list of associatedParts which will include that selectedPart.
     */
    @FXML
    public void modifyProductOnActionAddPart() {
        Part selectedPart = modifyProductAllPartsTV.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select a part to add to the product, please try again");
            alert.show();
        } else {
            associatedParts.add(selectedPart);
            modifyProductAssociatedPartsTV.setItems(associatedParts);
        }
    }


    /** This method will remove an associated part from the current product.
     Here we first attempt to get the highlighted/selected part from the AssociatedPartsTV, if the user did not select
     a part, we display a warning to them indicating them that they need to select one, otherwise, we can remove that
     selected part from the associatedParts list as well as update/set the AssociatedPartsTV items accordingly to update
     the TableView.
     */
    @FXML
    public void modifyProductOnActionRemove() {
        Part selectedPart = modifyProductAssociatedPartsTV.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select an associated part to remove from the product, please try again");
            alert.show();
        } else {
            associatedParts.remove(selectedPart);
            modifyProductAssociatedPartsTV.setItems(associatedParts);
        }
    }


    /** This method will save a product's modifications to Inventory, granted the required conditions are met.
     The below method has multiple if statements to check that all fields are filled out, accounts for non-numeric cases
     as well as makes sure that min, max, price, and inventory bound cases are covered.
     @param actionEvent actionEvent
     @throws IOException IOException.
     */
    @FXML
    public void modifyProductOnActionSave(ActionEvent actionEvent) throws IOException {

        /* If statement to check if any fields are blank, if so, require the user to fill them out. Also checks
        to see that numeric fields do not have characters in them */
        if (modifyProductNameTxt.getText().equals("")
                || modifyProductInvTxt.getText().equals("")
                || modifyProductPriceTxt.getText().equals("")
                || modifyProductMinTxt.getText().equals("")
                || modifyProductMaxTxt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill out all of the fields and try again");
            alert.showAndWait();
            return;
        } else if (modifyProductInvTxt.getText().matches(".*[a-z].*")
                || modifyProductPriceTxt.getText().matches(".*[a-z].*")
                || modifyProductMinTxt.getText().matches(".*[a-z].*")
                || modifyProductMaxTxt.getText().matches(".*[a-z].*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You entered letters for one or more fields that require numbers, try again");
            alert.showAndWait();
            return;
        }

        int id = Integer.parseInt(modifyProductIdTxt.getText());
        String name = modifyProductNameTxt.getText();
        double price = Double.parseDouble(modifyProductPriceTxt.getText());
        int inventory = Integer.parseInt(modifyProductInvTxt.getText());
        int max = Integer.parseInt(modifyProductMaxTxt.getText());
        int min = Integer.parseInt(modifyProductMinTxt.getText());

        /* If statements to check that no numeric fields can have values less than zero and also that max fields must
        be greater than min and vice versa. Also check to see that the inventory can't be greater than max or less
        than min. */
        if (min <= 0 || max <= 0 || price <= 0 || inventory <= 0) {
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
        Product newProduct = new Product(id, name, price, inventory, min, max);
        for (Part part : associatedParts) {
            newProduct.addAssociatedPart(part);
        }
        int position = id - 1;
        Inventory.updateProduct(position, newProduct);

        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    /** This method will return a user to the MainForm and won't save any fields that were modified.
     Here we conduct a simple if statement that checks to see if the user has made any modifications to the text fields
     associated with the product, if so, we ask them for confirmation if they'd like to return to the main menus since
     none of their changes will be saved, if they haven't made any modifications we can just transition them back to the
     main form.
     @param actionEvent actionEvent
     @throws IOException IOException
     */
    @FXML
    public void modifyProductOnActionCancel(ActionEvent actionEvent) throws IOException {
        if (!modifyProductNameTxt.getText().equals(selectedProduct.getName())
                || !modifyProductInvTxt.getText().equals(String.valueOf(selectedProduct.getStock()))
                || !modifyProductPriceTxt.getText().equals(String.valueOf(selectedProduct.getPrice()))
                || !modifyProductMinTxt.getText().equals(String.valueOf(selectedProduct.getMin()))
                || !modifyProductMaxTxt.getText().equals(String.valueOf(selectedProduct.getMax()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Main Menu");
            alert.setContentText("You have changed data for one or more fields, your data will not be saved, are you sure you want to go back?");
            Optional<ButtonType> results = alert.showAndWait();
            if (results.orElse(null) == ButtonType.OK) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
    public void modifyProductOnActionSearch() {

        String partSearch = modifyProductSearchTxtField.getText();

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
                    modifyProductAllPartsTV.setItems(newPartsList);
                }
            }
        } else {
            Part givenPart = Inventory.lookupPart(Integer.parseInt(partSearch));
            if (givenPart != null) {
                modifyProductAllPartsTV.getSelectionModel().select(givenPart);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Not Found");
                alert.setContentText("Part does not exist by the id you searched");
                alert.show();
            }
        }

    }


    /** This method sets up AssociatedPartsTV with the data that was selected from the product from the MainForm.
     Within this method, it is called when a product is selected and the modify button is clicked on the MainForm, from
     there we set the text fields according to the selectedProduct and we have to set each cell value factory to a new
     property value factory that is able to reference the appropriate values from associatedParts.
     @param product object of Product
     */
    public void setSelectedProduct(Product product) {
        selectedProduct = product;
        modifyProductIdTxt.setText(String.valueOf(selectedProduct.getId()));
        modifyProductIdTxt.setDisable(true);
        modifyProductNameTxt.setText(selectedProduct.getName());
        modifyProductInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceTxt.setText(Double.toString(selectedProduct.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinTxt.setText(String.valueOf(selectedProduct.getMin()));

        associatedParts = selectedProduct.getAllAssociatedParts();
        modifyProductAssociatedPartsTV.setItems(associatedParts);
        modifyProductAssociatedPartsColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAssociatedPartsColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAssociatedPartsColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAssociatedPartsColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /** This method initializes the resourceBundle.
     From this method we set the AllPartsTV accordingly with all the parts from Inventory, we then have to set each
     column accordingly with a new property value factory that is able to reference the appropriate data from all of our
     parts.
     @param url url
     @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductAllPartsTV.setItems(Inventory.getAllParts());
        modifyProductAllPartsColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAllPartsColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAllPartsColumnInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAllPartsColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}













