package com.example.ucot;

import Utilidades.Constantes;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DjangoApiFoto {

    String DJANGO_SITE = Constantes.URL_CREAR_EVIDENCIA;

    @Multipart
    @POST("upload/")
    Call<RequestBody>  uploadFile(@Part MultipartBody.Part file);



}