package tiendhph30203.poly.projectdatdoan;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class manhinhchaoActivity extends AppCompatActivity {

    ImageView ivBbkg;
    long startDelayMillis = 2000;
    ImageView ivlogo;
    TextView ivname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao);
        ivBbkg = findViewById(R.id.ivBbkg);
        ivlogo = findViewById(R.id.ivlogo);
        ivname = findViewById(R.id.ivname);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Bắt đầu chạy animation của ivBbkg
                ivBbkg.animate().translationY(9000).setDuration(2000);
                ivname.animate().translationY(900).setDuration(2000);
                ivlogo.animate().translationY(-9000).setDuration(2000);
                // Sau khi animation của ivBbkg kết thúc
                // Chuyển sang màn hình tiếp theo
                ivBbkg.animate().setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(manhinhchaoActivity.this, manhinhchao2Activity.class);
                        startActivity(intent);
                        finish(); // Đóng activity hiện tại để không quay lại khi nhấn nút Back
                    }
                });
            }
        }, startDelayMillis);
    }
}