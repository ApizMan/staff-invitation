package viewDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.staff_invitation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import  com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Admin.dashboard_admin;
import login.dashboard;


public class viewDetail_Admin extends AppCompatActivity implements OnMapReadyCallback{

    private  GoogleMap myMap;
    Button backButton;

    ListView myListView;

    ArrayList<String> myArrayList = new ArrayList<>();

    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_detail_admin);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        backButton = (Button) findViewById(R.id.button_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewDetail_Admin.this, dashboard_admin.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(viewDetail_Admin.this, android.R.layout.simple_list_item_1, myArrayList);

        myListView = (ListView) findViewById(R.id.listViewAdmin);

        myListView.setAdapter(myArrayAdapter);

        mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-2e102-default-rtdb.firebaseio.com/events/-NYYtveEYJwpCAcZW5LB");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                myArrayList.add(value);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        LatLng sydney = new LatLng(3.974341, 102.438057);
        myMap.addMarker(new MarkerOptions().position(sydney).title("Pahang"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
