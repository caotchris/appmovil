package com.example.ucot;

import android.app.ProgressDialog;
import android.os.Bundle;


import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import Utilidades.Constantes;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class main extends AppCompatActivity {
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uploadFoto();
    }

    private void uploadFoto() {
        dialog = new ProgressDialog(this);
        dialog.show ();

        String image_path = Constantes.foto;

        File imageFile = new File(image_path);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApiFoto.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DjangoApiFoto postApi= retrofit.create(DjangoApiFoto.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);
        MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("model_pic", imageFile.getName(), requestBody);
//                .createFormData("CodigoInfraccion", "20", requestBody);


        Call<RequestBody> call = postApi.uploadFile(multiPartBody);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.d("good", "good");
                dialog.cancel();

            }
            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d("fail", "fail");
                dialog.cancel();
            }
        });


    }

    private void uploadVideo() {
        dialog = new ProgressDialog(this);
        dialog.show ();

        String video_path = Constantes.video;

        File imageFile = new File(video_path);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApivideo.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        DjangoApiFoto postApi= retrofit.create(DjangoApiFoto.class);


        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);
        MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("model_pic", imageFile.getName(), requestBody);
//                .createFormData("id", imageFile.getName(), requestBody);


        Call<RequestBody> call = postApi.uploadFile(multiPartBody);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.d("good", "good");
                dialog.cancel();

            }
            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d("fail", "fail");
                dialog.cancel();
            }
        });


    }

    private void uploadAudio() {
        dialog = new ProgressDialog(this);
        dialog.show ();
        String audio_path = Constantes.audio;
        File imageFile = new File(audio_path);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApiAudio.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DjangoApiFoto postApi= retrofit.create(DjangoApiFoto.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);
        MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("model_pic", imageFile.getName(), requestBody);
//                .createFormData("id", imageFile.getName(), requestBody);

        Call<RequestBody> call = postApi.uploadFile(multiPartBody);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.d("good", "good");
                dialog.cancel();

            }
            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d("fail", "fail");
                dialog.cancel();
            }
        });

    }

}
