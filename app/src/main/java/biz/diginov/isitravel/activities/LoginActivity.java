package biz.diginov.isitravel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import biz.diginov.isitravel.R;
import biz.diginov.isitravel.network.response.LoginResponse;
import biz.diginov.isitravel.network.ApiService;
import biz.diginov.isitravel.network.RetrofitBuilder;
import biz.diginov.isitravel.SessionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class LoginActivity extends BaseActivity {

    public static final String TAG = "LoginActivity";

    @BindView(R.id.til_username)
    TextInputLayout til_username;
    @BindView(R.id.til_password)
    TextInputLayout ti_code;

    ApiService service;
    Call<LoginResponse> call;
    AwesomeValidation validator;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(ApiService.class);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        sessionManager = SessionManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        sessionManager.endSession();
        setupValidator();
    }

    @OnClick(R.id.btn_login)
    void login() {
        final String username = til_username.getEditText().getText().toString();
        final String code = ti_code.getEditText().getText().toString();

        til_username.setError(null);
        ti_code.setError(null);
        validator.clear();

        if (validator.validate()) {
            call = service.login(username, code);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getResponseCode().equals("1")) {
                            Toasty.success(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                            sessionManager.startSession(username);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toasty.error(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    Toasty.error(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            });
        }
    }

    public void setupValidator() {
        validator.addValidation(this, R.id.til_username, RegexTemplate.NOT_EMPTY, R.string.err_username);
        validator.addValidation(this, R.id.til_password, "[a-zA-Z0-9]{4,}", R.string.err_password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
    }
}
