package com.shmakov.techfate.fragments.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.CategoryAdapter;
import com.shmakov.techfate.entities.Review;
import com.shmakov.techfate.entities.User;
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Headphones;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.entities.inner.SmartPhone;
import com.shmakov.techfate.fragments.globals.ItemsFragment;
import com.shmakov.techfate.mytools.ColorManager;

import java.util.HashMap;


public class HomeFragment extends Fragment {

    private CategoryAdapter categoryAdapter;
    private FrameLayout popular_items_container;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getParentFragmentManager();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView categories_list = view.findViewById(R.id.categories_list);

        Category.init();

        categoryAdapter = new CategoryAdapter(getContext(), Category.getCategoriesNamesAsArrayList());

        categories_list.setAdapter(categoryAdapter);

        popular_items_container = view.findViewById(R.id.popular_items_container);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ItemsFragment itemsFragment = new ItemsFragment(ItemsFragment.MAKE_POPULAR_ITEMS);
        ft.replace(popular_items_container.getId(), itemsFragment);
        ft.commit();
        makeProducts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentManager = null;
        categoryAdapter = null;
        popular_items_container = null;
    }

    public void makeProducts() {
        int[] imgs = {
            R.drawable.apple_airpods_2_pro_img1,
            R.drawable.apple_airpods_2_pro_img2,
            R.drawable.apple_airpods_2_pro_img3
        };

        String colors[] = {
            "white"
        };

        HashMap<String, int[]> specs_col = new HashMap<>();
        specs_col.put("8/128 Gb", new int[]{1, 3});
        specs_col.put("12/128 Gb", new int[]{0, 5});

        Product headphones1 = new Product(Category.HEADPHONES_NAME_CATEGORY, "Apple", "AirPods Pro 2", 20000, R.drawable.apple_airpods_2_pro_img1, imgs, colors, new int[]{1});
        Product smartphone = new Product(Category.SMARTPHONE_NAME_CATEGORY, "Apple", "iPhone 13 Pro Max", 120000, R.drawable.smartphones_img, new int[]{}, new String[]{"white", "black"}, specs_col);

        smartphone.addReview(new Review(new User("Nikolay", "dwdwa", 915086),
                "И так начнем по порядку.Продавец был сам Яндекс Маркет ,купила его 2 января вечером пришел 7января утром,не отслеживался товар,завис на точке обработка данных,поэтому сильно переживала что будет с задержкой,но нет пришел в срок !Сильно скачет цена я взяла за 116 тсч 512гб.Удобно что можно оформить кредит почти за 5 минут!По телефону,лично у меня держит заряд он только день ,активно пользуюсь телефоном,сутки или полутора он конечно у меня не выдержит точно!Взяла черный графит цвет нравится.Достаточно тяжелый и большой .Не удобно одной рукой что то делать ,но наверное это дело привычки\uD83E\uDD37\uD83C\uDFFB\u200D♀️Всю жизнь у меня были андроиды в последние 7 лет дак вообще ходила с Самсунгами s серии,в прошлом голу взяла Самсунг s 22,не скажу что аппарат огонь часто грелся ,маленькая память 128 гб ,а в последнее время так вообще начал тупить сильно ,телефону 7 месяцев\uD83E\uDD37\uD83C\uDFFB\u200D♀️Решила брать айфон в декабре ,по работе нужна камера хорошая ,ну и что тут сказать конечно айфон выигрывает любой Самсунг тут даже говорить не о чем ,а так как я занимаюсь сьемками ,уж понимаю о чем говорю\uD83D\uDC4CСамсунг даже на качестве 8к ультра hd не выдавал такой результат как айфон 1080 \uD83E\uDD37\uD83C\uDFFB\u200D♀️По приложениям и нфс,да очень не удобно что отсутствует платежная система ,но всегда можно найти выход,Сбербанк тоже можно установить !!! В офисах банка!Пришел тебе все сделали за бесплатно !Вроде бы все нужные МНЕ приложения я смогла найти и установить единственное приложение совкомбанка нельзя установить на айфон ни в каком виде ,только вкладку на рабочий стол можно добавить браузера все ,это не удобно конечно", "18/09/20", 4.5f));
    }

}