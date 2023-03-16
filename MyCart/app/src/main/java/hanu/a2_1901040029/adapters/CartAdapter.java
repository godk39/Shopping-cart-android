package hanu.a2_1901040029.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1901040029.R;
import hanu.a2_1901040029.db.DBHelper;
import hanu.a2_1901040029.models.CartProduct;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    private List<CartProduct> cartProducts;

    public CartAdapter(Context context, List<CartProduct> cartProducts) {
        this.context = context;
        this.cartProducts = cartProducts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_layout, parent, false);
        final CartViewHolder holder = new CartViewHolder(view);
        final DBHelper dbHelper = new DBHelper(context);


        holder.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (CartProduct cartProduct : cartProducts) {
                    if (holder.nameCart.getText().equals(cartProduct.getName())) {
                        dbHelper.increaseQuantity(cartProduct.getName());

                    }
                }
                update(dbHelper.getCartProducts());

            }

        });


        holder.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (CartProduct cartProduct : cartProducts) {
                    if (holder.nameCart.getText().equals(cartProduct.getName())) {
                        dbHelper.decreaseQuantity(cartProduct.getName());
                        if (cartProduct.getQuantity() == 1) {
                            dbHelper.deleteProductFromCart(cartProduct.getName());
                        }

                    }
                }

                update(dbHelper.getCartProducts());
            }
        });


        return new CartViewHolder(view);
    }

    public void update(List<CartProduct> cartProducts2) {
        cartProducts.clear();
        cartProducts.addAll(cartProducts2);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int pos) {
        CartProduct cartProduct = cartProducts.get(pos);
        if (cartProduct == null) {
            return;
        }
        holder.nameCart.setText(cartProduct.getName());
        holder.unitPriceCart.setText(Integer.toString(cartProduct.getUnitPrice()));
        Picasso.get().load(cartProduct.getThumbnail()).into(holder.thumbnailCart);
        holder.quantityCart.setText(Integer.toString(cartProduct.getQuantity()));
        holder.sumPriceCart.setText(Integer.toString(cartProduct.getSumPrice()));
    }

    @Override
    public int getItemCount() {
        if (cartProducts != null) {
            return cartProducts.size();
        }
        return 0;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailCart;
        private TextView nameCart, unitPriceCart, quantityCart, sumPriceCart;
        private ImageButton increaseButton, decreaseButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailCart = itemView.findViewById(R.id.thumbnail_cart);
            nameCart = itemView.findViewById(R.id.name_cart);
            unitPriceCart = itemView.findViewById(R.id.unit_price_cart);
            quantityCart = itemView.findViewById(R.id.quantity_cart);
            sumPriceCart = itemView.findViewById(R.id.sum_price_cart);
            increaseButton = itemView.findViewById(R.id.increase_button);
            decreaseButton = itemView.findViewById(R.id.decrease_button);

        }
    }
}
