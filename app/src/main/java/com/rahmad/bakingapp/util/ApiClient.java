package com.rahmad.bakingapp.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Api client.
 */
public class ApiClient {

  private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
  private static Retrofit retrofit = null;

  /**
   * Gets client.
   *
   * @return the client
   */
  public static Retrofit getClient() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofit;
  }
}
