package org.juicecode.telehlam.socketio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

public class onMessageListener implements Emitter.Listener {
    private MainActivity mainActivity;
    private FragmentManagerSimplifier fragmentManagerSimplifier;
    private onMessageCallback callback;
    public onMessageListener(final MainActivity mainActivity, final onMessageCallback callback) {
        this.mainActivity = mainActivity;
        this.fragmentManagerSimplifier = mainActivity;
        this.callback = callback;
    }

    @Override
    public void call(final Object... args) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // opening authorisation if user's token is wrong
                JSONObject json = (JSONObject) args[0];
                String text = null;
                try {
                    text = json.getString("text");
                } catch (JSONException jsonException){

                }
                MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
                mutableLiveData.setValue(text);
                callback.savingIncomingMessage(mutableLiveData);
            }
        });
    }
}

