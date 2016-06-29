package edu.fjbatresv.android.socialphotomap.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.fjbatresv.android.socialphotomap.R;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapApp;
import edu.fjbatresv.android.socialphotomap.login.LoginPresenter;
import edu.fjbatresv.android.socialphotomap.login.events.LoginEvent;
import edu.fjbatresv.android.socialphotomap.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @Bind(R.id.txtEmail)
    EditText txtEmail;
    @Bind(R.id.txtPassword)
    EditText txtPassword;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btnSignin)
    Button btnSignIn;
    @Bind(R.id.btnSignup)
    Button btnSignUp;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    @Inject
    LoginPresenter presenter;
    @Inject
    SharedPreferences sharedPreferences;
    private SocialPhotoMapApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        app = (SocialPhotoMapApp) getApplication();
        setupInjection();
        presenter.onCreate();
        presenter.validateLogin(null, null);
    }

    private void setupInjection() {
        app.getLoginComponent(this).inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnSignup)
    @Override
    public void handleSingUp() {
        //startActivity(new Intent(this, RegisterActivity.class));
        presenter.registerNewUser(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @OnClick(R.id.btnSignin)
    @Override
    public void handleSingIn() {
        presenter.validateLogin(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class)
                .putExtra(MainActivity.signedUser, txtEmail.getText().toString()));
    }

    @Override
    public void loginError(String error) {
        txtPassword.setText(null);
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        txtPassword.setError(msgError);
    }

    @Override
    public void setUserEmail(String email) {
        if (email != null){
            sharedPreferences.edit().putString(app.getEmailKey(), email).commit();
        }
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, getString(R.string.login_notice_message_useradded), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void newUserError(String error) {
        txtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        txtPassword.setError(msgError);
    }


    private void setInputs(boolean status){
        txtEmail.setEnabled(status);
        txtPassword.setEnabled(status);
        btnSignIn.setEnabled(status);
        btnSignUp.setEnabled(status);
    }
}
