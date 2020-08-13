package com.onlinesortoutadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class AddQueActivity extends AppCompatActivity {
    private EditText question ;
    private RadioGroup options;
    private LinearLayout answers;
    private Button uploadBtn;
    private String Cat , SubCat;
    private int setNo;


    private RadioButton b1 ,b2,b3,b4;
    private EditText e1,e2,e3,e4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_que);

        question  = findViewById(R.id.editText4);
        options = findViewById(R.id.options);
        answers = findViewById(R.id.answers);

        ////////// to clear previous data

        b1 = findViewById(R.id.radio_button1);
        b2 = findViewById(R.id.radio_button2);
        b3 = findViewById(R.id.radio_button3);
        b4 = findViewById(R.id.radio_button4);

        e1 = findViewById(R.id.OpA);
        e2 = findViewById(R.id.OpB);
        e3 = findViewById(R.id.OpC);
        e4 = findViewById(R.id.OpD);



        /////////
        uploadBtn =  findViewById(R.id.btn_upload);

        Cat = getIntent().getStringExtra("Cat");
        SubCat = getIntent().getStringExtra("SubCat");
        setNo = getIntent().getIntExtra("setNo",0);

        if(setNo==0){
            finish();return;
        }

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(question.getText().toString().isEmpty()){
                    question.setError("Abe Question to daal");return;
                }
                upload();
            }
        });



    }

    private void upload() {
        int correct = -1 ;
        for(int i=0;i<options.getChildCount();i++){

            EditText ans = (EditText)answers.getChildAt(i);

            if(ans.getText().toString().isEmpty()){
                ans.setError("Daal Ye Option !!");return;
            }

            RadioButton btn  = (RadioButton)options.getChildAt(i);
            if(btn.isChecked()){
                correct = i;
                break;
            }
        }
        if(correct==-1){
            Toast.makeText(AddQueActivity.this,"Ans N Tik Kr Ab",Toast.LENGTH_LONG).show();
            return;
        }

        HashMap<String , Object> map = new HashMap<>();
        map.put("correctAns",((EditText)answers.getChildAt(correct)).getText().toString());
        map.put("optionA",((EditText)answers.getChildAt(0)).getText().toString());
        map.put("optionB",((EditText)answers.getChildAt(1)).getText().toString());
        map.put("optionC",((EditText)answers.getChildAt(2)).getText().toString());
        map.put("optionD",((EditText)answers.getChildAt(3)).getText().toString());
        map.put("question",question.getText().toString());
        map.put("setNo",setNo);


        FirebaseDatabase.getInstance().getReference()
                .child("SETS").child(Cat).child(SubCat).child("questions").child(UUID.randomUUID().toString())
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddQueActivity.this,"Store Hogo Database M",Toast.LENGTH_LONG).show();
                    question.getText().clear();
                    e1.getText().clear();
                    e2.getText().clear();
                    e3.getText().clear();
                    e4.getText().clear();
                    b1.setChecked(false);
                    b3.setChecked(false);
                    b2.setChecked(false);
                    b4.setChecked(false);

                }
                else{
                    Toast.makeText(AddQueActivity.this,"Kuni Dlyo Database M",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
