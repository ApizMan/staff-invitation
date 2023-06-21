package login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.staff_invitation.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import viewDetail.viewDetail_Admin;

public class dashboard extends AppCompatActivity {

    Button buttonLogout;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressBar progressBar;

    Button viewDetailAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        buttonLogout = findViewById(R.id.logout);
        user = auth.getCurrentUser();
        viewDetailAdmin = (Button) findViewById(R.id.viewDetailAdmin);

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

        viewDetailAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, viewDetail_Admin.class);
                startActivity(intent);
            }
        });
    }
}