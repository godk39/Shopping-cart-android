package hanu.a2_1901040029.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1901040029.R;
import hanu.a2_1901040029.adapters.CartAdapter;
import hanu.a2_1901040029.db.DBHelper;
import hanu.a2_1901040029.models.CartProduct;
import hanu.a2_1901040029.models.Product;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartProductList;
    List<CartProduct> cartProducts;
    DBHelper db = new DBHelper(CartActivity.this);
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        cartProducts = db.getCartProducts();
        cartProductList = findViewById(R.id.cart_product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cartAdapter = new CartAdapter(CartActivity.this, cartProducts);
        cartProductList.setLayoutManager(linearLayoutManager);
        cartProductList.setAdapter(cartAdapter);
        TextView totalPrice = findViewById(R.id.total_price_cart);


        int total = 0;
        ArrayList<Integer> sumPriceList = db.returnSumPrices();
        for (Integer i : sumPriceList) {
            total += i;
        }

        totalPrice.setText(Integer.toString(total));




    }



    public void calculateTotal(ArrayList<Integer> returnSumPrices) {

    }
}


