package ui;

import exceptions.*;
import lib.*;
import org.json.JSONArray;
import org.json.JSONObject;

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
    public void addProduct(Product product) throws NonUniqueProductIdException {
        if (isProductIdAlreadyUsed(product.getId())) {
            throw new NonUniqueProductIdException();
        }

        this.productList.add(product);
    }

    /**
     * Checks if a product has been registered using the id provided
     * @param id to be checked against all existing products
     * @return boolean 
     */
    private boolean isProductIdAlreadyUsed(String id) {
        return this.productList.stream()
            .anyMatch(e -> e.getId().equals(id));
    }

    /**
     * Searches and returns a Product matching its product id
     * @param id product id
     * @return returns the {@link Product} if found, else returns null
     */
    public Product getProduct(String id) {
        return this.productList.stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElse(null);
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
        if (isUsernameAlreadyUsed(user.getUsername())) {
            throw new NonUniqueUsernameException();
        }

        this.userList.add(user);
    }

    /**
     * Checks if a user has already registered using the provided username
     * @param username to be checked against existing users
     * @return boolean
     */
    private boolean isUsernameAlreadyUsed(String username) {
        return this.userList.stream()
            .anyMatch(e -> e.getUsername().equals(username));
    }

    /**
     * Searches and returns a {@link User}
     * @param username of the User object
     * @return User object if found or null.
     */
    public User getUser(String username) {
        return this.userList.stream()
        .filter(e -> e.getUsername().equals(username))
        .findFirst()
        .orElse(null);
    }

    /**
     * Returns the user list
     * @return User list containing all users added into the program
     */
    public ArrayList<User> getUserList() {
        return this.userList;
    }



    /**
     * Creates an ASCII table of all products and information and displays it
     */
    @Override
    public void printProductList() {
        sortProductList(0);
        StringBuilder sb = new StringBuilder();

        sb.append(
            "Product::Type::ID::Price::Count::Size::Color::Brand::WarrantyPeriod\n"
            .replaceAll("::", "             ")
        );

        sb.append(
            "----------::-------::-----::--------::--------::-------::--------::--------::-----------------\n"
            .replaceAll("::", "          ")
        );

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
                str.append(String.format(
                    "%15.15s | %15.15s | %15.15s | %15.2f | %15.15s | %15.15s | %15.15s | %15.15s | %15.15s\n",
                    i.getName(),
                    i.getClass().getSimpleName(),
                    i.getId(),
                    i.getPrice(),
                    i.getCount(),
                    i.getClass() == Clothing.class ? ((Clothing) i).getSize() : "-",
                    i.getClass() == Clothing.class ? ((Clothing) i).getColor() : "-",
                    i.getClass() == Electronic.class ? ((Electronic) i).getBrand() : "-",
                    i.getClass() == Electronic.class ? ((Electronic) i).getWarrantyPeriod() : "-"
                )
            );
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

            if (e.getClass() == Client.class) {
                Client c = (Client) e;
                jo.put(
                    "purchaseHistory",
                    getJsonArrayFromProductList(c.getPurchaseHistory())
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
            
            if (e.getClass() == Clothing.class) {
                Clothing c = (Clothing) e;
                jo.put("type", "Clothing");
                jo.put("size", c.getSize());
                jo.put("color", c.getColor());
            
            } else if (e.getClass() == Electronic.class) {
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
    public void readFromFile(String file) throws IOException {
        StringBuilder sb = new StringBuilder();

        FileReader fr = new FileReader(file);

        while (true) {
            int seek = fr.read();
            if (seek == -1) break;
            sb.append((char) seek);
        }

        fr.close();

        JSONObject root = new JSONObject(sb.toString());
        JSONArray jsonProdArr = root.getJSONArray("products");
        JSONArray jsonUserArr = root.getJSONArray("users");

        this.productList = getProductListFromJsonArray(jsonProdArr);
        this.userList = getUserListFromJsonArray(jsonUserArr);
    }


    public ArrayList<Product> getProductListFromJsonArray(JSONArray ja) {
        ArrayList<Product> list = new ArrayList<>();

        ja.forEach(e -> {
            JSONObject jo = (JSONObject) e;

            Product obj = jo.getString("type").equals("Clothing")
                ? new Clothing(null, null) 
                : new Electronic(null, 0);

            obj.setId(jo.getString("id"));
            obj.setName(jo.getString("name"));
            obj.setPrice(jo.getDouble("price"));
            obj.setCount(jo.getInt("count"));

            if (obj.getClass().getSimpleName().equals("Clothing")) {
                ((Clothing) obj).setSize(jo.getString("size"));
                ((Clothing)obj).setColor(jo.getString("color"));
            
            } else {
                ((Electronic)obj).setBrand(jo.getString("brand"));
                ((Electronic)obj).setWarrantyPeriod(jo.getInt("warranty"));
            }

            list.add(obj);
        });

        return list;
    }

    public ArrayList<User> getUserListFromJsonArray(JSONArray jsonArr) {
        ArrayList<User> list = new ArrayList<>();

        jsonArr.forEach(e -> {
            JSONObject jo = (JSONObject) e;

            User obj = jo.getString("access").equals("Admin")
                ? new Manager(null, null)
                : new Client(null, null);
            
            obj.setUsername(jo.getString("username"));
            obj.setPassword(jo.getString("password"));

            if (obj.getClass().getSimpleName().equals("Client")) {
                ((Client) obj).setPurchaseHistory(getProductListFromJsonArray(
                    jo.getJSONArray("purchaseHistory"))
                );
            }

            list.add(obj);
        });

        return list;
    }

    @Override
    public void startGUI() {
        // TODO
    }
}
