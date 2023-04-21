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
                "Перешёл на 13 pro max после 12. Первое, что бросается в глаза - габариты. И если при прямом сравнении разница не такая большая, то тактильно она ощущается будто аппарат в два раза шире, длиннее, толще, тяжелее. Отсюда все стандартные особенности лопаты - не в каждый карман влезет, носить неудобно, одной рукой не всегда можно пользоваться (риск уронить) и др. Но тут каждый сам, как говориться, решает, что ему важнее - компактность или автономность.\n" +
                "По автономности это монстр, держит два дня стабильно и только ради этого его стоит покупать. 12й с трудом доживал рабочий день, а спустя месяцы износа провод всегда был при мне. Здесь даже спустя полгода - год эксплуатации уверен, рабочий день гарантировано будет держать.\n" +
                "Второе ощутимое отличие - 120 герцовый дисплей, который делает анимации и скроллинг сильно плавнее. К нему быстро привыкаешь и перестаёшь замечать, но стоит взять в руки прошлые модели - разница на лицо.\n" +
                "На этом все, в остальном я разницу не вижу (она мб есть, просто для меня не релевантна).", "18/09/20", 4.5f));

        smartphone.addReview(new Review(new User("Daniil", "dwdwa", 915086),
                "говно", "12/09/20", 0.5f));
    }

}