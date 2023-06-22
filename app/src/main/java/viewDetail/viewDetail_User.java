package viewDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import login.dashboard;
import login.login_mainpage;

public class viewDetail_User extends AppCompatActivity implements OnMapReadyCallback{

    private  GoogleMap myMap;
    Button backButton, acceptButton, rejectButton;

    ListView myListView;

    ArrayList<String> myArrayList = new ArrayList<>();

    DatabaseReference mRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_detail_user);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        backButton = (Button) findViewById(R.id.button_back);
        acceptButton = (Button) findViewById(R.id.accept_button);
        rejectButton = (Button) findViewById(R.id.reject_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewDetail_User.this, dashboard.class);
                startActivity(intent);
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewDetail_User.this, dashboard.class);
                startActivity(intent);
                Toast.makeText(viewDetail_User.this, "You have accept the invitation.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewDetail_User.this, dashboard.class);
                startActivity(intent);
                Toast.makeText(viewDetail_User.this, "You have reject the invitation.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(viewDetail_User.this, android.R.layout.simple_list_item_1, myArrayList);

        myListView = (ListView) findViewById(R.id.listViewUser);

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
