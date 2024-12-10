package ie.ul.cs4084;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragment3 extends Fragment implements OnMapReadyCallback {
GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SupportMapFragment mapFragment =(SupportMapFragment) getParentFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return inflater.inflate(R.layout.fragment_fragment3, container, false);


    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;
        //to test
        LatLng Cork = new LatLng(51.8985,8.4756);
        map.addMarker(new MarkerOptions().position(Cork).title("Cork"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Cork));
    }
}