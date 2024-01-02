package edu.birzeit.saeedmosaffer.newsaggregator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailLogIn;
    private EditText edtPasswordLogIn;
    private CheckBox checkBoxRememberMe;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmailLogIn = findViewById(R.id.edtEmailLogIn);
        edtPasswordLogIn = findViewById(R.id.edtPasswordLogIn);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        preferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Get "Remember Me" state from SharedPreferences and set the checkbox accordingly (checked/unchecked)
        boolean rememberMeChecked = preferences.getBoolean("rememberMe", false);
        checkBoxRememberMe.setChecked(rememberMeChecked);

        // If "Remember Me" was checked, fill the email and password fields with the saved values
        if (rememberMeChecked) {
            String savedEmail = preferences.getString("savedEmail", "");
            String savedPassword = preferences.getString("savedPassword", "");

            edtEmailLogIn.setText(savedEmail);
            edtPasswordLogIn.setText(savedPassword);
        }

        Button btnLogIn = findViewById(R.id.btnLogIn);
        Button btnToRegister = findViewById(R.id.btnToRegister);

        btnLogIn.setOnClickListener(v -> {
            String email = edtEmailLogIn.getText().toString();
            String password = edtPasswordLogIn.getText().toString();

            if (login(email, password)) {
                // Successful login attempt (email and password match) - go to NewsFeedActivity

                // Save "Remember Me" state and entered email/password
                saveRememberMeState(email, password);

                Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            } else {
                // Unsuccessful login attempt (email and password don't match) - show error message
                Toast.makeText(LoginActivity.this, "Incorrect email or password. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        btnToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private boolean login(String email, String password) {
        Map<String, ?> allEntries = preferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue() != null && entry.getKey().startsWith("account_")) {
                String accountJson = entry.getValue().toString();
                Account account = Account.fromJson(accountJson);

                if (account != null && account.getEmail().equals(email) && account.getPassword().equals(password)) {
                    // Match found
                    return true;
                }
            }
        }
        // No match found
        return false;
    }

    private void saveRememberMeState(String email, String password) {
        // Save "Remember Me" state
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("rememberMe", checkBoxRememberMe.isChecked());

        // Save entered email and password if "Remember Me" is checked
        if (checkBoxRememberMe.isChecked()) {
            editor.putString("savedEmail", email);
            editor.putString("savedPassword", password);
        }

        editor.apply();
    }
}
