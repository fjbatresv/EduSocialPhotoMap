package edu.fjbatresv.android.socialphotomap.register.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.fjbatresv.android.socialphotomap.R;
import edu.fjbatresv.android.socialphotomap.login.ui.LoginActivity;
import edu.fjbatresv.android.socialphotomap.register.RegisterPresenter;
import edu.fjbatresv.android.socialphotomap.register.RegisterPresenterImplementation;


public class RegisterActivity extends AppCompatActivity implements RegisterView{
    @Bind(R.id.txtEmail)
    EditText txtEmail;
    @Bind(R.id.txtPassword)
    EditText txtPassword;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btnSignup)
    Button btnSignUp;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    private RegisterPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestoy();
        super.onDestroy();
    }

     @OnClick(R.id.btnSignup)
     public void addUser(){
         if(txtEmail.getText().toString() != null && txtPassword.getText().toString() != null){
             presenter.addUser(txtEmail.getText().toString(), txtPassword.getText().toString());
         }else if(txtEmail.getText().toString() == null){
             txtEmail.setError(getString(R.string.register_error_email));
         }else if(txtPassword.getText().toString() == null){
             txtPassword.setError(getString(R.string.register_error_password));
         }
     }

    @Override
    public void toggleInputs(boolean status) {
        txtEmail.setEnabled(status);
        txtPassword.setEnabled(status);
        btnSignUp.setEnabled(status);
        btnCancel.setEnabled(status);
    }

    @Override
    public void toogleProgressBar(boolean status) {
        if(status){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnCancel)
    @Override
    public void succesSignUp() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void errorSignUp(String error) {
        txtPassword.setError(String.format(getString(R.string.register_error_signup), error));
    }
}
