package hanu.a2_1901040029.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1901040029.R;
import hanu.a2_1901040029.db.DBHelper;
import hanu.a2_1901040029.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    Context context;
    private List<Product> productList;


    public ProductAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        final ProductViewHolder holder = new ProductViewHolder(view);
        final DBHelper dbHelper = new DBHelper(context);

        holder.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Product product : productList) {
                    if (holder.name.getText().equals(product.getName())) {
                        if (!dbHelper.checkIfProductExists(product.getName())) {
                            dbHelper.insertProductToCart(product.getId(),
                                    product.getName(),
                                    product.getUnitPrice(),
                                    1, product.getThumbnail());
                        } else {
                            dbHelper.increaseQuantity(product.getName());
                        }
                    }
                }
            }
        });

        return new ProductViewHolder(view);
    }

    // method for filtering our recyclerview items.
    public void filterList(List<Product> filterList) {
        // below line is to add our filtered
        // list in our course array list.
        productList = filterList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int pos) {

        Product product = productList.get(pos);
        if (product == null) {
            return;
        }

        holder.name.setText(product.getName());
        holder.unitPrice.setText(Integer.toString(product.getUnitPrice()));
        Picasso.get().load(product.getThumbnail()).into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView name, unitPrice;
        private ImageButton orderButton;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            unitPrice = itemView.findViewById(R.id.unit_price);
            orderButton = itemView.findViewById(R.id.orderbutton);

        }


    }
}
