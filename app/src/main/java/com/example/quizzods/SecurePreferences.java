package com.example.quizzods;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecurePreferences {
    private SharedPreferences sharedPreferences;

    public SecurePreferences(Context context) throws GeneralSecurityException, IOException {
        // Configura a MasterKey
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        // Cria o EncryptedSharedPreferences usando o contexto como primeiro argumento
        sharedPreferences = EncryptedSharedPreferences.create(
                context,               // Contexto
                "user_credentials",    // Nome do arquivo
                masterKey,             // Chave mestra
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    // Métodos para salvar e recuperar as credenciais
    public void saveCredentials(String username, String hashedPassword, String salt) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("hashed_password", hashedPassword);
        editor.putString("salt", salt);
        editor.apply();
    }

    public String getHashedPassword() {
        return sharedPreferences.getString("hashed_password", null);
    }

    public String getSalt() {
        return sharedPreferences.getString("salt", null);
    }
}
