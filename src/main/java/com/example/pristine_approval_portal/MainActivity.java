package com.example.pristine_approval_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pristine_approval_portal.Model.LoginModel;
import com.example.pristine_approval_portal.globalization.ApiRequestFailure;
import com.example.pristine_approval_portal.globalization.LoadingDialog;
import com.example.pristine_approval_portal.retrofithit.retrofitApi.NetworkInterface;
import com.example.pristine_approval_portal.retrofithit.retrofitApi.RetrofitApiUtils;
import com.example.pristine_approval_portal.sessionManagement.SessionManagement;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputLayout ti_email_layout,ti_password_layout;
    TextInputEditText editTextEmail,editTextPassword;
    Button cirLoginButton;
    SessionManagement sessionManagement;
    ProgressBar loading_login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //todo text input edit text
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        //todo text input layout
        ti_email_layout = findViewById(R.id.ti_email_layout);
        ti_password_layout = findViewById(R.id.ti_password_layout);
        //todo button
        cirLoginButton = findViewById(R.id.cirLoginButton);
        loading_login_button = findViewById(R.id.loading_login_button);
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editTextEmail.getText().length()<=0){
                    ti_email_layout.setError("Please Enter Email.");
                }else {
                    ti_email_layout.setError(null);
                }

            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editTextPassword.getText().length()<=0){
                    ti_password_layout.setError("Please Enter Password.");
                }else {
                    ti_password_layout.setError(null);
                }

            }
        });

        sessionManagement = new SessionManagement(getApplicationContext());
        try{
            if(sessionManagement.getUserEmail()!=null && !sessionManagement.getUserEmail().equalsIgnoreCase("")){
                        Intent intent = new Intent(this,Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                       finish();
            }
        }catch (Exception e){

        }

        cirLoginButton.setOnClickListener(view -> {
            loginMethod();
        });

    }

    private void loginMethod() {
        try {
            if (editTextEmail.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please fill email", Toast.LENGTH_LONG).show();
            } else if (editTextPassword.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please fill password", Toast.LENGTH_LONG).show();
            } else {
                cirLoginButton.setVisibility(View.GONE);
                loading_login_button.setVisibility(View.VISIBLE);
                NetworkInterface mpiLoginService = RetrofitApiUtils.getPristineAPIService();
                JsonObject postedJson = new JsonObject();
                postedJson.addProperty("Email", editTextEmail.getText().toString());
                postedJson.addProperty("password", editTextPassword.getText().toString());
                Call<List<LoginModel>> loginHit = mpiLoginService.postLogin(postedJson);
                loginHit.enqueue(new Callback<List<LoginModel>>() {
                    @Override
                    public void onResponse(Call<List<LoginModel>> call, Response<List<LoginModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                List<LoginModel> tempLogin = response.body();
                                if (tempLogin.size() > 0 && tempLogin.get(0).condition) {
                                    SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
                                    sessionManagement.setUserEmail(tempLogin.get(0).email);
                                    sessionManagement.setUsername(tempLogin.get(0).name);
                                    sessionManagement.setMenu(new Gson().toJson(tempLogin.get(0).menu));
                                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (tempLogin.size() > 0) {
                                        if (tempLogin.get(0).message.contains("Email is wrong")) {
                                            ti_email_layout.setError(tempLogin.get(0).message);
                                        } else if (tempLogin.get(0).message.contains("Password is wrong")) {
                                            ti_password_layout.setError(tempLogin.get(0).message);
                                        } else {
                                            Snackbar.make(cirLoginButton, tempLogin.get(0).message, Snackbar.LENGTH_LONG).show();

                                        }
                                    } else {
                                        Snackbar.make(cirLoginButton, "Api Not Respond.", Snackbar.LENGTH_LONG).show();

                                    }
                                }
                            } else {
                                Snackbar.make(cirLoginButton, response.message() + ". Error Code:" + response.code(), Snackbar.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }finally {
                            cirLoginButton.setVisibility(View.VISIBLE);
                            loading_login_button.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LoginModel>> call, Throwable t) {
                        cirLoginButton.setVisibility(View.VISIBLE);
                        loading_login_button.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(t,getClass().getName(),"login",MainActivity.this);
                    }
                });
            }
        }catch (Exception ey){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}