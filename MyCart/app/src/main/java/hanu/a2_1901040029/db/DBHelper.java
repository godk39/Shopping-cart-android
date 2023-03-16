package hanu.a2_1901040029.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1901040029.models.CartProduct;
import hanu.a2_1901040029.models.Product;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mycart.db";
    private static final int DB_VERSION = 1;
    private static final String ID = "id";
    private static final String THUMBNAIL = "thumbnail";
    private static final String NAME = "name";
    private static final String UNIT_PRICE = "unitprice";
    private static final String PRODUCTS = "products";
    private static final String CART = "cart";
    private static final String QUANTITY = "quantity";
    private static final String SUM_PRICE = "sumprice";
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE "
            + PRODUCTS + " (" + ID + " INT PRIMARY KEY, " +
            THUMBNAIL + " TEXT NOT NULL, " +
            NAME + " TEXT NOT NULL, " +
            UNIT_PRICE + " INT NOT NULL)";
    private static final String CREATE_TABLE_CART = "CREATE TABLE "
            + CART + "(" + ID + " INT PRIMARY KEY, " +
            NAME + " TEXT NOT NULL, " +
            THUMBNAIL + " TEXT NOT NULL, " +
            UNIT_PRICE + " INT NOT NULL, " +
            "" + QUANTITY + " INT NOT NULL, " +
            "" + SUM_PRICE + " INT NOT NULL)";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table products

        db.execSQL(CREATE_TABLE_PRODUCTS);
        //create table cart
        db.execSQL(CREATE_TABLE_CART);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop tables - lost data
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + CART);
        //create again
        onCreate(db);
    }

    public Boolean insertProductData(int id, String thumbnail, String name, int unitPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("thumbnail", thumbnail);
        cv.put("name", name);
        cv.put("unitprice", unitPrice);
        long result = db.insert(PRODUCTS, null, cv);
        if (result == -1) {
            db.close();
            return false;
        } else {
            return true;
        }

    }

    public Boolean insertProductToCart(int id, String name, int unitPrice, int quantity, String thumbnail) {
        int sumPrice = unitPrice;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("thumbnail", thumbnail);
        cv.put("unitprice", unitPrice);
        cv.put("quantity", quantity);
        cv.put("sumprice", sumPrice);
        long result = db.insert(CART, null, cv);
        if (result == -1) {
            db.close();
            return false;
        } else {
            return true;
        }
    }

    public void deleteProductFromCart(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CART, "name=?", new String[]{name});
        db.close();
    }

    public Boolean checkIfProductExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM cart WHERE name LIKE " + "'" + name + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public void increaseQuantity(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE cart SET quantity = quantity + 1 WHERE name LIKE " + "'" + name + "'";
        String queryString2 = "UPDATE cart SET sumprice = quantity * unitprice WHERE name LIKE " + "'" + name + "'";
        db.execSQL(queryString);
        db.execSQL(queryString2);
        db.close();
    }

    public void decreaseQuantity(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE cart SET quantity = quantity - 1 WHERE name LIKE " + "'" + name + "'";
        String queryString2 = "UPDATE cart SET sumprice = quantity * unitprice WHERE name LIKE " + "'" + name + "'";
        db.execSQL(queryString);
        db.execSQL(queryString2);
        db.close();
    }

    public List<CartProduct> getCartProducts() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<CartProduct> cartProductList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CART;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                int productId = cursor.getInt(0);
                String name = cursor.getString(1);
                String thumbnail = cursor.getString(2);
                int unitPrice = cursor.getInt(3);
                int quantity = cursor.getInt(4);
                int sumPrice = cursor.getInt(5);

                // Product newProduct = new Product(productId, thumbnail, name, unitPrice);
                CartProduct newCartProduct = new CartProduct(productId, name, thumbnail, unitPrice, quantity, sumPrice);
                cartProductList.add(newCartProduct);
            } while (cursor.moveToNext());

        } else {
            Log.e("ERROR", "FAILED!");
        }
        cursor.close();
        db.close();
        return cartProductList;
    }

    public ArrayList<Integer> returnSumPrices() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> sumPriceList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CART;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {

                int sumPrice = cursor.getInt(5);

                // Product newProduct = new Product(productId, thumbnail, name, unitPrice);
                sumPriceList.add(sumPrice);
            } while (cursor.moveToNext());

        } else {
            Log.e("ERROR", "FAILED!");
        }
        cursor.close();
        db.close();
        return sumPriceList;
    }

}
