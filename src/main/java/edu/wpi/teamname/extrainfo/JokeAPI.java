package edu.wpi.teamname.extrainfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JokeAPI {
  @GET("jokes/random")
  Call<Joke> getRandomJoke(@Query("exclude") String type, @Query("id") int[] ids);
}
