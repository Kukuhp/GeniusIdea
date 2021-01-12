package geniusidea.coworking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

public class Register extends AppCompatActivity {
    EditText name,login_email,edPerusahaan,edJabatan,edAlamat,login_pass,retypePass;
    CardView btnRegister;
    String nama,email,perusahaan,jabatan,alamat,password1,password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        login_email = findViewById(R.id.login_email);
        edPerusahaan = findViewById(R.id.edPerusahaan);
        edJabatan = findViewById(R.id.edJabatan);
        edAlamat = findViewById(R.id.edAlamat);
        login_pass = findViewById(R.id.login_pass);
        retypePass = findViewById(R.id.retypePass);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = name.getText().toString();
                email = login_email.getText().toString();
                perusahaan = edPerusahaan.getText().toString();
                jabatan = edJabatan.getText().toString();
                alamat = edAlamat.getText().toString();
                password1 = login_pass.getText().toString();
                password2 = retypePass.getText().toString();

                if (nama.isEmpty()){
                    name.setError("Enter your full name");
                }else {
                    name.setError(null);
                }

                if (email.isEmpty()){
                    login_email.setError("Enter email address");
                }else {
                    login_email.setError(null);
                }

                if (perusahaan.isEmpty()){
                    edPerusahaan.setError("Enter Company Name");
                }else {
                    edPerusahaan.setError(null);
                }

                if (jabatan.isEmpty()){
                    edJabatan.setError("Enter Position in Company");
                }else {
                    edJabatan.setError(null);
                }

                if (alamat.isEmpty()){
                    edAlamat.setError("Enter Company Address");
                }else {
                    edAlamat.setError(null);
                }

                if (password1.isEmpty()){
                    login_pass.setError("Enter Password");
                }else {
                    login_pass.setError(null);
                }

                if (password2.isEmpty()){
                    retypePass.setError("Please retype password");
                }else {
                    retypePass.setError(null);
                }

                if (!password1.equals(password2)){
                    retypePass.setError("Please retype password corectly");
                }else {
                    if (!nama.isEmpty()&&!email.isEmpty()&&!perusahaan.isEmpty()&&!jabatan.isEmpty()&&!alamat.isEmpty()&&!password1.isEmpty()&&!password2.isEmpty()){
                        startActivity(new Intent(Register.this,Sms_Verification.class)
                        .putExtra("nama",nama)
                        .putExtra("email",email)
                        .putExtra("perusahaan",perusahaan)
                        .putExtra("jabatan",jabatan)
                        .putExtra("alamat",alamat)
                        .putExtra("password",password1));
                    }
                }
            }
        });
    }
}
