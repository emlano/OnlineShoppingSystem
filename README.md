# Online Shopping System
Java Object Oriented Programing coursework

Submitted as partial completion of Bachelor of Computer Science.

## Required Features
### Structure
* ✅ `Product` should be abstract and `Clothing`, `Electronic` must inherit from it.
* ✅ Add one constructor for each class.
* ✅ Add appropriate Getters/Setters.
* ✅ `Electronic` class contains `Brand` and `Warranty Period` as attributes.
* ✅ `Clothing` class contains `Color` and `Size` as attributes.
* ✅ Class `User` must represent a user account, attributes must contain `username`, `password`
* ✅ `ShoppingCart` must represent the cart of the `User`.
* ✅ `WestminsterShoppingManager` must implement `ShoppingManager` which maintains the list of products in the system

### Console Menu
* ✅ Add a `Product`, with the max capacity being 50.
* ✅ Delete a `Product` with the `ProductID`, Display info about the `Product` after deletion and the remaining `Product` count.
* ✅ List all `Product`s with all the information relating to them, Sort the list alphabetically.
* ✅ Save all information and program state in a file, Read back and reinstate program after a restart.

### Graphical User Interface
* ✅ `User` can select `Product` types from a dropdown menu to filter the displayed products.
* ✅ Display all relevant information about the `Product`s.
* ✅ `User` can sort product list alphabetically (Through selecting the `Product` name column header).
* ✅ `User` can add items to the shopping cart.
* ✅ `Product`s with low stock must be marked with Red color.
* ✅ When the `User` selects a product, its information should appear in a panel below the product display table.
* ✅ Shopping cart must calculate and display a `Total Price`
* Following discounts must be available to the `User`
    * ✅ '10%' Discount for the first purchase
    * ✅ '20%' Discount for buying 3 different `Product`s of same `Product Type`