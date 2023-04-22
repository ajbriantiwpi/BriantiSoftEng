package edu.wpi.teamname;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.extras.Joke;
import edu.wpi.teamname.extras.JokeAPI;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {

    DataManager.configConnection(
        "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
        "teamd",
        "teamd40");
    App.launch(App.class, args);
    // create a Retrofit instance
    Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl("https://official-joke-api.appspot.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    // create an instance of the API interface
    JokeAPI jokeApi = retrofit.create(JokeAPI.class);

    // make a request to the API
    Call<Joke> call = jokeApi.getRandomJoke("programming", new int[] {53, 92, 45});
    Response<Joke> response = call.execute();

    // check if the request was successful
    if (response.isSuccessful()) {
      Joke joke = response.body();
      System.out.println("Joke ID: " + joke.getId());
      System.out.println(joke.getSetup());
      System.out.println(joke.getPunchline());
    } else {
      System.out.println("Request failed: " + response.code());
    }
    System.out.println("MAIN IS COMMENTED OUT!!!!UNCOMMENT IF PUSHING TO MAIN");

  }
  // shortcut: psvm
}
