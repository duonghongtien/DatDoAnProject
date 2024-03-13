package tiendhph30203.poly.projectdatdoan;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class manhinhchao2Activity extends AppCompatActivity {


    Button btnLogin;
    Button btnSigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao2);
        btnLogin = findViewById(R.id.btnLogin);
        btnSigin = findViewById(R.id.btnSigin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(manhinhchao2Activity.this, manhinhlogin.class));
            }
        });

        btnSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(manhinhchao2Activity.this, manhinhdangky.class));
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Tạo hộp thoại AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(manhinhchao2Activity.this);
                builder.setMessage("Bạn có muốn thoát không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

}

