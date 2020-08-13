package com.onlinesortoutadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1 ;
    private EditText et2 ;
    private EditText et3 ;
    private Button nxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et1 = findViewById(R.id.editText);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);
        nxt = findViewById(R.id.button1);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cat = et1.getText().toString();
                final String subcat = et2.getText().toString();
                final int setNo = Integer.parseInt(et3.getText().toString());


                if(cat==null || subcat==null || setNo==0){
                    Toast.makeText(MainActivity.this,"Sari Cheez Daal R",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this,AddQueActivity.class);
                    intent.putExtra("Cat",cat);
                    intent.putExtra("SubCat",subcat);
                    intent.putExtra("setNo",setNo);
                    startActivity(intent);
                }
            }
        });

    }
}
