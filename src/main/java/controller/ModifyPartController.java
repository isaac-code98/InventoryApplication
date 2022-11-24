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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class creates the ModifyPartController.*/
public class ModifyPartController implements Initializable {

    Stage stage;
    Scene scene;

    // Text field for the part ID on the ModifyPartForm
    @FXML
    private TextField modifyPartIdTxt;

    // Text field for the part name on the ModifyPartForm
    @FXML
    private TextField modifyPartNameTxt;

    // Text field for the part inventory on the ModifyPartForm
    @FXML
    private TextField modifyPartInvTxt;

    // Text field for the part price on the ModifyPartForm
    @FXML
    private TextField modifyPartPriceTxt;

    // Text field for the part max on the ModifyPartForm
    @FXML
    private TextField modifyPartMaxTxt;

    // Text field for the part min on the ModifyPartForm
    @FXML
    private TextField modifyPartMinTxt;

    // Label for the part machine ID on the ModifyPartForm
    @FXML
    private Label modifyPartMachineIdLabel;

    // Text field for the part machine ID on the ModifyPartForm
    @FXML
    private TextField modifyPartMachineIdTxt;

    // Radio button for the In-House option on the ModifyPartForm
    @FXML
    private RadioButton modifyPartInHouseRBtn;

    // Radio button for the outsourced option on the ModifyPartForm
    @FXML
    private RadioButton modifyPartOutsourceRBtn;

    // Instantiating a selectedPart of the Part class
    public Part selectedPart;

