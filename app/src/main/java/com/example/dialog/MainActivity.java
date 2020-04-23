package com.example.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shengqf.dialog.CustomDialog;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private CustomDialog mDialog01, mDialog02, mDialog03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        findViewById(R.id.btn01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog01();
            }
        });
    }

    private void showDialog01() {
        mDialog01 = new CustomDialog.Builder(mContext)
                .setContentView(R.layout.app_layout_dialog_01)
                .setWidth(dp2px(300))
                .setText(R.id.tips_tv, "你确定中午不吃饭吗？你确定中午不吃饭吗？你确定中午不吃饭吗？你确定中午不吃饭吗？")
                .setRoundCorner(dp2px(10))
                .setAnimations(R.style.dialog_from_bottom_anim)
                .setOnClickListener(R.id.cancel_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog01.dismiss();
                    }
                })
                .setOnClickListener(R.id.confirm_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog01.dismiss();
                        Toast.makeText(mContext, "btn01", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public int dp2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
