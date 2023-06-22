package login;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
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

import Admin.dashboard_admin;
import java.util.concurrent.Executor;

public class login_mainpage extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonSignUp0, buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout mMainLayout;

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
        mMainLayout = findViewById(R.id.main_layout);

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

        BiometricManager biometricManager=BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "Device Doesn't Have Fingerprint", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "Fingerprint Not Working", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getApplicationContext(), "No Fingerprint Assigned", Toast.LENGTH_SHORT).show();
                break;
        }

        Executor executor=ContextCompat.getMainExecutor(this);

        biometricPrompt=new BiometricPrompt(login_mainpage.this, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Biometric Success", Toast.LENGTH_SHORT).show();
                mMainLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("FKOM SDN. BHD")
                        .setDescription("Use Fingerprint To Enter App").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);

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


                                if (mAuth.getUid().equals("eYh3lWy2n7YjZyvx4MovK6NM5qX2")) {

                                    if (task.isSuccessful()) {

                                            Toast.makeText(login_mainpage.this, "Login Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                            startActivity(intent);
                                            finish();
                                    } else {
                                        Toast.makeText(login_mainpage.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(login_mainpage.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), dashboard_admin.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(login_mainpage.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
            }
        });
    }
}