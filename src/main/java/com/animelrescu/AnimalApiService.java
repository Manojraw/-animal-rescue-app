package com.animelrescu;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import java.util.List;

public interface AnimalApiService {

    @Headers("X-Api-Key: oYz/Eviaj+Szl4Iw7qiC2w==zbiUUXZC7FZU2Nnw") // Replace with your actual API key
    @GET("v1/animals") // Corrected URL: No ?name= here
    Call<List<AnimalDTO>> getAnimals(@Query("name") String query); // Use @Query for the entire parameter

    static AnimalApiService create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AnimalApiService.class);
    }
}