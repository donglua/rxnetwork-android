package com.laimiux.rxnetwork;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * Observe the network change state.
 * <p/>
 * To use this, you need to add this code to AndroidManifest.xml
 * <p/>
 * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
 * <p/>
 * <pre>
 */
public class RxNetwork {
  private RxNetwork() {
    // No instances
  }

  /**
   * Helper function that returns the connectivity state
   *
   * @param context Context
   * @return Connectivity State
   */
  public static boolean getConnectivityStatus(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return null != activeNetwork && activeNetwork.isConnected();

  }

  /**
   * Creates an observable that listens to connectivity changes
   */
  public static Observable<Boolean> stream(Context context) {
    final Context applicationContext = context.getApplicationContext();
    final IntentFilter action = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    return ContentObservable.fromObservableBroadcast(context, action)
        .map(new Function<Intent, Boolean>() {
          @Override
          public Boolean apply(Intent intent) throws Exception {
            return getConnectivityStatus(applicationContext);
          }
        }).distinctUntilChanged();
  }

  /**
   * Creates an observable that listens to connectivity changes
   */
  public static Flowable<Boolean> flow(Context context) {
    final Context applicationContext = context.getApplicationContext();
    final IntentFilter action = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    return ContentObservable.fromFlowableBroadcast(context, action)
        .map(new Function<Intent, Boolean>() {
          @Override
          public Boolean apply(Intent intent) throws Exception {
            return getConnectivityStatus(applicationContext);
          }
        }).distinctUntilChanged();
  }



}
