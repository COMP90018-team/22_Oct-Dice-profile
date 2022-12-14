package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public String[] numList = new String[]{"1","2","3","4","5","6"};
    public boolean soundAble = true;
    public boolean vibrationSensor = true;
    public boolean lightSensor = true;
//    Button startButton;
    private String selectParameter = "1";
    Intent intent;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.spinner_dicenum);//初始化控件
        ArrayAdapter<String>adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,numList);//建立Adapter并且绑定数据源
//第一个参数表示在哪个Activity上显示，第二个参数是系统下拉框的样式，第三个参数是数组。
        spinner.setAdapter(adapter);//绑定Adapter到控件
        //监听spinner选中的参数并赋值，通过intent进行传参至下一个页面
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectParameter = numList[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(this); // calling onClick() method
        Button settingButton = (Button) findViewById(R.id.setting_btn);
        settingButton.setOnClickListener(this);


        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                 result -> {
                         // There are no request codes
                         Log.i("TEST", "Testing RESULT back");
                         Intent data = result.getData();
//                         Bundle extras = data.getExtras();
                         if (data != null) {
                             // _______________________新_____________________________
                             Bundle extras = data.getExtras();
                             //______________________________________________________
                             soundAble = extras.getBoolean("soundAble");
                             vibrationSensor = extras.getBoolean("vibrationSensor");
                             lightSensor = extras.getBoolean("lightSensor");
                             Log.i("TEST", "Testing" + Boolean.toString(soundAble));
                         }
                     }

                );

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                // do your code
                System.out.println("Clicked!");
                Intent intent = new Intent(MainActivity.this,DiceGameActivity.class);
                intent.putExtra("selectParameter",selectParameter);
                intent.putExtra("lightSensor",lightSensor);
                intent.putExtra("vibrationSensor",vibrationSensor);
                intent.putExtra("soundAble",soundAble);
                startActivity(intent);
                break;
            case R.id.setting_btn:
                // do your code
                System.out.println("setting Clicked!");
                intent = new Intent(MainActivity.this,SettingsActivity.class);
                intent.putExtra("lightSensor",lightSensor);
                intent.putExtra("vibrationSensor",vibrationSensor);
                intent.putExtra("soundAble",soundAble);
                someActivityResultLauncher.launch(intent);
                break;

            default:
                break;
        }
    }
}

