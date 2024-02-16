package br.edu.utfpr.fillipecerdan.comissioningcontrol.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Startable;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Targetable;

public class AppInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        setTitle(getResources().getString(R.string.lblStringAbout));
    }

    public void finishMe(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    public static void start(@NonNull Startable starter) {
        // Sets target if Targetable
        if (starter instanceof Targetable)
            ((Targetable) starter).setTarget(AppInfoActivity.class);
        starter.start();
    }

}