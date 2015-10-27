package com.example.itachi.texttospeech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextToSpeech speak;
    EditText ed1;
    TextView textview_language;
    Button b1, select_language;
    Dialog dialog;
    RadioGroup radioLanguageGroup;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = (EditText) findViewById(R.id.editText);
        b1 = (Button) findViewById(R.id.button);
        select_language = (Button) findViewById(R.id.language);
        textview_language = (TextView)findViewById(R.id.tv_lang);

        speak = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speak.setLanguage(Locale.US);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                speak.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        select_language.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.select_language);
                dialog.setTitle("Language?");
                Button bt_ok = (Button)dialog.findViewById(R.id.bt_Ok);
                radioLanguageGroup = (RadioGroup)dialog.findViewById(R.id.radioGroup);
                bt_ok.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        int selectedId = radioLanguageGroup.getCheckedRadioButtonId();
                        RadioButton selectedButton = (RadioButton) dialog.findViewById(selectedId);

                        Languages lang = Languages.valueOf((String) selectedButton.getText());

                        switch (lang){
                            case US:
                                speak.setLanguage(Locale.US);
                                textview_language.setText("US");
                                break;
                            case GERMANY:
                                speak.setLanguage(Locale.GERMANY);
                                textview_language.setText("GERMANY");
                                break;
                            case ITALY:
                                speak.setLanguage(Locale.ITALY);
                                textview_language.setText("ITALY");
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    enum Languages {
        US, GERMANY, ITALY;
    }

    public void onPause(){
        if(speak !=null){
            speak.stop();
            speak.shutdown();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}