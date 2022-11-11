package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kwong.inventoryapplication.Main;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class creates the AddPartController.*/
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    // Text field for part ID
    @FXML
    private TextField addPartIdTxt;

    // Text field for part name
    @FXML
    private TextField addPartNameTxt;

    // Text field for part inventory
    @FXML
    private TextField addPartInvTxt;

    // Text field for part price
    @FXML
    private TextField addPartPriceTxt;

    // Label for part machine ID
    @FXML
    private Label addPartMachineIdLabel;

    // Text field for part machine ID
    @FXML
    private TextField addPartMachineIdTxt;

    // Text field for part max stock
    @FXML
    private TextField addPartMaxTxt;

    // Text field for part min stock
    @FXML
    private TextField addPartMinTxt;

    // Radio button for outsourced parts
    @FXML
    private RadioButton addPartOutsourceRBtn;

    // Radio button for in house parts
    @FXML
    private RadioButton addPartInHouseRBtn;


    /** This method will save a part to Inventory granted all the conditions are met and fulfilled.
     This method is extensive in its conditions, checking to make sure that all fields are filled out, accounting for
     non-numeric, numeric values, integer ranges, as well as whether the part is in-house or out-sourced, more of the
     code's logic can be seen down below with block comments.
     @param actionEvent actionEvent
     @throws IOException IOException
     */
    @FXML
    public void addPartOnActionSavePart(ActionEvent actionEvent) throws IOException {

        /* If statement to check if any fields are blank, if so, require the user to fill them out. Also checks
        to see that numeric fields do not have characters in them */
        if (addPartNameTxt.getText().equals("")
                || addPartInvTxt.getText().equals("")
                || addPartPriceTxt.getText().equals("")
                || addPartMinTxt.getText().equals("")
                || addPartMaxTxt.getText().equals("")
                || addPartMachineIdTxt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill out all of the fields and try again");
            alert.showAndWait();
            return;
        } else if (addPartInvTxt.getText().matches(".*[a-z].*")
                        || addPartPriceTxt.getText().matches(".*[a-z].*")
                        || addPartMinTxt.getText().matches(".*[a-z].*")
                        || addPartMaxTxt.getText().matches(".*[a-z].*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You entered letters for one or more fields that require numbers, try again");
            alert.showAndWait();
            return;
        }


        String name = addPartNameTxt.getText();
        int inventory = Integer.parseInt(addPartInvTxt.getText());
        double price = Double.parseDouble(addPartPriceTxt.getText());
        int min = Integer.parseInt(addPartMinTxt.getText());
        int max = Integer.parseInt(addPartMaxTxt.getText());

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

        /* Multi-level if statement to check and see if the machine ID field has respective numbers, if so we can
        add that new in-house part to inventory, if not displaying error message. Also, another statement to see if
        out-sourced radio button is checked, if so, check to see that the field has all characters and add the
        out-sourced part to inventory, if not, display the warning message. */
        if (addPartInHouseRBtn.isSelected()) {
            int newPartId = generateNextId();
            String testMachineId = addPartMachineIdTxt.getText();
            if (testMachineId.matches(".*[0-9].*")) {
                int machineId = Integer.parseInt(testMachineId);
                InHouse inHousePart = new InHouse(newPartId, name, price, inventory, min, max, machineId);
                Inventory.addPart(inHousePart);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You entered letters for machineID , Please Try again");
                alert.showAndWait();
                return;
            }

        } else if (addPartOutsourceRBtn.isSelected()){
            int newPartId = generateNextId();
            String company = addPartMachineIdTxt.getText();
            if (company.matches(".*[a-z].*")) {
                Outsourced outsourcedPart = new Outsourced(newPartId, name, price, inventory, min, max, company);
                Inventory.addPart(outsourcedPart);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You entered numbers for company , Please Try again");
                alert.showAndWait();
                return;
            }
        }
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /** This method will return a user to the main form and not save any data entered into any fields.
     Added an if statement into this method to give the user a chance to cancel the "cancel button action" if they
     have left any of the fields populated, ie ask them for confirmation if they'd like to return to the main form.
     @param actionEvent actionEvent
     @throws IOException IOException
     */
    @FXML
    public void addPartOnActionCancelPart(ActionEvent actionEvent) throws IOException {
        if (addPartNameTxt.getText().equals("")
                && addPartInvTxt.getText().equals("")
                && addPartPriceTxt.getText().equals("")
                && addPartMinTxt.getText().equals("")
                && addPartMaxTxt.getText().equals("")
                && addPartMachineIdTxt.getText().equals("")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root).getRoot();
            stage.setScene(scene.getScene());
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Main Menu");
            alert.setContentText("You have entered data for one or more fields, your data will not be saved, are you sure you want to go back?");
            Optional<ButtonType> results = alert.showAndWait();
            if(results.orElse(null) == ButtonType.OK){
                Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
                stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root).getRoot();
                stage.setScene(scene.getScene());
                stage.show();
            }
        }
    }


    /** Method to fire off when the in-house radio button is clicked.
     Method will set the label of the machineID field to Machine ID.
     RUNTIME ERROR: I had some slight but small issues and logic errors working with the radio buttons and actually
     trying to not allow more than one radio button to be selected, because obviously when a user would try to select
     both the in-house and the out-sourced option, this would introduce a logical/runtime error. So I opted to do two
     separate and clean methods for each radio button and then just checking off whether the in-house or outsourced
     button was selected upon saving, and the actual fix to only allow one radio button to be clicked was the use of
     the radio button toggle group which I set up in the AddPartForm.
     */
    @FXML
    public void selectRBtnInHouse() {
        addPartMachineIdLabel.setText("Machine ID");
    }


    /** Method to fire off when the out-sourced radio button is clicked.
     Method will set the label of the machineID field to company name.
     */
    @FXML
    public void selectRBtnOutsource() {
        addPartMachineIdLabel.setText("Company Name");
    }


    /** This method will auto generate the next id based upon the Inventory size of all parts.
     @return id
     */
    public int generateNextId() {
        int id = 1;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            id++;
        }
        return  id;
    }


    /** This method initializes the resourceBundle.
     Within this method we initialize our resources as well as disable the addPartIdTxt field since it will be
     auto-generated.
     @param url url
     @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartIdTxt.setDisable(true);
    }
}

