package org.juicecode.telehlam.rest;

import android.content.SharedPreferences;
import android.util.Log;

import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestUserClass {
    private static UserApi userApi;
    private FragmentManagerSimplifier fragmentManagerSimplifier;
    private User user;
    private SharedPreferences sharedPreferences;
    private DrawerLocker drawerLocker;

    public void registerUser(){
        //register user with all info
        final Call registerUser = userApi.registerUser(user);
        registerUser.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //removing fragments
                        fragmentManagerSimplifier.remove("firstRegistrationFragment");
                        fragmentManagerSimplifier.remove("secondRegistrationFragment");
                        fragmentManagerSimplifier.remove("authorisation");
                        //saving info
                        sharedPreferences.edit().putBoolean("isNotRegistered", false).putString("userLogin", user.getLogin()).commit();
                        drawerLocker.setDrawerLock(false);
                        Log.i("responseMessage", response.body().toString());
                    }
                } else {
                    //TODO make error handling

                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("error", t.toString());
            }
        });
    }

    public void signIn(){
    Call signIn = userApi.signIn(user);
    signIn.enqueue(new Callback() {
        @Override
        public void onResponse(Call call, Response response) {

        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    });



    }
    public RestUserClass(FragmentManagerSimplifier fragmentManagerSimplifier,User user,DrawerLocker drawerLocker,SharedPreferences sharedPreferences,RetrofitBuilder retrofitBuilder) {
        this.fragmentManagerSimplifier = fragmentManagerSimplifier;
        this.drawerLocker = drawerLocker;
        this.sharedPreferences = sharedPreferences;
        this.user = user;
        this.userApi = retrofitBuilder.getUserApi();
    }
}
