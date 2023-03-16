package hanu.a2_1901040029.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import hanu.a2_1901040029.R;
import hanu.a2_1901040029.adapters.ProductAdapter;
import hanu.a2_1901040029.api.ApiService;
import hanu.a2_1901040029.db.DBHelper;
import hanu.a2_1901040029.models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView displayProductsView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private ImageButton myCartBtn;
    private SearchView searchView;
    private DBHelper db = new DBHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayProductsView = findViewById(R.id.display_products_view);
        callApi();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        displayProductsView.setLayoutManager(gridLayoutManager);

        myCartBtn = findViewById(R.id.mycartbtn);
        myCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        searchView = (SearchView) searchItem.getActionView();
        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        List<Product> filteredList = new ArrayList<>();

        // running a for loop to compare elements.
        for (Product product : productList) {
            // checking if the entered string matched with any item of our recycler view.
            if (product.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(product);
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            productAdapter.filterList(filteredList);
        }
    }

    private void callApi() {

        Call<List<Product>> call = ApiService.apiService.callProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                productList = response.body();
                for (Product product : productList) {
                    db.insertProductData(product.getId(), product.getThumbnail(), product.getName(), product.getUnitPrice());
                }

                productAdapter = new ProductAdapter(MainActivity.this, productList);
                displayProductsView.setAdapter(productAdapter);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call API failed", Toast.LENGTH_LONG).show();
            }
        });

    }


}