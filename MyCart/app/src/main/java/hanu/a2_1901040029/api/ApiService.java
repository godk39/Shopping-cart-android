package hanu.a2_1901040029.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import hanu.a2_1901040029.models.Product;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    //API_LINK = " https://mpr-cart-api.herokuapp.com/products";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://mpr-cart-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory
                    .create(gson))
            .build()
            .create(ApiService.class);

    @GET("products")
    Call<List<Product>> callProducts();
}
