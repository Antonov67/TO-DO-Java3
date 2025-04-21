package org.example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodoService {

    private static final String BASE_URl = "http://192.168.11.164:8090/api/";

    API api;

    public TodoService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    public void getTodos(SimpleCallback<ResponseTodo> callback) {
        Call<ResponseTodo> call = api.getTodos();
        call.enqueue(new Callback<ResponseTodo>() {
            @Override
            public void onResponse(Call<ResponseTodo> call, Response<ResponseTodo> response) {
                if (response.isSuccessful()) {
                    callback.load(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTodo> call, Throwable throwable) {

            }
        });
    }
}
