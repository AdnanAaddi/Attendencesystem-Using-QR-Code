package com.example.amna.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.amna.R;
import com.example.amna.network.request.ApiClient;
import com.example.amna.network.request.ApiInterface;
import com.example.amna.network.request.RestError;
import com.example.amna.network.response.LoginResponse;
import com.example.amna.network.response.UserResponce;
import com.example.amna.utils.LoaderDialog;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    AppCompatEditText email,password;
    public static String loginToken;
    private String emailvalid;
    private String passwordvalid;
    LoaderDialog loaderDialog;
  // public String name;
  // public String emailad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Attendence System Using QR Code");
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 emailvalid=email.getText().toString();
                 passwordvalid=password.getText().toString();
                if(emailvalid.equals("") ||passwordvalid.equals("")){
                    Toast.makeText(MainActivity.this, "Please Fill all the fields",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    loginRequest();
                    //userRequest();
                }
            }
        });
        loaderDialog = new LoaderDialog();
    }
    private void userRequest() {
        //loaderDialog.showDialog(MainActivity.this);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        apiService.userCall("Bearer " + loginToken).enqueue(new Callback<UserResponce>() {

            @Override
            public void onResponse(Call<UserResponce> call, Response<UserResponce> response) {

                loaderDialog.hideDialog();
                if (response.code() == 200) {
                    Log.e("success", "==>" + response.body().toString());

                    String name = response.body().getName();
                    String emailad = response.body().getEmail();
                    String profile=response.body().getProfile();
                    String ph=response.body().getPhoneno();
                    String adress=response.body().getAddress();
                    Intent intent=new Intent(MainActivity.this, UserProfile.class);
                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                    intent.putExtra("username",name);
                    intent.putExtra("email",emailad);
                    intent.putExtra("profile",profile);
                    intent.putExtra("phone",ph);
                    intent.putExtra("adress",adress);
                    startActivity(intent);
                 //   Log.e("userDetail", "==>" + response.body().getData().getUserId());
                } else {
                    RestError errorResponse;
                    if (!response.isSuccessful()) {
                        Gson gson = new Gson();
                        try {
                            errorResponse = gson.fromJson(response.errorBody().string().toString(), RestError.class);
                            errorResponse.setError(true);
                            Log.e("fail", "==>" + errorResponse.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<UserResponce> call, Throwable t) {
                loaderDialog.hideDialog();
                Log.e("fail", "==>" + t.getMessage());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==12){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission is granted", Toast.LENGTH_SHORT).show();
                Log.e("OnResult","Camera Permission Granted");
            }
            else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                Log.e("OnResult","Camera Permission Denied");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    12);
            Log.e("Reques","Request");

        }
        else {
            Toast.makeText(this, "Camera Permission is granted", Toast.LENGTH_SHORT).show();
            Log.e("Already","Camera Permission Granted");
        }

    }

    private void loginRequest() {
        loaderDialog.showDialog(MainActivity.this);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        apiService.signUpCall(emailvalid, passwordvalid).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loaderDialog.hideDialog();

                if (response.code() == 200) {
                    Log.e("success", "==>" + response.body().toString());
                    Log.e("loginToken", "==>" + response.body().getToken());
                    loginToken = response.body().getToken();
                    Toast.makeText(MainActivity.this, "Login Successfully",
                            Toast.LENGTH_SHORT).show();
                    userRequest();
//                    Log.e("userDetail", "==>" + response.body().getData().getUserId());
                } else {
                    RestError errorResponse;
                    if (!response.isSuccessful()) {
                        Gson gson = new Gson();
                        try {
                            errorResponse = gson.fromJson(response.errorBody().string(), RestError.class);
                            errorResponse.setError(true);
                            Log.e("fail", "==>" + errorResponse.getMessage());
                            Toast.makeText(MainActivity.this, "Login Failed Incorrect " +
                                            "Username and Password", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loaderDialog.hideDialog();
                Log.e("fail", "==>" + t.getMessage());
                Toast.makeText(MainActivity.this, "Login Failed Incorrect Username and Password",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
}
