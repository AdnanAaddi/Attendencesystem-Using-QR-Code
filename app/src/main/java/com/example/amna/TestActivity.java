package com.example.amna;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amna.network.request.ApiClient;
import com.example.amna.network.request.ApiInterface;
import com.example.amna.network.request.RestError;
import com.example.amna.network.response.LoginResponse;
import com.example.amna.network.response.MessageResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    Button btnlogin,btnqrcode;
    String loginToken;
  //  LoaderDialog loaderDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btnlogin=findViewById(R.id.btnlogin);
        btnqrcode=findViewById(R.id.btnqrcode);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();
            }
        });
        btnqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });
    }

    private void addCourse() {
       // loaderDialog.showDialog(TestActivity.this);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        apiService.receiveMsg("Bearer " + loginToken,"512").enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                String msg=response.body().getMessage();
                Log.e("AddCourse","Message==>"+ msg);
                Toast.makeText(TestActivity.this,msg, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                //loaderDialog.hideDialog();
                Log.e("fail", "==>" + t.getMessage());
            }
        });

    }

    private void loginRequest() {
        //loaderDialog.showDialog(TestActivity.this);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        apiService.signUpCall("amnaajaz06@gmail.com", "amna0019").enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               // loaderDialog.hideDialog();

                if (response.code() == 200) {
                    Log.e("success", "==>" + response.body().toString());
                    Log.e("loginToken", "==>" + response.body().getToken());
                    loginToken = response.body().getToken();
                    Toast.makeText(TestActivity.this, "Login Successfully",
                            Toast.LENGTH_SHORT).show();
//                    Log.e("userDetail", "==>" + response.body().getData().getUserId());
                } else {
                    RestError errorResponse;
                    if (!response.isSuccessful()) {
                        Gson gson = new Gson();
                        try {
                            errorResponse = gson.fromJson(response.errorBody().string(), RestError.class);
                            errorResponse.setError(true);
                            Log.e("fail", "==>" + errorResponse.getMessage());
                            Toast.makeText(TestActivity.this, "Login Failed Incorrect " +
                                    "Username and Password", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //loaderDialog.hideDialog();
                Log.e("fail", "==>" + t.getMessage());
                Toast.makeText(TestActivity.this, "Login Failed Incorrect Username and Password",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
