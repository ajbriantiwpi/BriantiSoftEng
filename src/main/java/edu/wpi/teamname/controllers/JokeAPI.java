package edu.wpi.teamname.controllers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JokeAPI {
  @GET("jokes/random?exclude=?")
  Call<Joke> getRandomJoke(@Query("exclude") String category);
}
