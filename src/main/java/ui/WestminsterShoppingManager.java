package ui;

import comparators.UsernameComparator;
import enums.Size;
import exceptions.*;
import lib.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ui.gui.GraphicalInterface;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WestminsterShoppingManager implements ShoppingManager {
    private ArrayList<Product> productList;
    private ArrayList<User> userList;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    /**
     * Adds a {@link Product} to the product list.
     * @param product is the {@link Product} to be added.
     */
    @Override
    public void addProduct(Product product) throws NonUniqueProductIdException, CapacityOverloadException {
        if (isIdNotUnique(product.getId())) {
            throw new NonUniqueProductIdException();
        }

        if (this.productList.size() == 50) {
            throw new CapacityOverloadException();
        }

        this.productList.add(product);
    }

    /**
     * Checks if a product has been registered using the id provided
     * @param id to be checked against all existing products
     * @return boolean 
     */
    private boolean isIdNotUnique(String id) {
        return this.productList.stream()
            .anyMatch(e -> e.getId().equals(id));
    }

    /**
     * Searches and returns a Product matching its product id
     * @param id product id
     * @return returns the {@link Product} if found, else returns null
     */
    public Product getProduct(String id) throws ProductNotFoundException {
        Product product = this.productList.stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElse(null);

        if (product == null) throw new ProductNotFoundException();

        return product;
    }

    /**
     * Deletes a {@link Product} from the product list.
     * @param productId {@link String} id of the product.
     * @return {@link Product} object removed.
     */
    @Override
    public Product deleteProduct(String productId) throws ProductNotFoundException {
        for (int i = 0; i < this.productList.size(); i++) {
            if (this.productList.get(i).getId().equals(productId)) {
                return this.productList.remove(i);
            }
        }

        throw new ProductNotFoundException();
    }

    /**
     * Returns the product list
     * @return Product list containing all products added into the program
     */
    public ArrayList<Product> getProductList() {
        return this.productList;
    } 

    /**
     * Adds a {@link User} to the user list.
     * @param user is the {@link User} to be added.
     */
    @Override
    public void addUser(User user) throws NonUniqueUsernameException {
        if (isUsernameNotUnique(user.getUsername())) {
            throw new NonUniqueUsernameException();
        }

        this.userList.add(user);
    }

    /**
     * Checks if a user has already registered using the provided username
     * @param username to be checked against existing users
     * @return boolean
     */
    private boolean isUsernameNotUnique(String username) {
        return this.userList.stream()
            .anyMatch(e -> e.getUsername().equals(username));
    }

    /**
     * Searches and returns a {@link User}
     * @param username of the User object
     * @return User object if found or null.
     */
    public User getUser(String username) throws UserNotFoundException {
        User user = this.userList.stream()
        .filter(e -> e.getUsername().equals(username))
        .findFirst()
        .orElse(null);

        if (user == null) throw new UserNotFoundException();

        return user;
    }

    /**
     * Returns the user list
     * @return User list containing all users added into the program
     */
    public ArrayList<User> getUserList() {
        userList.sort(new UsernameComparator());
        return this.userList;
    }

    /**
     * Creates an ASCII table of all products and information and displays it
     */
    @Override
    public void printProductList() {
        sortProductList(0);
        StringBuilder sb = new StringBuilder();
        Object[] headers = {
            "Product", "Type", "ID", "Price", "Count", "Size", "Color", "Brand", "WarrantyPeriod"
        };

        sb.append(String.format("%15.15s | ".repeat(9), headers) + "\n");
        sb.append("%15.15s | ".formatted("-".repeat(15)).repeat(9)).append("\n");
        sb.append(this.convertProductsToString(this.productList));

        System.out.println(sb);
    }

    /**
     * Sorts the product list by either product id or product name
     * @param sortBy Accepts int 0 or 1, where
     *  0 : sort by product id,
     *  1 : sort by product name
     */
    public void sortProductList(int sortBy) {
        switch (sortBy) {
            case 0 -> this.productList.sort(new comparators.ProductIdComparator());
            case 1 -> this.productList.sort(new comparators.ProductNameComparator());
            default -> {}
        }
    }

    /**
     * Support method, used in gathering row data for the product table,
     * displayed by {@link #printProductList()} 
     * @param list {@link ArrayList} of product objects.
     * @return Concatenated {@link StringBuilder} object with all information about each product.
     */
    public StringBuilder convertProductsToString(ArrayList<Product> list) {
        StringBuilder str = new StringBuilder();

        for (Product i : list) {
            str.append(String.format("%15.15s | ".repeat(3), i.getName(), i.getClass().getSimpleName(), i.getId()));
            str.append(String.format("$%14.2f | ", i.getPrice()));
            str.append(String.format(
                "%15.15s | ".repeat(5) + "\n",
                i.getCount(),
                i instanceof Clothing ? ((Clothing) i).getSize() : "-",
                i instanceof Clothing ? ((Clothing) i).getColor() : "-",
                i instanceof Electronic ? ((Electronic) i).getBrand() : "-",
                i instanceof Electronic ? ((Electronic) i).getWarrantyPeriod() : "-"
            ));
        }

        return str;
    }

    /**
     * Saves all users and product data into a JSON file.
     *
     */
    @Override
    public void saveToFile(String file) throws IOException {
        JSONArray jsonProdArr = this.getJsonArrayFromProductList(this.productList);
        JSONArray jsonUserArr = this.getJsonArrayFromUserList(this.userList);

        FileWriter fw = new FileWriter(file);
        JSONObject root = new JSONObject();

        root.put("products", jsonProdArr);
        root.put("users", jsonUserArr);
        fw.write("");
        fw.write(root.toString());
        fw.flush();
        fw.close();
    }

    /**
     * Support method, converts any User array into a JSONArray
     * @param list {@link ArrayList} of {@link User} objects.
     * @return {@link JSONArray} of {@link JSONObject}s.
     */
    public JSONArray getJsonArrayFromUserList(ArrayList<User> list) {
        JSONArray jsonArr = new JSONArray();

        list.forEach(e -> {
            JSONObject jo = new JSONObject();

            jo.put("username", e.getUsername());
            jo.put("password", e.getPassword());
            jo.put("access", e.getAccess().toString());

            if (e instanceof Client) {
                Client c = (Client) e;
                jo.put(
                    "purchaseHistory",
                    getJsonArrayFromProductList(c.getPurchaseHistory())
                );

                jo.put("shoppingCart",
                getJsonArrayFromProductList(c.getCart().getProductList())
                );
            }

            jsonArr.put(jo);
        });

        return jsonArr;
    }

    /**
     * Support method, converts any products array into a JSONArray
     * @param list {@link ArrayList} of {@link Product} objects.
     * @return {@link JSONArray} of {@link JSONObject}s.
     */
    public JSONArray getJsonArrayFromProductList(ArrayList<Product> list) {
        JSONArray jsArr = new JSONArray();

        list.forEach(e -> {
            JSONObject jo = new JSONObject();

            jo.put("id", e.getId());
            jo.put("name", e.getName());
            jo.put("price", e.getPrice());
            jo.put("count", e.getCount());
            
            if (e instanceof Clothing) {
                Clothing c = (Clothing) e;
                jo.put("type", "Clothing");
                jo.put("size", c.getSize().toString());
                jo.put("color", c.getColor());
            
            } else if (e instanceof Electronic) {
                Electronic el = (Electronic) e;
                jo.put("type", "Electronic");
                jo.put("brand", el.getBrand());
                jo.put("warranty", el.getWarrantyPeriod());
            }

            jsArr.put(jo);
        });

        return jsArr;
    }

    /**
     * Reads from JSON file and stores data in the program.
     */
    @Override
    public void readFromFile(String file) throws IOException, CorruptedFileDataException {
        StringBuilder sb = new StringBuilder();

        FileReader fr = new FileReader(file);

        while (true) {
            int seek = fr.read();
            if (seek == -1) break;
            sb.append((char) seek);
        }

        fr.close();

        try {
            JSONObject root = new JSONObject(sb.toString());
            JSONArray jsonProdArr = root.getJSONArray("products");
            JSONArray jsonUserArr = root.getJSONArray("users");

            this.productList = getProductListFromJsonArray(jsonProdArr);
            this.userList = getUserListFromJsonArray(jsonUserArr);

        } catch (JSONException e) {
            throw new CorruptedFileDataException();
        }
    }

    public ArrayList<Product> getProductListFromJsonArray(JSONArray ja) throws CorruptedFileDataException {
        ArrayList<Product> list = new ArrayList<>();

        for (int i = 0; i < ja.toList().size(); i++) {
            JSONObject jo = (JSONObject) ja.get(i);
            Product obj;

            if (jo.getString("type").equals("Clothing")) {
                obj = new Clothing(null, null);
            
            } else if (jo.getString("type").equals("Electronic")) {
                obj = new Electronic(null, 0);
            
            } else throw new CorruptedFileDataException();

            obj.setId(jo.getString("id"));
            obj.setName(jo.getString("name"));
            obj.setPrice(jo.getDouble("price"));
            obj.setCount(jo.getInt("count"));

            if (obj instanceof Clothing) {
                ((Clothing) obj).setSize(Size.toSize(jo.getString("size")));
                ((Clothing)obj).setColor(jo.getString("color"));
            
            } else {
                ((Electronic)obj).setBrand(jo.getString("brand"));
                ((Electronic)obj).setWarrantyPeriod(jo.getInt("warranty"));
            }

            list.add(obj);
        }

        return list;
    }

    public ArrayList<User> getUserListFromJsonArray(JSONArray jsonArr) throws CorruptedFileDataException {
        ArrayList<User> list = new ArrayList<>();

        for (int i = 0; i < jsonArr.toList().size(); i++) {
            JSONObject jo = (JSONObject) jsonArr.get(i);

            User obj;
            
            if (jo.getString("access").equals("Admin")) {
                obj = new Manager(null, null);
            } else if (jo.getString("access").equals("Client")) {
                obj = new Client(null, null);
            } else throw new CorruptedFileDataException();
            
            obj.setUsername(jo.getString("username"));
            obj.setPassword(jo.getString("password"));

            if (obj instanceof Client) {
                ((Client) obj).setPurchaseHistory(getProductListFromJsonArray(
                    jo.getJSONArray("purchaseHistory"))
                );

                ((Client) obj).setCart(new ShoppingCart(getProductListFromJsonArray(
                    jo.getJSONArray("shoppingCart")))
                );
            }

            list.add(obj);
        }

        return list;
    }

    @Override
    public void startGUI(User user) {
        GraphicalInterface gui = new GraphicalInterface(user, productList);
        gui.start();
    }

    public void destroyState() {
        this.userList = new ArrayList<>();
        this.productList = new ArrayList<>();
    }
}