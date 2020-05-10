package com.example.amna.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.amna.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.amna.activities.UserProfile.adress;
import static com.example.amna.activities.UserProfile.email;
import static com.example.amna.activities.UserProfile.name;
import static com.example.amna.activities.UserProfile.ph;
import static com.example.amna.activities.UserProfile.profile;

public class ProfileFragment extends Fragment {
    TextView emailid,phone,adresstext,nametext;
   // public static String name,email,profile,ph,adress;
    CircleImageView profile_image;

    @Override
    public void onResume() {
        super.onResume();
        emailid.setText(email);
        nametext.setText(name);
        adresstext.setText(adress);
        phone.setText(ph);
        Glide.with(this).load(profile).into(profile_image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        emailid=view.findViewById(R.id.emailid);
        phone=view.findViewById(R.id.phone);
        adresstext=view.findViewById(R.id.address);
        profile_image=view.findViewById(R.id.profile);
        nametext=view.findViewById(R.id.name);
//        emailid.setText("addi@gmail.com");
//        nametext.setText("Adnan Talib");
//        adresstext.setText("Pakpattan");
//        Glide.with(this).load("http:\\/\\/www.pushtrends.org\\/attendence_system\\/images\\/5.jpg").into(profile_image);
        return view;

    }
}
