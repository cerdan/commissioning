package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

public class ActivityStarter implements Startable, Targetable<ActivityStarter> {
    private Context context;
    private Intent intent;
    private ActivityResultLauncher<Intent> launcher;
    private Class<?> target;


    public void start() {
        if (target == null) return;
        if (context == null) return;
        if (intent == null) intent = new Intent(context, target);
        if (launcher == null) context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        else launcher.launch(intent);

    }

    public ActivityStarter setContext(Context context) {
        this.context = context;
        return this;
    }

    public ActivityStarter setIntent(Intent intent) {
        this.intent = intent;
        return this;
    }

    public ActivityStarter setLauncher(ActivityResultLauncher<Intent> launcher) {
        this.launcher = launcher;
        return this;
    }

    public ActivityStarter setTarget(Class<?> target) {
        this.target = target;
        return this;
    }


}
