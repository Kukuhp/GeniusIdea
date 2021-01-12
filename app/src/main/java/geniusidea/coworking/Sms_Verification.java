package geniusidea.coworking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shuhart.stepview.StepView;

import java.util.concurrent.TimeUnit;

import geniusidea.coworking.LocalDatabase.Data_User_Login;
import geniusidea.coworking.LocalDatabase.Data_User_Token;
import geniusidea.coworking.Model.RegisterForm;
import geniusidea.coworking.Model.RegisterLoginResponse;
import geniusidea.coworking.Model.ResponseHomeMenu;
import geniusidea.coworking.Service.Retrofit.APIClient;
import geniusidea.coworking.Service.Retrofit.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sms_Verification extends AppCompatActivity {
    private int currentStep = 0;
    LinearLayout layout1,layout2,layout3;
    StepView stepView;

    private static String uniqueIdentifier = null;
    private static final String UNIQUE_ID = "UNIQUE_ID";
    private static final long ONE_HOUR_MILLI = 60*60*1000;

    private static final String TAG = "FirebasePhoneNumAuth";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth firebaseAuth;

    private String phoneNumber;
    private Button sendCodeButton;
    private Button verifyCodeButton;
    private Button signOutButton;
    private Button button3;

    private EditText phoneNum;
    private PinView verifyCodeET;
    private TextView phonenumberText;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog mDialog;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms__verification);

        mAuth = FirebaseAuth.getInstance();

        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        sendCodeButton = (Button) findViewById(R.id.submit1);
        verifyCodeButton = (Button) findViewById(R.id.submit2);
        button3 = (Button) findViewById(R.id.submit3);
        firebaseAuth = FirebaseAuth.getInstance();
        phoneNum = (EditText) findViewById(R.id.phonenumber);
        verifyCodeET = (PinView) findViewById(R.id.pinView);
        phonenumberText = (TextView) findViewById(R.id.phonenumberText);

        mDialog = new ProgressDialog(this);

        stepView = findViewById(R.id.step_view);
        stepView.setStepsNumber(15);
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {

            }
        });
        stepView.go(0, true);
        layout1.setVisibility(View.VISIBLE);

        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneNum.getText().toString();
                phonenumberText.setText("+62"+phoneNumber);

                if (TextUtils.isEmpty(phoneNumber)) {
                    phoneNum.setError("Enter a Phone Number");
                    phoneNum.requestFocus();
                } else if (phoneNumber.length() < 9) {
                    phoneNum.setError("Please enter a valid phone");
                    phoneNum.requestFocus();
                } else {
                    if (currentStep < stepView.getStepCount() - 1) {
                        currentStep++;
                        stepView.go(currentStep, true);
                    } else {
                        stepView.done(true);
                    }
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+62"+phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            Sms_Verification.this,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks
                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                mDialog.setMessage("Verifying...");
                mDialog.show();
                mDialog.setCancelable(false);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
              //  super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
                mResendToken = forceResendingToken;
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };

        verifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verificationCode = verifyCodeET.getText().toString();
                if(verificationCode.isEmpty()){
                    Toast.makeText(Sms_Verification.this,"Enter verification code",Toast.LENGTH_SHORT).show();
                }else {

                    mDialog.setMessage("Verifying...");
                    mDialog.show();
                    mDialog.setCancelable(false);

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                } else {
                    stepView.done(true);
                }
                mDialog.setMessage("Creating...");
                mDialog.show();
                mDialog.setCancelable(false);
                userRegister();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        finish();
//                    }
//                },3000);
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    mDialog.dismiss();
                    if (currentStep < stepView.getStepCount() - 1) {
                        currentStep++;
                        stepView.go(currentStep, true);
                    } else {
                        stepView.done(true);
                    }
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.VISIBLE);
                    // ...
                }   else {
                    mDialog.dismiss();
                    Toast.makeText(Sms_Verification.this,"Something wrong",Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
            }
        });
    }

    public void userRegister(){
        String deviceToken = ""+ FirebaseInstanceId.getInstance().getToken();
        String nama = getIntent().getStringExtra("nama");
        String email = getIntent().getStringExtra("email");
        String perusahaan = getIntent().getStringExtra("perusahaan");
        String jabatan = getIntent().getStringExtra("jabatan");
        String alamat = getIntent().getStringExtra("alamat");
        String password1 = getIntent().getStringExtra("password");
        APIService service = APIClient.getClient().create(APIService.class);
        RegisterForm registerForm = new RegisterForm("register",email,alamat,"-",jabatan,nama,perusahaan,password1,deviceToken,phoneNumber);
        Call<RegisterLoginResponse> userCall = service.Register("register",nama,perusahaan,jabatan,"-",alamat,password1,deviceToken,email,phonenumberText.getText().toString());
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
                        startActivity(new Intent(Sms_Verification.this,Utama.class));
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
}
