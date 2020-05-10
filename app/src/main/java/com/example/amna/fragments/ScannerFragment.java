package com.example.amna.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.amna.R;
import com.example.amna.activities.ScanerActivity;
import com.example.amna.network.request.ApiClient;
import com.example.amna.network.request.ApiInterface;
import com.example.amna.network.response.MessageResponse;
import com.example.amna.utils.LoaderDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.amna.activities.MainActivity.loginToken;
import static com.example.amna.activities.ScanerActivity.course_code;

public class ScannerFragment extends Fragment {
    Button scanerbtn;
    Button attendencebtn;
    LoaderDialog loaderDialog;
    public static int flag = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_scanner, container, false);
        getActivity().setTitle("DashBoard");
        loaderDialog = new LoaderDialog();
        scanerbtn=view.findViewById(R.id.scanerbtn);
        attendencebtn=view.findViewById(R.id.attendencebtn);
        scanerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ScanerActivity.class);
                startActivity(intent);
            }
        });
        attendencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0)
                    {
                        Toast.makeText(getContext(), "First Scan your Course_Code", Toast.LENGTH_SHORT).show();
                    }
                else {
                   addCourse();
               }
            }
        });

        return view;
    }
    private void addCourse() {
        loaderDialog.showDialog(getContext());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        apiService.receiveMsg("Bearer " + loginToken, course_code).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                loaderDialog.hideDialog();
                String msg=response.body().getMessage();
                Log.e("AddCourse","Message==>"+ msg);
                  Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();
                  flag=0;
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                loaderDialog.hideDialog();
                Log.e("fail", "==>" + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
