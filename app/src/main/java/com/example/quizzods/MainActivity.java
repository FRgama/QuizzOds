package com.example.quizzods;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {
    private Button createAccountBtn;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private SecurePreferences securePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login_btn);
        createAccountBtn = findViewById(R.id.create_account_btn);

        // Inicializa as preferências seguras
        try {
            securePreferences = new SecurePreferences(this);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao inicializar segurança: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        /* loginBtn.setOnClickListener(v -> {
                    String username = usernameInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    Log.i("Login", "Username: " + username + ", Password: " + password);
        }); */

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        /* loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, menuJogo.class);
                startActivity(intent);
            }
        }); */

        // Clique no botão de login para verificar as credenciais
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, preencha os campos de login.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Recupera as credenciais armazenadas (se houver)
                String storedHashedPassword = securePreferences.getHashedPassword();
                String storedSalt = securePreferences.getSalt();

                if (storedHashedPassword == null || storedSalt == null) {
                    // Primeiro login: armazena as credenciais
                    try {
                        String salt = PasswordUtils.getSalt();
                        String hashedPassword = PasswordUtils.generateSecurePassword(password, salt);
                        securePreferences.saveCredentials(username, hashedPassword, salt);
                        Toast.makeText(MainActivity.this, "Primeiro login, credenciais salvas!", Toast.LENGTH_SHORT).show();

                        // Redireciona para a tela do jogo
                        Intent intent = new Intent(MainActivity.this, menuJogo.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Erro ao salvar as credenciais.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Verifica a senha
                    boolean isPasswordCorrect = PasswordUtils.verifyPassword(password, storedHashedPassword, storedSalt);
                    if (isPasswordCorrect) {
                        Toast.makeText(MainActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                        // Redireciona para a tela do jogo
                        Intent intent = new Intent(MainActivity.this, menuJogo.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Senha incorreta!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

