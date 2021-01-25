package com.mespana.trans2021.ui.map;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mespana.trans2021.R;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.JsonParsingService;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;


public class MapFragment extends Fragment {

    private Context ctx;

    private ArrayList<OverlayItem> items;

    private MapView map;
    private IMapController mapController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
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

        GeoPoint startPoint = new GeoPoint(items.get(0).getPoint().getLatitude(), items.get(0).getPoint().getLongitude());
        mapController.setCenter(startPoint);

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(ctx, items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        // TODO : Passer sur le Fragment de display en single tap ?
                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return false;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);

        return v;
    }

    private void createMarkersList() {
        for(Artist a : JsonParsingService.getArtistList()) {
            items.add(new OverlayItem(a.getArtistes(),
                    a.getOrigine_pays1(),
                    new GeoPoint(a.getGeo_point_2d_x(),a.getGeo_point_2d_y())));
        }
    }

}
