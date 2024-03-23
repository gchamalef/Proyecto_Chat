package com.example.mychat.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mychat.model.UserModel;

public class AndroidUtil {

    public static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getUsername());
        intent.putExtra("userId",model.getUsername());
    }

    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        return userModel;
    }
}
