package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates the Inventory model.*/
public class Inventory {

    // Create allParts as an ObservableList to hold Part objects, we then pass this into an observableArrayList.
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();

    // Create allProducts as an ObservableList to hold Product objects, we then pass this into an observableArrayList.
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /** This method adds a newPart into allParts list.
     @param newPart object newPart of Part
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /** This method adds a newProduct of the Product into allProducts list.
     @param newProduct object newProduct of Product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }


    /** This method looks up a part with a given partId.
     Here we use a for each loop where we iterate through each part of the allParts list and if a given part id matches
     the partId that was passed in we return that part.
     @param partId int partId
     @return part
     */
    public static Part lookupPart (int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }


    /** This method looks up a product with a given productId.
     Here we use a for each loop where we iterate through each product of the allProducts list and if a given product id
     matches the productId that was passed in we return that product.
     @param productId int productId
     @return product
     */
    public static Product lookupProduct (int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }


    /** This method looks up a part with a given partName.
     Here we use a for each loop to parse through all parts of the allParts list, and if the partName that was passed in
     has any characters or whose string matches the part name that we retrieve we add that to the newList and return the
     newList.
     @param partName String partName
     @return newList
     */
    public static ObservableList<Part> lookupPart (String partName) {
        ObservableList<Part> newPartList = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                newPartList.add(part);
                break;
            }
        }
        return  newPartList;
    }


    /** This method looks up a product with a given productName.
     Here we use a for each loop to parse through all products of the allProducts list, and if the productName that was
     passed in has any characters or whose string matches the product name that we retrieve we add that to the newList
     and return the newList.
     @param productName takes a String productName
     @return newList
     */
    public static ObservableList<Product> lookupProduct (String productName) {
        ObservableList<Product> newProductList = FXCollections.observableArrayList();

        for (Product product : allProducts) {

            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                newProductList.add(product);
                break;
            }
        }
        return newProductList;
    }


    /** This method sets and updates allParts given a selectedPart.
     @param index object newProduct of Product
     @param selectedPart object selectedPart of Part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }


    /** This method sets and updates allProducts a given newProduct.
     @param index int index
     @param newProduct object newProduct of Product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }


    /** This method deletes a selectedPart from the allParts list.
     @param selectedPart takes a selectedPart object of Part
     @return allParts.remove(selectedPart)
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }


    /** This method deletes a selectedProduct from the allProducts list.
     @param selectedProduct takes a selectedProduct object of Product
     @return allProducts.remove(selectedProduct)
     */
    public static boolean deleteProduct (Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }


    /** This method retrieves the allParts list given that the list is NOT empty.
     @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        if (allParts.isEmpty()) {
            System.out.println("No parts available in inventory!");
        }
        return allParts;
    }


    /** This method retrieves the allProducts list given that the list is NOT empty.
     @return allProducts
     */
    public static ObservableList<Product> getAllProduct() {
        if (allProducts.isEmpty()) {
            System.out.println("No products available in inventory!");
        }
        return allProducts;
    }

}
