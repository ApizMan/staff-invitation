package login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.staff_invitation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_page extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword, fullname, contactnum;;
    Button buttonSignUp, backLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    private DatabaseReference mDatabase;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), login_mainpage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.register);
        mAuth= FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        backLogin = findViewById(R.id.backlogin);

        fullname = findViewById(R.id.fullname);
        contactnum = findViewById(R.id.contact);

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login_mainpage.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser();
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(signup_page.this,"Enter Username",Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(signup_page.this,"Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(signup_page.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), login_mainpage.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(signup_page.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    public void writeNewUser() {
        User user = new User(
                fullname.getText().toString(),
                editTextEmail.getText().toString(),
                contactnum.getText().toString(),
                editTextPassword.getText().toString());

        mDatabase.child("users").child(user.getContactnum()).setValue(user);
    }
}