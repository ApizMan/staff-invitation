package login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.staff_invitation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_mainpage extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonSignUp0, buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), dashboard.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mainpage);

        editTextEmail = findViewById(R.id.enterusername);
        editTextPassword = findViewById(R.id.enterpassword);
        buttonSignUp0 = findViewById(R.id.signupbutton);
        buttonLogin = findViewById(R.id.loginbutton);
        mAuth= FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        buttonSignUp0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signup_page.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(login_mainpage.this,"Enter Username",Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(login_mainpage.this,"Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(login_mainpage.this,"Login Successful",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(login_mainpage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}