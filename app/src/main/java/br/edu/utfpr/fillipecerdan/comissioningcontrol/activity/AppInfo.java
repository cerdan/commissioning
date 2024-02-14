package br.edu.utfpr.fillipecerdan.comissioningcontrol.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;

public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
    }

    public void finishAbout(View view){
        finish();
    }
}