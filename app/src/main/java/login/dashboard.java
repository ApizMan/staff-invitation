package login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.staff_invitation.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import viewDetail.viewDetail_Admin;
import viewDetail.viewDetail_User;
import Admin.dashboard_admin;

public class dashboard extends AppCompatActivity {

    Button buttonLogout;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressBar progressBar;

    Button viewDetailUser;

    Button list;

    ListView myListView;

    ArrayList<String> myArrayList = new ArrayList<>();

    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        buttonLogout = findViewById(R.id.logout);
        user = auth.getCurrentUser();
        viewDetailUser = (Button) findViewById(R.id.viewDetailUser);

        if (user ==null){
            Intent intent = new Intent(getApplicationContext(), login_mainpage.class);
            startActivity(intent);
            finish();
       }

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login_mainpage.class);
                startActivity(intent);
                finish();
            }
        });

        viewDetailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, viewDetail_User.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(dashboard.this, android.R.layout.simple_list_item_1, myArrayList);

        myListView = (ListView) findViewById(R.id.listViewDashboardUser);

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
}