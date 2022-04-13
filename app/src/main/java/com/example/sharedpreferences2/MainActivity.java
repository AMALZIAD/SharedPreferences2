package com.example.sharedpreferences2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBarSound ;
    private SeekBar seekBarBrightness;

    private RadioGroup radioGroupDiffLevel;
    private RadioButton radioButtonEasy;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonHard;

    private Button buttonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBarBrightness= findViewById(R.id.seekBar_brightness);
        seekBarSound= findViewById(R.id.seekBar_sound);

        seekBarBrightness.setMax(100);
        seekBarSound.setMax(100);

        radioGroupDiffLevel= findViewById(R.id.radioGroup_diffLevel);
        radioButtonEasy=findViewById(R.id.radioButton_easy);

        radioButtonMedium = findViewById(R.id.radioButton_medium);
        radioButtonHard=findViewById(R.id.radioButton_hard);

        buttonSave = findViewById(R.id.button_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.doSave(view);
            }
        });

        // Load saved game setting.
        loadGameSetting();
    }
    private void loadGameSetting()  {
        // instanciuate object with file name gameSetting mode private only this app can acces
        SharedPreferences sharedPreferences = getSharedPreferences("gameSetting", Context.MODE_PRIVATE);

        if(sharedPreferences!= null) {
            // get data from setting file
            int brightness = sharedPreferences.getInt("brightness", 90);
            int sound = sharedPreferences.getInt("sound",95);
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.radioButton_medium);
            // set the prefernce on the app
            seekBarSound.setProgress(sound);
            seekBarBrightness.setProgress(brightness);
            radioGroupDiffLevel.check(checkedRadioButtonId);

        } else // if no preferences are store or the file dosnet exist
             {
            radioGroupDiffLevel.check(R.id.radioButton_medium);
            Toast.makeText(this,"Use the default game setting",Toast.LENGTH_LONG).show();
        }

    }
    // Called when user click to Save button.
    public void doSave(View view)  {
        // The created file can only be accessed by the calling application

        SharedPreferences sharedPreferences= getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
       // instanciate the editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //set data using editor
        editor.putInt("brightness", seekBarBrightness.getProgress());
        editor.putInt("sound", seekBarSound.getProgress());

        // Checked RadioButton ID.
        int checkedRadioButtonId = radioGroupDiffLevel.getCheckedRadioButtonId();

        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);

        // Save.
        editor.apply();

        Toast.makeText(this,"Game Setting saved!",Toast.LENGTH_LONG).show();
    }
}