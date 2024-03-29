package com.shmakov.techfate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.shmakov.techfate.entities.Card;
import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.entities.User;
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db"; // название бд

    private static final int SCHEMA = 1; // версия базы данных

    public static final String USER_TABLE = "`User`"; // таблица пользователей

    public static final String USER_CART_TABLE = "`UserCart`"; // таблица корзин

    public static final String PRODUCTS_IN_USER_CART_TABLE = "`ProductsInUserCart`"; // таблица продуктов в корзине

    public static final String PRODUCTS_IN_ORDER_TABLE = "`ProductsInOrder`"; // таблица продуктов в заказе

    public static final String ORDER_CART_TABLE = "`OrderCart`"; // таблица корзины в заказе

    public static final String ORDER_TABLE = "'Order'"; // таблица заказов

    public static final String ADDRESSES_TABLE = "`Addresses`"; // таблица адресов

    public static final String CARDS_TABLE = "`Cards`"; // таблица банковских карт

    private static String DB_PATH = "/data/data/com.shmakov.techfate/databases/";
    Context context;

    static SQLiteDatabase sqLiteDatabase;

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, USER_TABLE, null, SCHEMA);
        this.context = context;
    }

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

    public void changePassword(String Email, String Password) {
        ContentValues values = new ContentValues();
        values.put("Password", Password);
        String whereClause = "Email=?";
        String[] whereArgs = {Email};
        sqLiteDatabase.update(USER_TABLE, values, whereClause, whereArgs);
    }

    public void saveAddress(String address, int user_id) {
        ContentValues values = new ContentValues();
        values.put("Address", address);
        values.put("UserId", user_id);
        sqLiteDatabase.insert(ADDRESSES_TABLE, null, values);
    }

    public void saveCard(Card card, int user_id) {
        ContentValues values = new ContentValues();
        values.put("CardNum", card.getCardNum());
        values.put("CardHolder", card.getCardHolder());
        values.put("CardDate", card.getCardDate());
        values.put("CardType", card.getCardType());
        values.put("CVC", card.getCVC());
        values.put("UserId", user_id);
        sqLiteDatabase.insert(CARDS_TABLE, null, values);
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }

    public User getLoggedUser(){
        User user = null;
        Cursor userCursor = sqLiteDatabase.query(USER_TABLE, null, "IsLogged = ?", new String[]{"1"}, null, null, null);
        while (userCursor.moveToNext()) {
            int user_id = userCursor.getInt(0);
            ArrayList<ProductInCart> products = new ArrayList<>();
            Cursor cartCursor = sqLiteDatabase.query(USER_CART_TABLE, null, "UserId = ?", new String[] {String.valueOf(user_id)}, null, null, null);
            Cursor productsInUserCartCursor = sqLiteDatabase.query(PRODUCTS_IN_USER_CART_TABLE, null, "UserId = ?", new String[]{String.valueOf(user_id)}, null, null, null);
            while (productsInUserCartCursor.moveToNext()) {
                ArrayList<Product> p = Category.getAllProducts();
                Product product = p.get(productsInUserCartCursor.getInt(0) - 1);
                products.add(new ProductInCart(product, productsInUserCartCursor.getString(1),
                        productsInUserCartCursor.getString(2)));
            }
            cartCursor.moveToNext();
            int total_cost = cartCursor.getInt(0);
            float rate = cartCursor.getFloat(1);
            productsInUserCartCursor.close();
            Cart User_Cart = new Cart(products, total_cost, rate);
            cartCursor.close();
            ArrayList<String> addresses = new ArrayList<>();
            Cursor AddressesCursor = sqLiteDatabase.query(ADDRESSES_TABLE, null, "UserId = ?", new String[]{String.valueOf(user_id)}, null, null, null);
            while (AddressesCursor.moveToNext()) {
                addresses.add(AddressesCursor.getString(0));
            }
            AddressesCursor.close();

            ArrayList<Card> cards = new ArrayList<>();
            Cursor CardCursor = sqLiteDatabase.query(CARDS_TABLE, null, "UserId = ?", new String[]{String.valueOf(user_id)}, null, null, null);
            while (CardCursor.moveToNext()) {
                Card card = new Card(CardCursor.getString(1), CardCursor.getString(0),
                        CardCursor.getString(2), CardCursor.getString(3),
                        CardCursor.getString(4));
                cards.add(card);
            }
            CardCursor.close();

            boolean a = sqLiteDatabase.isOpen();
            ArrayList<Order> orders = new ArrayList<>();
            Cursor orderCursor = null;
            orderCursor = sqLiteDatabase.query("`Order`", null, "UserId = ?", new String[]{String.valueOf(user_id)}, null, null, null);
            while (orderCursor.moveToNext()) {
                int order_id = orderCursor.getInt(0);
                ArrayList<ProductInCart> products_in_order = new ArrayList<>();
                Cursor ProductsOrdersCursor = sqLiteDatabase.query(PRODUCTS_IN_ORDER_TABLE, null, "OrderId = ?", new String[]{String.valueOf(order_id)}, null, null, null);
                while (ProductsOrdersCursor.moveToNext()) {
                    Product product = Category.getAllProducts().get(ProductsOrdersCursor.getInt(0) - 1);
                    ProductInCart temp = new ProductInCart(product, ProductsOrdersCursor.getString(1),
                            ProductsOrdersCursor.getString(2));
                    products_in_order.add(temp);
                }
                ProductsOrdersCursor.close();

                Cart cart = new Cart(products_in_order, orderCursor.getInt(2), orderCursor.getFloat(6));

                Order order = new Order(
                        orderCursor.getString(1),
                        cart,
                        orderCursor.getString(3),
                        orderCursor.getString(4),
                        orderCursor.getString(8),
                        orderCursor.getInt(2),
                        orderCursor.getInt(5),
                        orderCursor.getString(7),
                        orderCursor.getFloat(6)
                );
                order.setStatus(orderCursor.getString(10));
                orders.add(order);
            }
            orderCursor.close();

            user = new User(
                    user_id,
                    context.getResources().getIdentifier(userCursor.getString(6), "drawable", context.getPackageName()),
                    userCursor.getString(1),
                    userCursor.getString(2),
                    userCursor.getString(3),
                    userCursor.getString(5),
                    User_Cart,
                    orders,
                    addresses,
                    cards
            );
        }
        userCursor.close();
        return user;
    }

    public void saveOrder(Order order, int user_id) {
        ContentValues values = new ContentValues();
        values.put("Name", order.getName());
        values.put("Cost", order.getFinal_cost());
        values.put("Address", order.getAddress());
        values.put("DeliveryMethod", order.getDeliveryMethod());
        values.put("DeliveryCost", order.getDelivery_cost());
        values.put("PromoRate", order.getPromocodeRate());
        values.put("PromoName", order.getPromocodeName());
        values.put("PaymentMethod", order.getPaymentMethod());
        values.put("UserId", user_id);
        values.put("Status", order.getStatus());
        long id = sqLiteDatabase.insert(ORDER_TABLE, null, values);
        if (id == -1) {
            Toast.makeText(context, "Не удалось совершить заказ", Toast.LENGTH_LONG).show();
            return;
        }
        String whereClause = "UserId = ?";
        String[] whereArgs = { String.valueOf(user_id) };
        sqLiteDatabase.delete(PRODUCTS_IN_USER_CART_TABLE, whereClause, whereArgs);
        values = new ContentValues();
        values.put("TotalCost", 0);
        sqLiteDatabase.update(USER_CART_TABLE, values, whereClause, whereArgs);
        ArrayList<ProductInCart> products = order.getCart().getProducts();
        for (ProductInCart product : products) {
            values = new ContentValues();
            values.put("ProductId", product.product.getId());
            values.put("SelectedColor", product.getSelected_color());
            values.put("SelectedConfiguration", product.getSelected_configuration());
            values.put("OrderId", id);
            sqLiteDatabase.insert(PRODUCTS_IN_ORDER_TABLE, null, values);
        }
    }

    public ArrayList<Order> getAllOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        Cursor orderCursor = sqLiteDatabase.query("`Order`", null, null, null, null, null, null);
        while (orderCursor.moveToNext()) {
            int order_id = orderCursor.getInt(0);
            ArrayList<ProductInCart> products_in_order = new ArrayList<>();
            Cursor ProductsOrdersCursor = sqLiteDatabase.query(PRODUCTS_IN_ORDER_TABLE, null, "OrderId = ?", new String[]{String.valueOf(order_id)}, null, null, null);
            while (ProductsOrdersCursor.moveToNext()) {
                Product product = Category.getAllProducts().get(ProductsOrdersCursor.getInt(0) - 1);
                ProductInCart temp = new ProductInCart(product, ProductsOrdersCursor.getString(1),
                        ProductsOrdersCursor.getString(2));
                products_in_order.add(temp);
            }
            ProductsOrdersCursor.close();

            Cart cart = new Cart(products_in_order, orderCursor.getInt(2), orderCursor.getFloat(6));

            Order order = new Order(
                    orderCursor.getInt(0),
                    orderCursor.getString(1),
                    cart,
                    orderCursor.getString(3),
                    orderCursor.getString(4),
                    orderCursor.getString(8),
                    orderCursor.getInt(2),
                    orderCursor.getInt(5),
                    orderCursor.getString(7),
                    orderCursor.getFloat(6)
            );
            order.setStatus(orderCursor.getString(10));
            orders.add(order);
        }
        orderCursor.close();
        return orders;
    }

    public void updateOrderInfo(Order order, String status) {
        ContentValues values = new ContentValues();
        values.put("Status", status);
        sqLiteDatabase.update(ORDER_TABLE, values, "OrderId=?", new String[]{String.valueOf(order.getId())});
    }

    public void updateProfileInfo(User user) {
        String selection = "id = ? ";
        String[] args = {String.valueOf(user.getId())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", user.getName());
        contentValues.put("Email", user.getEmailAdress());
        contentValues.put("Password", user.getPassword());
        contentValues.put("Img", user.getImg());
        sqLiteDatabase.update(USER_TABLE, contentValues, selection, args);
    }

    public boolean login(String email, String password){
        boolean exist = false;
        String selection = "Email = ? " + " AND " + "Password = ?";
        try {
            Cursor userCursor = sqLiteDatabase.query(USER_TABLE, null, selection, new String[]{email, password}, null, null, null);

        }
        catch (Exception exception) {
            Exception exception1 = exception;
        }
        Cursor userCursor = sqLiteDatabase.query(USER_TABLE, null, selection, new String[]{email, password}, null, null, null);
        while (userCursor.moveToNext()) {
            exist = true;
            int userId = userCursor.getInt(0);
            ContentValues values = new ContentValues();
            values.put("isLogged", 1);

            String whereClause = "id=?";
            String[] whereArgs = {String.valueOf(userId)};

            int rowsAffected = sqLiteDatabase.update(USER_TABLE, values, whereClause, whereArgs);

        }
        return exist;
    }

    public void logout(int id){
        String selection = "id = ?";
        Cursor userCursor = sqLiteDatabase.query(USER_TABLE, null, selection, new String[]{String.valueOf(id)}, null, null, null);
        while (userCursor.moveToNext()) {
            int userId = userCursor.getInt(0);
            ContentValues values = new ContentValues();
            values.put("isLogged", 0);
            String whereClause = "id=?";
            String[] whereArgs = {String.valueOf(userId)};
            int rowsAffected = sqLiteDatabase.update(USER_TABLE, values, whereClause, whereArgs);
        }
    }

    public boolean checkExistEmail(String Email){
        String selection = "Email = ?";
        boolean isExist = false;
        Cursor userCursor = sqLiteDatabase.query(USER_TABLE, null, selection, new String[]{Email}, null, null, null);
        while (userCursor.moveToNext()) {
            isExist = true;
        }
        return isExist;
    }

    public void addNewUser(String Name, String Email, String Password) {
        ContentValues values = new ContentValues();
        values.put("Name", Name);
        values.put("Email", Email);
        values.put("Password", Password);
        values.put("isLogged", 0);
        values.put("Img", "ava1");
        long new_row = sqLiteDatabase.insert(USER_TABLE, null, values);
        values = new ContentValues();
        values.put("TotalCost", 0);
        values.put("Rate", 1.0);
        values.put("UserId", (int)new_row);
        sqLiteDatabase.insert(USER_CART_TABLE, null, values);
    }

    public void addProduct(User user, ProductInCart product) {
        ContentValues values = new ContentValues();
        values.put("ProductId", product.getProduct().getId());
        values.put("SelectedColor", product.getSelected_color());
        values.put("SelectedConfiguration", product.getSelected_configuration());
        values.put("UserId", user.getId());
        sqLiteDatabase.insert(PRODUCTS_IN_USER_CART_TABLE, null, values);
        values = new ContentValues();
        values.put("TotalCost", user.getCart().getTotal_cost());
        String whereClause = "UserId=?";
        String[] whereArgs = {String.valueOf(user.getId())};
        sqLiteDatabase.update(USER_CART_TABLE, values, whereClause, whereArgs);
    }

    public void deleteProduct(User user, ProductInCart product) {
        String selection =
                "ProductId = ? AND SelectedColor = ? AND SelectedConfiguration = ? AND UserId = ?";
        String[] selectionArgs = {
                String.valueOf(product.getProduct().getId()),
                product.getSelected_color(),
                product.getSelected_configuration(),
                String.valueOf(user.getId())
        };
        sqLiteDatabase.delete(PRODUCTS_IN_USER_CART_TABLE, selection, selectionArgs);

        ContentValues values = new ContentValues();
        values.put("TotalCost", user.getCart().getTotal_cost());
        String whereClause = "UserId=?";
        String[] whereArgs = {String.valueOf(user.getId())};
        sqLiteDatabase.update(USER_CART_TABLE, values, whereClause, whereArgs);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
