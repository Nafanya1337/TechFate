package com.shmakov.techfate;



import static com.shmakov.techfate.ItemCartActivity.PRODUCT_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shmakov.techfate.adapters.CartAdapter;
import com.shmakov.techfate.adapters.CategoryAdapter;
import com.shmakov.techfate.adapters.ProductAdapter;
import com.shmakov.techfate.entities.Review;
import com.shmakov.techfate.entities.User;
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.cart.CartFragment;
import com.shmakov.techfate.fragments.home.HomeFragment;
import com.shmakov.techfate.mytools.ColorManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.openCategory, ProductAdapter.onClickProduct, CartAdapter.updateAmount {

    BottomNavigationView menu;
    NavController navController;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createProducts();
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        menu = findViewById(R.id.nav_menu);
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupWithNavController(menu, navController);
    }

    private void createProducts(){
        int[] imgs = {
                R.drawable.apple_airpods_2_pro_img1,
                R.drawable.apple_airpods_2_pro_img2,
                R.drawable.apple_airpods_2_pro_img3
        };

        String colors[] = {
                "white"
        };

        HashMap<String, int[]> specs_col = new HashMap<>();
        specs_col.put("6/128 Gb", new int[]{1, 3});
        specs_col.put("6/256 Gb", new int[]{0, 5});

        int[] imgs_iphone = {
            R.drawable.iphone13pro_1,
            R.drawable.iphone13pro_2,
            R.drawable.iphone13pro_3
        };

        Product headphones1 = new Product(Category.HEADPHONES_NAME_CATEGORY, "Apple", "AirPods Pro 2", 12000, R.drawable.headphones_2, imgs, colors, new int[]{1});
        Product smartphone = new Product(Category.SMARTPHONE_NAME_CATEGORY, "Apple", "iPhone 13 Pro Max", 120000, R.drawable.smartphones_img, imgs_iphone, new String[]{"white", "black"}, specs_col);


        headphones1.addReview(new Review(new User("Nikolay", "dwdwa", 915086),
                "Хорошие наушники!","23/04/23", 4.5f));

        headphones1.addReview(new Review(new User("Maria", "dwdwa", 915086),
                "Спасибо магазину за скидку! Забрала на следующий день и не могу нарадоваться!","06/04/23", 5.0f));

        headphones1.addReview(new Review(new User("Alex", "dwdwa", 915086),
                "АЧХ у наушников довольно неплох. Конечно для аудиофиллов вроде меня это как плевок в лицо, но для зала пойдет","10/04/23", 3.5f));

        headphones1.addReview(new Review(new User("Not192", "dwdwa", 915086),
                "Все устраивает. Посмотрим, что будет дальше","18/10/22", 5.0f));

        headphones1.addReview(new Review(new User("Тимофей", "dwdwa", 915086),
                "Не понимаю кто их берет... Через 3 дня сдал обратно. Звук - 0/5!","12/09/22", 2.5f));

        headphones1.addReview(new Review(new User("Анжела", "dwdwa", 915086),
                "Лучшие наушники которые я когда либо носила!","11/03/22", 5.0f));

        headphones1.addReview(new Review(new User("Даниил", "dwdwa", 915086),
                "Пользуюсь наушниками на протяжении 3 месяцев. В целом радуют. Держат заряд очень долго. Качество звука на высоте. Единственный недостаток - очень плохо сбалансированы низкие частоты. Любите басы - смотрите в сторону Soundcore или на крайний случай Skullcandy.","23/04/20", 4.5f));


        smartphone.addReview(new Review(new User("Nikolay", "dwdwa", 915086),
                "И так начнем по порядку.Продавец был сам Яндекс Маркет ,купила его 2 января вечером пришел 7января утром,не отслеживался товар,завис на точке обработка данных,поэтому сильно переживала что будет с задержкой,но нет пришел в срок !Сильно скачет цена я взяла за 116 тсч 512гб.Удобно что можно оформить кредит почти за 5 минут!По телефону,лично у меня держит заряд он только день ,активно пользуюсь телефоном,сутки или полутора он конечно у меня не выдержит точно!Взяла черный графит цвет нравится.Достаточно тяжелый и большой .Не удобно одной рукой что то делать ,но наверное это дело привычки\uD83E\uDD37\uD83C\uDFFB\u200D♀️Всю жизнь у меня были андроиды в последние 7 лет дак вообще ходила с Самсунгами s серии,в прошлом голу взяла Самсунг s 22,не скажу что аппарат огонь часто грелся ,маленькая память 128 гб ,а в последнее время так вообще начал тупить сильно ,телефону 7 месяцев\uD83E\uDD37\uD83C\uDFFB\u200D♀️Решила брать айфон в декабре ,по работе нужна камера хорошая ,ну и что тут сказать конечно айфон выигрывает любой Самсунг тут даже говорить не о чем ,а так как я занимаюсь сьемками ,уж понимаю о чем говорю\uD83D\uDC4CСамсунг даже на качестве 8к ультра hd не выдавал такой результат как айфон 1080 \uD83E\uDD37\uD83C\uDFFB\u200D♀️По приложениям и нфс,да очень не удобно что отсутствует платежная система ,но всегда можно найти выход,Сбербанк тоже можно установить !!! В офисах банка!Пришел тебе все сделали за бесплатно !Вроде бы все нужные МНЕ приложения я смогла найти и установить единственное приложение совкомбанка нельзя установить на айфон ни в каком виде ,только вкладку на рабочий стол можно добавить браузера все ,это не удобно конечно", "18/09/20", 4.5f));

        smartphone.addReview(new Review(new User("Данек", "dwdwa", 915086),
                "Разочарован",
                "18/09/20", 4.5f));

        smartphone.addReview(new Review(new User("Иван", "dwdwa", 915086),
                "Продавец Мегафон. Покупку отправили сразу после оплаты. Телефон новый, неактивированный, оригинальный. Все проверки прошёл)) Доставили даже раньше заявленного срока. Упаковка отличная, пломбы на месте.\n" +
                        "\n" +
                        "Телефон прекрасен. С андроида все данные перенеслись. Все нужнын мне приложения установила. Да, нет приложений банков. Это не очень удобно. Но можно пользоваться версией сайтов для мобильных устройств, настроить вход по код-паролю (это лучше, чем каждый раз ждать код на телефон).\n" +
                        "\n" +
                        "Камера отличная, настроек много. Огромное поле для творчества))",
                "18/09/20", 3.5f));

        smartphone.addReview(new Review(new User("ANV", "dwdwa", 915086),
                "Продавец Мегафон. Покупку отправили сразу после оплаты. Телефон новый, неактивированный, оригинальный. Все проверки прошёл)) Доставили даже раньше заявленного срока. Упаковка отличная, пломбы на месте.\n" +
                        "\n" +
                        "Телефон прекрасен. С андроида все данные перенеслись. Все нужнын мне приложения установила. Да, нет приложений банков. Это не очень удобно. Но можно пользоваться версией сайтов для мобильных устройств, настроить вход по код-паролю (это лучше, чем каждый раз ждать код на телефон).\n" +
                        "\n" +
                        "Камера отличная, настроек много. Огромное поле для творчества))",
                "18/09/20", 4.0f));

        smartphone.addReview(new Review(new User("Jorj", "dwdwa", 915086),
                "Продавец Мегафон. Покупку отправили сразу после оплаты. Телефон новый, неактивированный, оригинальный. Все проверки прошёл)) Доставили даже раньше заявленного срока. Упаковка отличная, пломбы на месте.\n" +
                        "\n" +
                        "Телефон прекрасен. С андроида все данные перенеслись. Все нужнын мне приложения установила. Да, нет приложений банков. Это не очень удобно. Но можно пользоваться версией сайтов для мобильных устройств, настроить вход по код-паролю (это лучше, чем каждый раз ждать код на телефон).\n" +
                        "\n" +
                        "Камера отличная, настроек много. Огромное поле для творчества))",
                "18/09/20", 4.5f));

        smartphone.addReview(new Review(new User("Minecraft", "dwdwa", 915086),
                "",
                "18/09/20", 5.0f));

        smartphone.addReview(new Review(new User("Minecraft", "dwdwa", 915086),
                "",
                "18/09/20", 5.0f));

        smartphone.addReview(new Review(new User("Minecraft", "dwdwa", 915086),
                "",
                "18/09/20", 5.0f));

        smartphone.addReview(new Review(new User("Minecraft", "dwdwa", 915086),
                "",
                "18/09/20", 5.0f));

        smartphone.addReview(new Review(new User("Minecraft", "dwdwa", 915086),
                "",
                "18/09/20", 5.0f));

        smartphone.addReview(new Review(new User("Minecraft", "dwdwa", 915086),
                "",
                "18/09/20", 5.0f));


        new Product(Category.HEADPHONES_NAME_CATEGORY, "Skullcandy", "Hesh2", 8000, R.drawable.headphones_1, new int[]{}, new String[]{"blue", "black"}, new int[]{1, 0});
        new Product(Category.HEADPHONES_NAME_CATEGORY, "Skullcandy", "Hesh3", 10000, R.drawable.headphones_1, new int[]{}, new String[]{"blue", "black"}, new int[]{0, 0});

        HashMap<String, int[]> specs_realme = new HashMap<>();
        specs_realme.put("6/128 Gb", new int[]{1, 3});
        specs_realme.put("8/128 Gb", new int[]{0, -1});
        Product realme = new Product(Category.SMARTPHONE_NAME_CATEGORY, "Realme", "9 Pro 5G", 18000, R.drawable.realme_9_pro, new int[]{}, new String[]{"blue", "black"}, specs_realme);
        realme.addReview(new Review(new User("Ivan", "dwdwa", 915086),
                "",
                "18/09/20", 5.0f));
        realme.addReview(new Review(new User("Fedor", "dwdwa", 915086),
                "",
                "18/09/20", 4.5f));


        HashMap<String, int[]> specs_xiomi = new HashMap<>();
        specs_xiomi.put("6/128 Gb", new int[]{0, 0, 0, 4, 0});
        specs_xiomi.put("8/128 Gb", new int[]{0, 0, 0, 0, 0});
        specs_xiomi.put("8/256 Gb", new int[]{0, 0, 0, 0 , 5});
        Product xiaomi = new Product(Category.SMARTPHONE_NAME_CATEGORY, "Xiaomi", "11 Lite 5G NE", 29900, R.drawable.xiomi_front, new int[]{}, new String[]{"blue", "black", "white", ColorManager.GRAY_COLOR, ColorManager.GREEN_COLOR}, specs_xiomi);
        xiaomi.addReview(new Review(new User("Ivan", "dwdwa", 915086),
                "",
                "18/09/20", 3.0f));
        xiaomi.addReview(new Review(new User("Fedor", "dwdwa", 915086),
                "",
                "18/09/20", 2.5f));


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void openCategory(String category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.CATEGORY_TAG, category);
        activityCategoryResultLauncher.launch(intent);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        menu = null;
        navController = null;
        fragmentManager = null;
    }

    @Override
    public void onClickProduct(View view, Product product) {
        Intent intent = new Intent(this, ItemCartActivity.class);
        product.addWatch();
        intent.putExtra(PRODUCT_TAG, product);
        activityProductResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activityProductResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            if (data != null) {
                products.add(data.getParcelableExtra(PRODUCT_TAG));
            }
        }
    });

    public static ArrayList<Product> products = new ArrayList<>();
    ActivityResultLauncher<Intent> activityCategoryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (data != null) {
                        products.addAll(data.getParcelableArrayListExtra(PRODUCT_TAG));
                    }
                }
            });

    @Override
    public void add(int cost) {

    }

    @Override
    public void remove(int cost) {

    }
}