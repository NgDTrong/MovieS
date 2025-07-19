package app.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import app.movies.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edtName,edtPass;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        edtName=findViewById(R.id.edtName);
        edtPass=findViewById(R.id.edtPass);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().isEmpty()||edtPass.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if (edtName.getText().toString().equals("trong")||edtPass.getText().toString().equals("trong")){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Không có tài khoản", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}