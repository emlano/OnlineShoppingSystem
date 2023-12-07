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

    @Override
    public void addProduct(Product product) {
        productList.add(product);
    }

    @Override
    public void addUser(User user) {
        userList.add(user);
    }

    @Override
    public Product deleteProduct(String productId) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(productId)) {
                return productList.remove(i);
            }
        }
        return null;
    }

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

        System.out.println(sb.toString());
    }

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

    @Override
    public void readFromFile() {
        // TODO
    }

    @Override
    public void startGUI() {
        // TODO
    }

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

    private JSONArray getProductListJsonArray(ArrayList<Product> list) {
        JSONArray jsArr = new JSONArray();

        list.stream().forEach(e -> {
            JSONObject jo = new JSONObject();

            jo.put("id", e.getId());
            jo.put("name", e.getName());
            jo.put("price", e.getPrice());
            jo.put("count", e.getCount());
            
            if (e.getClass() == Clothing.class) {
                Clothing c = (Clothing) e;
                jo.put("size", c.getSize());
                jo.put("color", c.getColor());
            
            } else if (e.getClass() == Electronic.class) {
                Electronic el = (Electronic) e;
                jo.put("brand", el.getBrand());
                jo.put("warranty", el.getWarrantyPeriod());
            }

            jsArr.put(jo);
        });

        return jsArr;
    }

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
}
