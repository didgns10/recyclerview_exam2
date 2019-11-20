package com.example.recyclerview_exam2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recyclerview_exam2.R;
import com.example.recyclerview_exam2.model.ItemData;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA= "extra_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tv_title = findViewById(R.id.tv_detail_title);
        TextView tv_day = findViewById(R.id.tv_detail_day);
        TextView tv_content = findViewById(R.id.tv_detail_content);

        ItemData itemData = getIntent().getParcelableExtra(EXTRA_DATA);

        tv_title.setText(itemData.getTitle());
        tv_day.setText(itemData.getDay());
        tv_content.setText(itemData.getContent());
    }
}
