package com.mespana.trans2021.ui.map;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mespana.trans2021.R;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.JsonParsingService;
import com.mespana.trans2021.ui.display.DisplayFragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.Objects;

public class MapFragment extends Fragment {

    Context ctx;

    private ArrayList<OverlayItem> items;

    private MapView map;
    private IMapController mapController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ctx = getContext();
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(ctx));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_map, null);
        map = v.findViewById(R.id.mapView);

        map.setTileSource(TileSourceFactory.MAPNIK);

        map.getZoomController().setZoomInEnabled(true);
        map.getZoomController().setZoomOutEnabled(true);
        map.setMultiTouchControls(true);

        mapController = map.getController();
        mapController.setZoom(5.0);

        this.createMarkersList();
        this.putMarkers(map);

        GeoPoint startPoint = new GeoPoint(items.get(0).getPoint().getLatitude(), items.get(0).getPoint().getLongitude());
        mapController.setCenter(startPoint);

        return v;
    }

    private void putMarkers(MapView map) {
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(ctx, items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        // TODO : quand Display sera prêt : Fragment newFragment = new DisplayFragment(item.getTitle()); (le title est l'id nécessaire pour retrouvé le grp dnas le json)
                        Fragment newFragment = new DisplayFragment();
                        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

                        transaction.replace(R.id.view_pager, newFragment);

                        transaction.commit();
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
        for(Artist a : JsonParsingService.getArtistList()) {
            items.add(new OverlayItem(a.getRecordid(),
                    a.getArtistes(),
                    new GeoPoint(a.getGeo_point_2d_x(),a.getGeo_point_2d_y())));
        }
    }

}
