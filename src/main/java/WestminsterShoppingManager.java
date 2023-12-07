import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private static ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();

    @Override
    public void start() {
        // TODO
    }

    /**
     * Adds a {@link Product} to the product list.
     * @param product is the {@link Product} to be added.
     */
    @Override
    public void addProduct(Product product) {
        productList.add(product);
    }

    /**
     * Adds a {@link User} to the user list.
     * @param user is the {@link User} to be added.
     */
    @Override
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * Deletes a {@link Product} from the product list.
     * @param productId {@link String} id of the product.
     * @return {@link Product} object removed.
     */
    @Override
    public Product deleteProduct(String productId) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(productId)) {
                return productList.remove(i);
            }
        }
        return null;
    }

    /**
     * Creates an ASCII table of all products and information and displays it
     */
    @Override
    public void printProductList() {
        productList.sort(new ProductIdComparator());
        StringBuilder sb = new StringBuilder();

        sb.append(
            "Product::Type::ID::Price::Count::Size::Color::Brand::WarrantyPeriod\n"
            .replaceAll("::", "             ")
        );

        sb.append(
            "----------::-------::-----::--------::--------::-------::--------::--------::-----------------\n"
            .replaceAll("::", "          ")
        );

        sb.append(this.getProductListRows(productList));

        System.out.println(sb);
    }

    /**
     * Support method, used in gathering row data for the product table,
     * displayed by {@link #printProductList()} 
     * @param productArr {@link ArrayList} of product objects.
     * @return Concatenated {@link StringBuilder} object with all information about each product.
     */
    private StringBuilder getProductListRows(ArrayList<Product> productArr) {
        StringBuilder str = new StringBuilder();

        for (Product i : productArr) {
                str.append(String.format(
                    "%15.15s | %15.15s | %15.15s | %15.2f | %15.15s | %15.15s | %15.15s | %15.15s | %15.15s\n",
                    i.getName(),
                    i.getClass().getName(),
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
    public void saveToFile() {
        JSONArray jsonProdArr = this.getProductListJsonArray(productList);
        JSONArray jsonUserArr = this.getUserListJsonArray(userList);
        
        try {
            FileWriter fw = new FileWriter("data.json");
            JSONObject root = new JSONObject();
            
            root.put("products", jsonProdArr);
            root.put("users", jsonUserArr);
            fw.write("");
            fw.write(root.toString());
            fw.flush();
            fw.close();

        } catch (IOException e) {
            System.out.println("Error! Could not open file to write! \n");
        }
    }

    /**
     * Support method, converts any User array into a JSONArray
     * @param list {@link ArrayList} of {@link User} objects.
     * @return {@link JSONArray} of {@link JSONObject}s.
     */
    private JSONArray getUserListJsonArray(ArrayList<User> list) {
        JSONArray jsonArr = new JSONArray();

        list.forEach(e -> {
            JSONObject jo = new JSONObject();

            jo.put("username", e.getUsername());
            jo.put("password", e.getPassword());
            jo.put("access", e.getAccess());

            if (e.getClass() == Client.class) {
                Client c = (Client) e;
                jo.put(
                    "purchaseHistory",
                    getProductListJsonArray(c.getPurchaseHistory())
                );
            }

            jsonArr.put(jo);
        });

        return jsonArr;
    }

    /**
     * Support method, converts any product array into a JSONArray
     * @param list {@link ArrayList} of {@link Product} objects.
     * @return {@link JSONArray} of {@link JSONObject}s.
     */
    private JSONArray getProductListJsonArray(ArrayList<Product> list) {
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
    public void readFromFile() {
        StringBuilder sb = new StringBuilder();

        try {
            FileReader fr = new FileReader("data.json");
            
            while (true) {
                int seek = fr.read();
                if (seek == -1) break;
                sb.append((char) seek);
            }

            fr.close();
        
        } catch (IOException e) {
            System.out.println("Error! Could not open file to read!");
        }

        JSONObject root = new JSONObject(sb.toString());
        JSONArray jsonProdArr = root.getJSONArray("products");
        JSONArray jsonUserArr = root.getJSONArray("users");

        productList = populateProductsList(jsonProdArr);
        userList = populateUserList(jsonUserArr);
        System.out.println(userList);
    }

    private ArrayList<Product> populateProductsList(JSONArray ja) {
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
            
            if (obj.getClass().getName().equals("Clothing")) {
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

    private ArrayList<User> populateUserList(JSONArray ja) {
        ArrayList<User> list = new ArrayList<>();

        ja.forEach(e -> {
            JSONObject jo = (JSONObject) e;

            User obj = jo.getString("access").equals("ADMIN")
                ? new Manager(null, null)
                : new Client(null, null);
            
            obj.setUsername(jo.getString("username"));
            obj.setPassword(jo.getString("password"));

            if (obj.getClass().getName().equals("Client")) {
                ((Client) obj).setPurchaseHistory(populateProductsList(
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
