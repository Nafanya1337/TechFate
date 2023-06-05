package com.shmakov.techfate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.shmakov.techfate.entities.Review;
import com.shmakov.techfate.entities.inner.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "products"; // название таблицы в бд
    public static final String TABLE_COLORS = "products_colors";
    public static final String TABLE_CONFIGURATIONS = "products_configurations";
    public static final String TABLE_IMAGES = "products_images";
    public static final String TABLE_REVIEWS = "products_reviews";

    public static final String IMAGES_TABLE = "products_images"; // название таблицы в бд

    public static final String IMAGES_TABLE_COLUMN_IMAGE = "products_images_image"; // название таблицы в бд

    public static final String IMAGES_TABLE_COLUMN_PARENT_ID = "products_images_image"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_MARK = "mark";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_IMG = "img";

    public ProductDatabaseHelper(@Nullable Context context) {
        super(context, TABLE, null, SCHEMA);
        this.context = context;
    }

    private static String DB_PATH = "/data/data/com.shmakov.techfate/databases/";

    public Context context;
    static SQLiteDatabase sqLiteDatabase;

    public void createDataBase() throws IOException {
        //check if the database exists
        boolean databaseExist = checkDataBase();

        if (databaseExist) {
            // Do Nothing.
        } else {
            this.getWritableDatabase();
            copyDataBase();
        }// end if else dbExist
    } // end createDataBase().

    public boolean checkDataBase() {
        String path = DB_PATH + DATABASE_NAME;
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }

    public ArrayList<Review> getReviews(int id){
        ArrayList<Review> reviews = new ArrayList<>();
        Cursor cursorReviews = sqLiteDatabase.query(TABLE_REVIEWS, null, "REVIEWS_TABLE_COLUMN_PARENT_ID = ?",
                new String[] {String.valueOf(id)}, null, null, null);
        while (cursorReviews.moveToNext()) {
            Review review = new Review(cursorReviews.getString(0), cursorReviews.getString(1),
                    cursorReviews.getString(3), cursorReviews.getFloat(2));
            reviews.add(review);
        }
        return reviews;
    }

    public void getAllProducts(){
        Product product = null;
        Cursor cursor = sqLiteDatabase.query(ProductDatabaseHelper.TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ArrayList<String> colors = new ArrayList<>();
            ArrayList<Integer> amount = new ArrayList<>();
            ArrayList<String> configurations = new ArrayList<>();
            ArrayList<String> images = new ArrayList<>();
            ArrayList<Review> reviews = new ArrayList<>();
            int id = cursor.getInt(0);
            String img_name = cursor.getString(5);
            product = new Product(
                    context,
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    context.getResources().getIdentifier(img_name, "drawable", context.getPackageName()),
                    new int[0],
                    new String[0],
                    new int[]{2}
            );
            Cursor cursorColors = sqLiteDatabase.query(TABLE_COLORS, null, "COLORS_TABLE_COLUMN_PARENT_ID = ?",
                    new String[] {String.valueOf(id)}, null, null, null);
            while (cursorColors.moveToNext()) {
                colors.add(cursorColors.getString(0));
                amount.add(cursorColors.getInt(1));
            }
            cursorColors.close();
            product.setColors(colors.toArray(new String[0]));
            product.setAmount(amount.stream().mapToInt(i -> i).toArray());
            Cursor cursorConfigurations = sqLiteDatabase.query(TABLE_CONFIGURATIONS, null, "CONFIGURATIONS_TABLE_COLUMN_PARENT_ID = ?",
                    new String[] {String.valueOf(id)}, null, null, null);
            while (cursorConfigurations.moveToNext()) {
                configurations.add(cursorConfigurations.getString(0));
            }
            cursorConfigurations.close();
            product.setConfigurations(configurations.toArray(new String[0]));
            Cursor cursorImages = sqLiteDatabase.query(TABLE_IMAGES, null, "IMAGES_TABLE_COLUMN_PARENT_ID = ?",
                    new String[] {String.valueOf(id)}, null, null, null);
            while (cursorImages.moveToNext()) {
                images.add(cursorImages.getString(0));
            }
            cursorImages.close();
            product.setImages(images.toArray(new String[0]));
            Cursor cursorReviews = sqLiteDatabase.query(TABLE_REVIEWS, null, "REVIEWS_TABLE_COLUMN_PARENT_ID = ?",
                    new String[] {String.valueOf(id)}, null, null, null);
            while (cursorReviews.moveToNext()) {
                Review review = new Review(cursorReviews.getString(0), cursorReviews.getString(1),
                        cursorReviews.getString(3), cursorReviews.getFloat(2));
                reviews.add(review);
            }
            product.setReviews(reviews);
        }
        if (product == null) return;
        cursor.close();
    }

    public void addReview(String name, String text, Float rating, String date, int productId) {
        ContentValues values = new ContentValues();
        values.put("REVIEWS_TABLE_COLUMN_AUTHOR", name);
        values.put("REVIEWS_TABLE_COLUMN_TEXT", text);
        values.put("REVIEWS_TABLE_COLUMN_RATING", rating);
        values.put("REVIEWS_TABLE_COLUMN_DATE", date);
        values.put("REVIEWS_TABLE_COLUMN_PARENT_ID", productId);
        sqLiteDatabase.insert(TABLE_REVIEWS, null, values);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
