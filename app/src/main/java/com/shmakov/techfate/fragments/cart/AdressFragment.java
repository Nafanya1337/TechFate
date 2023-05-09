package com.shmakov.techfate.fragments.cart;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.shmakov.techfate.R;

import com.shmakov.techfate.SavedAddressesFragment;
import com.yandex.mapkit.MapKitFactory;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.Animation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class AdressFragment extends Fragment implements GeoObjectTapListener, InputListener {
    private MapView mapView;

    String address = "";
    FrameLayout adressFragmentFrameLayout;

    SavedAddressesFragment savedAddressesFragment;

    ArrayList<String> addresses = new ArrayList<>();

    PlacemarkMapObject placemark = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adress, container, false);
        mapView = view.findViewById(R.id.mapview);
        adressFragmentFrameLayout = view.findViewById(R.id.adressFragmentFrameLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        mapView.getMap().addTapListener(this);
        mapView.getMap().addInputListener(this);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        savedAddressesFragment = new SavedAddressesFragment(getContext(), addresses);
        ft.replace(adressFragmentFrameLayout.getId(), savedAddressesFragment).commit();

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey.equals("requestKey")) {
                    if (result.containsKey("Address")) {
                        String address = result.getString("Address");
                        addresses.add(address);
                        savedAddressesFragment.setAddresses(addresses);
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public boolean onObjectTap(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
        final GeoObjectSelectionMetadata selectionMetadata = geoObjectTapEvent
                .getGeoObject()
                .getMetadataContainer()
                .getItem(GeoObjectSelectionMetadata.class);

        if (selectionMetadata != null) {
            mapView.getMap().selectGeoObject(selectionMetadata.getId(), selectionMetadata.getLayerId());
            address = getAdress(geoObjectTapEvent);
            String[] parts = makeAddressSplit();
            Bundle bundle = new Bundle();
            bundle.putStringArray("parts", parts);
            Navigation.findNavController(getView()).navigate(R.id.addressBottomSheetFragment, bundle);
        }


        return selectionMetadata != null;
    }


    private String getAdress(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
        String address = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        double latitude = geoObjectTapEvent.getGeoObject().getGeometry().get(0).getPoint().getLatitude();
        double longitude = geoObjectTapEvent.getGeoObject().getGeometry().get(0).getPoint().getLongitude();
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                return returnedAddress.getAddressLine(0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Некорректный адрес";
    }

    @Override
    public void onMapTap(@NonNull Map map, @NonNull Point point) {
        mapView.getMap().deselectGeoObject();
    }

    @Override
    public void onMapLongTap(@NonNull Map map, @NonNull Point point) {

    }


    public String[] makeAddressSplit() {
        String[] parts = address.split(", ");
        return parts;
    }
}