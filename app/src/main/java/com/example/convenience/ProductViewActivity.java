package com.example.convenience;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProductViewActivity extends AppCompatActivity {
    String ip = "121.165.116.253";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        ImageView pimage1 = findViewById(R.id.iv_1);
        TextView pid= findViewById(R.id.tv_1);
        TextView pname=findViewById(R.id.tv_2);
        TextView pprice=findViewById(R.id.tv_3);
        TextView pdetail=findViewById(R.id.tv_4);
        TextView prdate=findViewById(R.id.tv_5);

        Intent intent = getIntent();
        Glide.with(ProductViewActivity.this)
                .load("http://" + ip + ":8080/Convenience/img/"+intent.getStringExtra("pimage1"))
                .into(pimage1);
        pid.setText(intent.getStringExtra("pid"));
        pname.setText(intent.getStringExtra("pname"));
        pprice.setText(intent.getStringExtra("pprice"));
        pdetail.setText(intent.getStringExtra("pdetail"));
        prdate.setText(intent.getStringExtra("prdate"));


    }
}