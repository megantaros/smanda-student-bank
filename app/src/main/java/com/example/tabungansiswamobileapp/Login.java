package com.example.tabungansiswamobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edNis = findViewById(R.id.ed_nis);
        EditText edPassword = findViewById(R.id.ed_password);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {

            String nis = edNis.getText().toString();
            String password = edPassword.getText().toString();

            if(nis.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "NIS dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, "Mohon tunggu...", Toast.LENGTH_SHORT).show();
                formLogin(nis, password);
            }
        });
    }

    private void formLogin(String nis, String password) {
        AndroidNetworking.post(Endpoint.LOGIN)
                .addBodyParameter("nis", nis)
                .addBodyParameter("password", password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONObject("data");
                            SharedPreferences sharedPreferences = Login.this.getSharedPreferences("login_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id_siswa", result.getString("id_siswa"));
                            editor.putString("nis", result.getString("nis"));
                            editor.putString("nama_siswa", result.getString("nama_siswa"));
                            editor.putString("tgl_lahir", result.getString("tgl_lahir"));
                            editor.putString("jenis_kelamin", result.getString("jenis_kelamin"));
                            editor.putString("kelas", result.getString("kelas"));
                            editor.putString("alamat", result.getString("alamat"));
                            editor.putString("agama", result.getString("agama"));
                            editor.putString("saldo", result.getString("saldo"));
                            editor.apply();
                            Toast.makeText(Login.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Koneksi Gagal!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Login.this, "Email atau Password Salah!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}