import java.io.File;
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
    public String getProductListString() {
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

        for (Product i : productList) {
            sb.append(
                String.format(
                    "%15.15s | %15.15s | %15.15s | %15.2f | %15.15s",
                    i.getName(),
                    i.getClass().getName(),
                    i.getId(),
                    i.getPrice(),
                    i.getCount()
                )
            );

            switch (i.getClass().getName()) {
                case "Clothing" -> {
                    sb.append(
                        String.format(
                            " | %15.15s | %15.15s | %15.15s | %15.15s\n",
                            ((Clothing) i).getSize(),
                            ((Clothing) i).getColor(),
                            "-",
                            "-"
                        )
                    );
                }

                case "Electronic" -> {
                    sb.append(
                        String.format(
                            " | %15.15s | %15.15s | %15.15s | %15.15s\n",
                            "-",
                            "-",
                            ((Electronic) i).getBrand(),
                            ((Electronic) i).getWarrantyPeriod()
                        )
                    );
                }
            }
        }

        return sb.toString();
    }

    @Override
    public void saveToFile() {
        JSONArray jsonProdArr = this.getProductListJsonArray(productList);
        JSONArray jsonUserArr = new JSONArray();

        userList.stream().forEach(e -> {
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("username", e.getUsername());
            jsonUser.put("password", e.getPassword());
            jsonUser.put("access", e.getAccess());
            
            if (e.getClass().getName().equals("Client")) {
                Client j = (Client) e;
                jsonUser.put(
                    "productHistory",
                    this.getProductListJsonArray(
                        j.getPurchaseHistory()
                    )
                );
            }

            jsonUserArr.put(jsonUser);
        });
        
        try {
            FileWriter fw = new FileWriter(new File("data.json"));
            JSONObject root = new JSONObject();
            
            root.put("products", jsonProdArr);
            root.put("users", jsonUserArr);
            fw.write(root.toString());
            
            fw.flush();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
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


    private JSONArray getProductListJsonArray(ArrayList<Product> list) {
        JSONArray jsArr = new JSONArray();

        list.stream().forEach(e -> {
            JSONObject jsonProduct = new JSONObject();
            jsonProduct.put("id", e.getId());
            jsonProduct.put("name", e.getName());
            jsonProduct.put("price", e.getPrice());
            jsonProduct.put("count", e.getCount());

            switch (e.getClass().getName()) {
                case "Clothing" -> {
                    Clothing j = (Clothing) e;
                    jsonProduct.put("type", "Clothing");
                    jsonProduct.put("size", j.getSize());
                    jsonProduct.put("color", j.getColor());
                }

                case "Electronic" -> {
                    Electronic j = (Electronic) e;
                    jsonProduct.put("type", "Electronic");
                    jsonProduct.put("brand", j.getBrand());
                    jsonProduct.put("warranty", j.getWarrantyPeriod());
                }
            }

            jsArr.put(jsonProduct);
        });

        return jsArr;
    }

    
}
