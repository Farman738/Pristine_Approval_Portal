package com.example.pristine_approval_portal.retrofithit.retrofitApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    public static Retrofit retrofit;

    public static Retrofit getClient(String baseUrl){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request origin = chain.request();
                Request request = origin.newBuilder()
                        .header("Content-Type","application/json")
                        .method(origin.method(),origin.body())
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient
                .readTimeout(2,TimeUnit.MINUTES)
                .connectTimeout(2,TimeUnit.MINUTES)
                .build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }

        return retrofit;
    }











//    public static Retrofit retrofit;
//
//    public static Retrofit getClient(String baseUrl){
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request origin = chain.request();
//                Request request = origin.newBuilder()
//                        .header("Content-Type","application/json")
//                        .method(origin.method(),origin.body())
//                        .build();
//
//
//                return chain.proceed(request);
//            }
//        });
//     OkHttpClient client =httpClient
//             .readTimeout(2, TimeUnit.MINUTES)
//             .connectTimeout(2,TimeUnit.MINUTES)
//              .build();
//     if(retrofit == null){
//         retrofit = new Retrofit.Builder()
//                 .baseUrl(baseUrl)
//                 .addConverterFactory(GsonConverterFactory.create())
//                 .client(client)
//                 .build();
//     }
//        return retrofit;
//    }
}
