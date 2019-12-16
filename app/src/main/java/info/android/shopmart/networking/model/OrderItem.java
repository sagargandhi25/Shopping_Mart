package info.android.shopmart.networking.model;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("product_id")
    public long productId;
    public int quantity;
    public Product product;
}
