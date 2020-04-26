package org.juicecode.telehlam.ui.registration;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.User;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;

import java.security.cert.LDAPCertStoreParameters;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private MaterialToolbar toolbar;
    private Button floatingActionButton;
    private TextInputEditText loginField;
    private TextInputEditText passwordField;
    private TextInputEditText nameField;
    private TextInputEditText surnameField;
    private TextInputEditText repeatPassword;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.registration_fragment, container, false);

        floatingActionButton = view.findViewById(R.id.sign_up_button);
        final FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier) view.getContext();

        loginField = view.findViewById(R.id.loginField);
        repeatPassword = view.findViewById(R.id.repeatPasswordField);
        passwordField = view.findViewById(R.id.passwordField);
        nameField = view.findViewById(R.id.nameField);
        surnameField = view.findViewById(R.id.surnameField);

        final MainActivity activity = (MainActivity) view.getContext();
        activity.setDrawerLock(true);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            private String name;
            private String surname;
            private String login;
            private String phone;
            private String password;
            private String repeatedPassword;


            @Override
            public void onClick(View v) {
                name = nameField.getText().toString().trim();
                surname = surnameField.getText().toString().trim();
                login = loginField.getText().toString().trim();
                password = passwordField.getText().toString().trim();
                repeatedPassword = repeatPassword.getText().toString().trim();

                // TODO(matthew.nekirov@gmail.com): Саша, ПРОШУ, ПОДТЯНИ ЛОГИЧЕСКИЕ ОПЕРАТОРЫ.
                //  Я чуть с ума не сошел, пока ошибку искал.
                if (!(name.isEmpty() || surname.isEmpty() || login.isEmpty() || password.isEmpty()) && password.equals(repeatedPassword)) {
                    RetrofitBuilder retrofit = new RetrofitBuilder();

                    Call registerUser = retrofit.getAuthorisationAPI().registerUser(new User(login, password, name, surname));
                    registerUser.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Log.i("responseBody", Integer.toString(response.code()));
                            if (response.body() != null) {

                                // Constant.setUserLogin(login);
                                fragmentManagerSimplifier.remove("registration");
                                fragmentManagerSimplifier.remove("authorisation");
                                activity.setDrawerLock(false);
                                // Constant.isRegistered = true;
                                Log.i("responseMessage", response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.i("Connection error!", t.toString());
                            Snackbar
                                    .make(getView(), "Some error with network!",
                                            Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    });

                    fragmentManagerSimplifier.remove("registration");
                    fragmentManagerSimplifier.remove("authorisation");
                    activity.setDrawerLock(false);
                } else {
                    // TODO(matthew.nekirov@gmail.com): get string from R.strings
                    Snackbar
                            .make(getView(), "Please, fill all fields!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        return view;
    }

}
