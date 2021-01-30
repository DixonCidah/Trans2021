package com.mespana.trans2021.ui.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mespana.trans2021.BuildConfig;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentMapBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.ArtistsLocalService;

import org.jetbrains.annotations.NotNull;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.SimpleInvalidationHandler;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private List<OverlayItem> items;

    private FragmentMapBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IConfigurationProvider provider = Configuration.getInstance();
        provider.setUserAgentValue(BuildConfig.APPLICATION_ID);
        provider.load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        loadMap();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMap();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void loadMap() {
        MapView map = binding.mapView;

        MapTileProviderBasic tileProvider = new MapTileProviderBasic(requireContext().getApplicationContext(), TileSourceFactory.MAPNIK);
        SimpleInvalidationHandler mTileRequestCompleteHandler = new SimpleInvalidationHandler(binding.mapView);
        tileProvider.setTileRequestCompleteHandler(mTileRequestCompleteHandler);
        map.setTileProvider(tileProvider);

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(5.0);

        createMarkersList();

        GeoPoint startPoint = new GeoPoint(items.get(0).getPoint().getLatitude(), items.get(0).getPoint().getLongitude());
        mapController.setCenter(startPoint);

        putMarkers(map);

        map.invalidate();
    }

    private void navigateForward(OverlayItem item) {
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getResources().getString(R.string.shared_prefs_artist_rec_id), item.getTitle());
        editor.apply();
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.displayFragment);
    }

    private void putMarkers(MapView map) {
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(requireContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        navigateForward(item);
                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return false;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);
    }

    private void createMarkersList() {
        items = new ArrayList<>();
        for(Artist a : ArtistsLocalService.getArtistListFiltre()) {
            items.add(new OverlayItem(a.getRecordid(),
                    a.getArtistes(),
                    new GeoPoint(a.getGeo_point_2d_x(),a.getGeo_point_2d_y())));
        }
    }

}
