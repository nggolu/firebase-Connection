package com.example.goku.firebasecheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Email, Password;
    Button SignUp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Filter", "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d("Filter", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        SignUp = (Button) findViewById(R.id.signup_button);

        SignUp.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {

        final String email = Email.getText().toString();
        final String password = Password.getText().toString();
        Log.d("Filter",email + " " +password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Filter", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        Log.d("Filter", "hello "+email+" pass "+ password +" " + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("Filter", "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "enter vaild authentiction",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent intent = new Intent(LoginActivity.this,Dummy.class);
                            startActivity(intent);
                        }

                        // ...
                    }
                });
    }
}
