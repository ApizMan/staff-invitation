package Admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.staff_invitation.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Add_admin extends AppCompatActivity {

    EditText companyname, contactnumber, email, eventdate, eventdescription, eventlocation, eventname, eventtime;
    Button btnAdd, btnBack;
    Vibrator vibrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_admin);

        eventname = (EditText)findViewById(R.id.txtEvent);
        eventdate = (EditText)findViewById(R.id.txtDate);
        eventtime = (EditText)findViewById(R.id.txtTime);
        eventlocation = (EditText)findViewById(R.id.txtlocation);
        eventdescription = (EditText)findViewById(R.id.txtEventDescription);
        email = (EditText)findViewById(R.id.txtEmail);
        companyname = (EditText)findViewById(R.id.txtCompany);
        contactnumber = (EditText)findViewById(R.id.txtContact);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_admin.this,dashboard_admin.class);
                startActivity(intent);
            }
        });
    }
    private void insertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("eventname", eventname.getText().toString());
        map.put("eventdate", eventdate.getText().toString());
        map.put("eventtime", eventtime.getText().toString());
        map.put("eventlocation", eventlocation.getText().toString());
        map.put("eventdescription", eventdescription.getText().toString());
        map.put("email", email.getText().toString());
        map.put("companyname", companyname.getText().toString());
        map.put("contactnumber", contactnumber.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("events").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Add_admin.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        vibrateDevice();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_admin.this, "Error While Inserting", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearAll()
    {
        eventname.setText("");
        eventdate.setText("");
        eventtime.setText("");
        eventlocation.setText("");
        eventdescription.setText("");
        email.setText("");
        companyname.setText("");
        contactnumber.setText("");
    }

    private void vibrateDevice() {
        // Vibrate for 500 milliseconds (0.5 seconds)
        vibrator.vibrate(500);
    }
}
