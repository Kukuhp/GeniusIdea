package geniusidea.coworking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import geniusidea.coworking.LocalDatabase.Data_User_Login;
import geniusidea.coworking.LocalDatabase.Data_User_Token;
import geniusidea.coworking.Model.RegisterLoginResponse;
import geniusidea.coworking.Service.Retrofit.APIClient;
import geniusidea.coworking.Service.Retrofit.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText login_email,login_pass;
    ImageView hidePassword,showPaswrod;
    CardView cardLogin,cardRegister;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mDialog = new ProgressDialog(this);
        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_pass);

        hidePassword = findViewById(R.id.hidePassword);
        hidePassword.setOnClickListener(this);
        showPaswrod = findViewById(R.id.showPaswrod);
        showPaswrod.setOnClickListener(this);

        cardLogin = findViewById(R.id.cardLogin);
        cardLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        cardRegister = findViewById(R.id.cardRegister);
        cardRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (Login.this,Register.class));
            }
        });
    }

    public void userLogin(){
        mDialog.setMessage("Creating...");
        mDialog.show();
        mDialog.setCancelable(false);
        APIService service = APIClient.getClient().create(APIService.class);
        geniusidea.coworking.Model.Login login = new geniusidea.coworking.Model.Login(login_email.getText().toString(),login_pass.getText().toString());
        Call<RegisterLoginResponse> userCall = service.Login("login",login_email.getText().toString(),login_pass.getText().toString());
        userCall.enqueue(new Callback<RegisterLoginResponse>() {
            @Override
            public void onResponse(Call<RegisterLoginResponse> call, Response<RegisterLoginResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equals("success")){
                        Data_User_Login data_user_login = Data_User_Login.findById(Data_User_Login.class,1L);
                        data_user_login.Login = true;
                        data_user_login.save();
                        Data_User_Token data_user_token = Data_User_Token.findById(Data_User_Token.class,1L);
                        data_user_token.token = response.body().getId();
                        data_user_token.save();
                        startActivity(new Intent(Login.this,Utama.class));
                    }else {
                        Toast.makeText(getApplicationContext(),""+response.body().getStatus(),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RegisterLoginResponse> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(),"failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.showPaswrod:
                login_pass.setInputType(InputType.TYPE_CLASS_TEXT);
                login_pass.setSelection(login_pass.getText().length());
             //   login_pass.setTypeface(typeface);
                showPaswrod.setVisibility(View.GONE);
                hidePassword.setVisibility(View.VISIBLE);
                break;

            case R.id.hidePassword:
                login_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                login_pass.setSelection(login_pass.getText().length());
               // login_pass.setTypeface(typeface);
                hidePassword.setVisibility(View.GONE);
                showPaswrod.setVisibility(View.VISIBLE);
                break;
        }
    }
}
