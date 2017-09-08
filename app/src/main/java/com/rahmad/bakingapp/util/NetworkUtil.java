package com.rahmad.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rahmad on 7/2/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */
public class NetworkUtil {

  /**
   * Is online boolean.
   *
   * @param context the context
   * @return the boolean
   */
  public static boolean isOnline(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
    return (netInfo != null && netInfo.isConnected());
  }
}
