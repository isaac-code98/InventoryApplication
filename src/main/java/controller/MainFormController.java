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
import java.util.Optional;
import java.util.ResourceBundle;

/** This class creates the MainFormController.*/
public class MainFormController implements Initializable {

    // TableView for Parts
    @FXML
    private TableView<Part> partsTableView;

    // Part id TableColumn for Parts
    @FXML
    private TableColumn<Part, Integer> partsTVColumnId;

    // Part name TableColumn for Parts
    @FXML
    private TableColumn<Part, String> partsTVColumnName;

    // Part inventory TableColumn for Parts
    @FXML
    private TableColumn<Part, Integer> partsTVColumnInventory;

    // Part price TableColumn for Parts
    @FXML
    private TableColumn<Part, Double> partsTVColumnPrice;

    // Part search field for Parts
    @FXML
    private TextField partSearchField;


    // TableView for Products
    @FXML
    private TableView<Product> productsTableView;

    // Product id TableColumn for Products
    @FXML
    private TableColumn<Product, Integer> productTVColumnId;

    // Product name TableColumn for Products
    @FXML
    private TableColumn<Product, String> productTVColumnName;

    // Product inventory TableColumn for Products
    @FXML
    private TableColumn<Product, Integer> productTVColumnInventory;

    // Product price TableColumn for Products
    @FXML
    private TableColumn<Product, Double> productTVColumnPrice;

    // Product search field for Products
    @FXML
    private TextField productSearchField;

    /** This method will set up our AddPart scene after the respective event is fired.
     @param event event linked to add button under Parts TableView
     @throws IOException IOException
     */
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AddPartForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }


    /** This method will set up our ModifyPart scene after the respective event is fired.
     We will also initialize a selectedPart on the highlighted/selected item of the Parts model, so it will take us to
     that exact menu to modify said part, also set up an if statement that says if the selectedPart is null, notify
     user that they did not select a part.
     @param event event linked to modify button under Parts TableView
     @throws IOException IOException
     */
    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException {

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select a part to modify , Please Try again");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("ModifyPartForm.fxml"));
            Parent root = loader.load();
            ModifyPartController control = loader.getController();
            control.setSelectedPart(selectedPart);

            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    /** This method will delete and remove a selectedPart from the Inventory.
     Here we create an object selectedPart of the Part class that will be taken from the Part model in the respective
     and highlighted area, we then delete the selectedPart from the Inventory, like the method above, set up an
     if statement to warn the user if they did not select a part.
     */
    @FXML
    public void onActionDeletePart() {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select a Part, Please Try again");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Are you sure you want to delete this part? This is NOT reversible");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(null) == ButtonType.OK){
                    Inventory.deletePart(selectedPart);
            }
        }
    }


    /** This method will delete and remove a selectedPart from the Inventory.
     Here we create an object selectedPart of the Part class that will be taken from the Part model in the respective
     and highlighted area, we then delete the selectedPart from the Inventory.
     @param actionEvent this event is linked to the delete button under the Parts TableView
     @throws IOException IOException
     */
    @FXML
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AddProductForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /** This method will retrieve the selectedProduct on the productsTableView and take us to the ModifyProductForm.
     Here we create an object selectedProduct of the Product class that will be taken from the Product model in the
     respective and highlighted area, we then load a new scene in which we will be sent to the ModifyProductForm with
     its data.
     @param actionEvent this event is linked to the modify button under the Products TableView
     @throws IOException IOException
     */
    @FXML
    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException {

        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select a Product, Please Try again");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("ModifyProductForm.fxml"));
            Parent root = loader.load();
            ModifyProductController control = loader.getController();
            control.setSelectedProduct(selectedProduct);

            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
    }


    /** This method will delete and remove a selectedProduct from the Inventory.
     Here we create an object selectedProduct of the Product class that will be taken from the Product model in the
     respective and highlighted area, we then delete the selectedProduct from the Inventory, we also display a respective
     error message indicating to the user that they need to remove associated parts from a product before deleting.
     */
    @FXML
    public void onActionDeleteProduct() {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select a Product, Please Try again");
            alert.show();
        } else if (selectedProduct.getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning product has associated parts");
            alert.setContentText("This product has associated parts, please remove those first before proceeding");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Are you sure you want to delete this product? This is NOT reversible");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(null) == ButtonType.OK){
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }


    /** This method will filter and search for a specific part related to user input.
     Here we create an object selectedProduct of the Product class that will be taken from the Product model in the respective
     and highlighted area, we then delete the selectedProduct from the Inventory.
     */
    @FXML
    public void searchFieldPart() {
        String partSearch = partSearchField.getText();

        /* If statement to check if search field is blank, display proper error message if so. Else, check to see
        that the entire search field is all characters, if so, we get all parts from Inventory and make a newPartsList
        and then add all parts to that list that match the search field. We'll do the exact same thing for integers
        and search/loop for the respective IDs */

        if (partSearch.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did enter anything in the search field, Please Try again");
            alert.show();

        } else if (!partSearch.isBlank() && partSearch.matches(".*[a-z].*")){
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
                    partsTableView.setItems(newPartsList);
                }
            }
        } else {
            Part givenPart = Inventory.lookupPart(Integer.parseInt(partSearch));
            if (givenPart != null) {
                partsTableView.getSelectionModel().select(givenPart);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Not Found");
                alert.setContentText("Part does not exist by the id you searched");
                alert.show();
            }
        }
    }


    /** This method will filter and search for a specific product related to user input.
     Here we create an object selectedProduct of the Product class that will be taken from the Product model in the respective
     and highlighted area, we then delete the selectedProduct from the Inventory.
     */
    @FXML
    public void searchFieldProduct() {
        String productSearch = productSearchField.getText();

        /* This multi-level if-statement and for-loop structure is almost identical as the searchFieldPart method
        with the exception that we will be looping through the products, not parts in inventory */

        if (productSearch.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You did not select a Product, Please Try again");
            alert.show();

        } else if (!productSearch.isBlank() && productSearch.matches(".*[a-z].*")){
            ObservableList<Product> allProductsList = Inventory.getAllProduct();
            ObservableList<Product> newProductsList = FXCollections.observableArrayList();
            Product product;
            if(allProductsList.size() > 0){
                for (Product value : allProductsList) {
                    product = value;
                    if (product.getName().toLowerCase().contains(productSearch.toLowerCase())) {
                        newProductsList.add(product);
                    }
                }

                if (newProductsList.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Product Not Found");
                    alert.setContentText("Product does not exist by the name you searched");
                    alert.show();
                } else {
                    productsTableView.setItems(newProductsList);
                }
            }
        } else {
            Product givenProduct = Inventory.lookupProduct(Integer.parseInt(productSearch));
            if (givenProduct != null) {
                productsTableView.getSelectionModel().select(givenProduct);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product Not Found");
                alert.setContentText("Product does not exist by the id you searched");
                alert.show();
            }
        }
    }


    /** This method will exit the application upon clicking the exit button.
     */
    @FXML
    public void onActionExitApplication() {
        System.exit(0);
    }


    /** This method initializes the resourceBundle.
     Within this method we set up the respective parts and products table views so that they are able to take in the
     respective objects of Part and Product as well as all of their properties that we want to show the user.
     @param url url
     @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        partsTVColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsTVColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsTVColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsTVColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProduct());
        productTVColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productTVColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productTVColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productTVColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
