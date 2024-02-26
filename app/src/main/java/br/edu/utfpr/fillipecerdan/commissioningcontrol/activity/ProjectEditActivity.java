package br.edu.utfpr.fillipecerdan.commissioningcontrol.activity;

import static br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.ValidationHelper.isValid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Project;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.App;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Misc;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Startable;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Targetable;

public class ProjectEditActivity extends AppCompatActivity {
    public static final int MIN_YEAR = 1970;
    public static final int MAX_YEAR = 2300;
    private static Project project;
    private EditText txtProjectCode;
    private EditText txtProjectName;
    private EditText txtProjectCustomer;
    private EditText txtProjectLocation;
    private EditText txtProjectStartYear;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        menu.findItem(R.id.menuEditPreferences).setVisible(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menuEditPreferences).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuEditClear)       clear(null);
        else if (itemId == R.id.menuEditSave)   save(null);
        else if (itemId == android.R.id.home)   finishMe(null);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project_edit);

        txtProjectCode = findViewById(R.id.txtProjectCode);
        txtProjectName = findViewById(R.id.txtProjectName);
        txtProjectCustomer = findViewById(R.id.txtProjectCustomer);
        txtProjectLocation = findViewById(R.id.txtProjectLocation);
        txtProjectStartYear = findViewById(R.id.txtProjectStartYear);

        project = (Project) getIntent().getSerializableExtra(App.KEY_PROJECT);

        if (project != null) {
            copyToView(project);
            setTitle(getString(R.string.lblStringEdit));
        }else{
            project = new Project();
            setTitle(getString(R.string.lblStringAdd));
        }

    }

    public void save(View view) {
        if (!validateFields()) return;

        String lastCode = project.getCode();

        Project projectFromView = copyFromView();

        if(project.equals(projectFromView)) {
            finishMe(null);
            return;
        };

        project = projectFromView;

        Toast.makeText(this, getString(R.string.msgEquipmentSaved), Toast.LENGTH_SHORT).show();

        // Set result and finish
        setResult(Activity.RESULT_OK, (new Intent())
                .putExtra(App.KEY_PROJECT, project)
                .putExtra(App.KEY_RENAME, lastCode));
        finish();
    }

    public void clear(View view) {
        // Clear views of the form
        Misc.clearViews(findViewById(R.id.actProjectEditRoot));   //Workaround to get the root view
        // from the menu context

        // Set focus to first element
        txtProjectCode.requestFocus();

        // Display toast
        Toast.makeText(this, R.string.msgFieldsCleared, Toast.LENGTH_SHORT).show();

    }

    private void copyToView(Project p){
        txtProjectCode.setText(p.getCode());
        txtProjectName.setText(p.getName());
        txtProjectCustomer.setText(p.getCustomerName());
        txtProjectLocation.setText(p.getLocation());
        txtProjectStartYear.setText(((Integer)p.getStartYear()).toString());
    }

    private Project copyFromView(){
        Project newProject = new Project();
        newProject.setCode(txtProjectCode.getText().toString());
        newProject.setName(txtProjectName.getText().toString());
        newProject.setCustomerName(txtProjectCustomer.getText().toString());
        newProject.setLocation(txtProjectLocation.getText().toString());

        newProject.setStartYear(Integer.parseInt(txtProjectStartYear.getText().toString()));

        return newProject;
    }

    private boolean validateFields(){
        Function<String,String> createMsg = (input)->{
            return String.format(getString(R.string.msgFieldRequired), input);
        };

        // Validate Project Code
        if (!isValid(txtProjectCode,
                createMsg.apply(getString(R.string.lblProjectCode))))
            return false;

        // Validate Project Name
        if (!isValid(txtProjectName,
                createMsg.apply(getString(R.string.lblProjectName))))
            return false;


        // Validate Customer Name
        if (!isValid(txtProjectCustomer,
                createMsg.apply(getString(R.string.lblProjectCustomer))))
            return false;

        // Validate Project Location
        if (!isValid(txtProjectLocation,
                createMsg.apply(getString(R.string.lblProjectLocation))))
            return false;

        // Validate Start Year
        if (!isValid(txtProjectCode,
                createMsg.apply(getString(R.string.lblProjectCode))))
            return false;

        int startYear = Integer.parseInt(txtProjectStartYear.getText().toString());
        if (startYear < MIN_YEAR || startYear > MAX_YEAR){
            Toast.makeText(getApplicationContext(),
                    String.format(getString(R.string.lblStringYearBetweenMINMAX), MIN_YEAR, MAX_YEAR),
                    Toast.LENGTH_SHORT)
                    .show();
            txtProjectStartYear.requestFocus();
            return false;
        }


        return true;
    }

    public void finishMe(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    public static void start(@NonNull Startable starter) {
        // Sets target if Targetable
        if (starter instanceof Targetable)
            ((Targetable) starter).setTarget(ProjectEditActivity.class);
        starter.start();
    }


}