    /** This method will save a modified part to Inventory granted all the conditions are met and fulfilled.
     This method is extensive in its conditions, checking to make sure that all fields are filled out, accounting for
     non-numeric, numeric values, integer ranges, as well as whether the part is in-house or out-sourced, more of the
     code's logic can be seen down below with block comments.
     @param actionEvent actionEvent
     @throws IOException IOException
     */
    @FXML
    public void modifyPartOnActionSave(ActionEvent actionEvent) throws IOException {

        /* If statement to check if any fields are blank, if so, require the user to fill them out. Also checks
        to see that numeric fields do not have characters in them */
        if (modifyPartNameTxt.getText().equals("")
                || modifyPartInvTxt.getText().equals("")
                || modifyPartPriceTxt.getText().equals("")
                || modifyPartMinTxt.getText().equals("")
                || modifyPartMaxTxt.getText().equals("")
                || modifyPartMachineIdTxt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill out all of the fields and try again");
            alert.showAndWait();
            return;
        } else if (modifyPartInvTxt.getText().matches(".*[a-z].*")
                || modifyPartPriceTxt.getText().matches(".*[a-z].*")
                || modifyPartMinTxt.getText().matches(".*[a-z].*")
                || modifyPartMaxTxt.getText().matches(".*[a-z].*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You entered letters for one or more fields that require numbers, try again");
            alert.showAndWait();
            return;
        }

        String name = modifyPartNameTxt.getText();
        double price = Double.parseDouble(modifyPartPriceTxt.getText());
        int inventory = Integer.parseInt(modifyPartInvTxt.getText());
        int min = Integer.parseInt(modifyPartMinTxt.getText());
        int max = Integer.parseInt(modifyPartMaxTxt.getText());

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


        /* Multi-level if statement to check and see if the machine ID field has respective numbers, if so we can
        add that new in-house part to inventory, if not displaying error message. Also, another statement to see if
        out-sourced radio button is checked, if so, check to see that the field has all characters and add the
        out-sourced part to inventory, if not, display the warning message. */
        if (modifyPartInHouseRBtn.isSelected()) {
            String testMachineId = modifyPartMachineIdTxt.getText();

            if (testMachineId.matches(".*[a-z].*")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You entered letters for machineID , Please Try again");
                alert.showAndWait();
                return;
            } else {
                int machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
                InHouse modifiedPart = new InHouse(selectedPart.getId(), name, price, inventory, min, max, machineId);
                int index = selectedPart.getId() - 1;
                Inventory.updatePart(index, modifiedPart);
            }
        } else if (modifyPartOutsourceRBtn.isSelected()) {
            String company = modifyPartMachineIdTxt.getText();

            if (company.matches(".*[0-9].*")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You entered numbers for company , Please Try again");
                alert.showAndWait();
                return;
            } else {
                Outsourced modifiedPart = new Outsourced(selectedPart.getId(), name, price, inventory, min, max, company);
                int index = selectedPart.getId() - 1;
                Inventory.updatePart(index, modifiedPart);
            }
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }


    /** This method will return a user to the main form.
     The method also contains a multi-level if statement where we check to see if the user has actually modified any
     data from within the fields, if so, we want them to confirm that they would like to go back to the main form
     screen since their data will not be saved, likewise if they haven't changed any data about the part, we account
     for this, and simply transition them back to the main form screen.
     @param actionEvent actionEvent
     @throws IOException IOException
     */
    @FXML
    public void modifyPartOnActionCancel(ActionEvent actionEvent) throws IOException {

        if (!modifyPartNameTxt.getText().equals(selectedPart.getName())
                || !modifyPartInvTxt.getText().equals(String.valueOf(selectedPart.getStock()))
                || !modifyPartPriceTxt.getText().equals(String.valueOf(selectedPart.getPrice()))
                || !modifyPartMinTxt.getText().equals(String.valueOf(selectedPart.getMin()))
                || !modifyPartMaxTxt.getText().equals(String.valueOf(selectedPart.getMax()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Main Menu");
            alert.setContentText("You have changed data for one or more fields, your data will not be saved, are you sure you want to go back?");
            Optional<ButtonType> results = alert.showAndWait();
            if (results.orElse(null) == ButtonType.OK) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainForm.fxml")));
            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root).getRoot().getScene();
            stage.setScene(scene);
            stage.show();

        }
    }

    /** Method to fire off when the in-house radio button is clicked.
     Method will set the label of the machineID field to Machine ID.
     */
    public void selectRBtnInHouse() {
        modifyPartMachineIdLabel.setText("Machine ID");
    }

    /** Method to fire off when the out-sourced radio button is clicked.
     Method will set the label of the machineID field to company name.
     */
    public void selectRBtnOutsource() {
        modifyPartMachineIdLabel.setText("Company Name");
    }


    /** This method sets up the ModifyPartForm with the correct data that was selected from the MainForm.
     Within this method, it is called when a part is selected and the modify button is clicked on the MainForm, from
     there we check to see if the selected part is an in-house or out-sourced part, then we grab all of that part's
     data through its getters and assign them to respective strings which are to be used by to populate the
     ModifyPartForm's text fields initially.
     @param partSelected object of Part
     */
    public void setSelectedPart(Part partSelected) {
        selectedPart = partSelected;

        if (selectedPart instanceof InHouse) {
            modifyPartMachineIdTxt.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
            modifyPartMachineIdLabel.setText("Machine ID");
            modifyPartInHouseRBtn.setSelected(true);

        } else if (selectedPart instanceof Outsourced) {
            modifyPartMachineIdTxt.setText(((Outsourced) selectedPart).getCompanyName());
            modifyPartMachineIdLabel.setText("Company Name");
            modifyPartOutsourceRBtn.setSelected(true);
        }

        String id = String.valueOf(selectedPart.getId());
        String name = selectedPart.getName();
        String inv = String.valueOf(selectedPart.getStock());
        String price = String.valueOf(selectedPart.getPrice());
        String max = String.valueOf(selectedPart.getMax());
        String min = String.valueOf(selectedPart.getMin());

        modifyPartIdTxt.setText(id);
        modifyPartNameTxt.setText(name);
        modifyPartInvTxt.setText(inv);
        modifyPartPriceTxt.setText(price);
        modifyPartMaxTxt.setText(max);
        modifyPartMinTxt.setText(min);
        modifyPartIdTxt.setDisable(true);
    }


    /** This method initializes the resourceBundle.
     Within this method we initialize our resources as well as disable the modifyPartIdTxt field since it will be
     auto-generated.
     @param url url
     @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyPartIdTxt.setDisable(true);
    }
}
