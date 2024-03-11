package br.edu.utfpr.fillipecerdan.commissioningcontrol.activity;

import static br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.ValidationHelper.isValid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Equipment;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.EquipmentStatus;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.EquipmentType;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.persistence.AppDatabase;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.persistence.EquipmentDAO;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.App;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Misc;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Startable;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Targetable;

public class EquipmentEditActivity extends AppCompatActivity {
    private EditText txtEquipmentTag;
    private EditText txtCommissioningMessage;
    private Spinner spnEquipmentType;
    private RadioGroup rdGrpEquipmentStatus;
    private CheckBox chkAcceptOutOfSpecification;
    private long projectId;
    private static Equipment equipment;
    private boolean suggestFields;
    private EquipmentType lastEquipmentType;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferences();

        setContentView(R.layout.activity_equipment_edit);

        txtEquipmentTag = findViewById(R.id.txtEquipmentTag);
        txtCommissioningMessage = findViewById(R.id.txtCommissioningMessage);
        spnEquipmentType = findViewById(R.id.spnEquipmentType);
        rdGrpEquipmentStatus = findViewById(R.id.rdGrpEquipmentStatus);
        chkAcceptOutOfSpecification = findViewById(R.id.chkAcceptedOutOfSpecification);


        projectId = getIntent().getLongExtra(App.KEY_PROJECT, App.NOT_FOUND);

        if (projectId == App.NOT_FOUND){
            Misc.displayWarning(this, R.string.msgInvalidProjectIdTryAgain,
                    (dialog, which) -> finishMe(null));
        }

        long equipmentId = getIntent().getLongExtra(App.KEY_EQUIPMENT, App.NOT_FOUND);

        if (equipmentId != App.NOT_FOUND) {
            setTitle(getResources().getString(R.string.lblStringEdit));
            AsyncTask.execute(() -> {
                equipment = AppDatabase.getInstance().equipmentDAO().findById(equipmentId);
                if (equipment == null) {
                    Misc.displayWarning(this, R.string.msgItemNotFound,
                            (dialog, which) -> finishMe(null)
                    );
                    return;
                }

                EquipmentEditActivity.this.runOnUiThread(() -> copyEquipmentToView(equipment));
            });
        } else {
            setTitle(getResources().getString(R.string.lblStringAdd));
            equipment = new Equipment();
            if (this.suggestFields) spnEquipmentType.setSelection(lastEquipmentType.ordinal());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        menu.findItem(R.id.menuEditSuggestFields).setChecked(suggestFields);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuEditClear)       clearEquipment(null);
        else if (itemId == R.id.menuEditSave)   saveEquipment(null);
        else if (itemId == android.R.id.home)   finishMe(null);
        else if (itemId == R.id.menuEditSuggestFields) setPreferredSuggestion(suggestFields = !suggestFields);

        return true;
    }

    public void saveEquipment(View view) {
        if (!validateEquipmentAction()) return;

        AsyncTask.execute(() -> {
            if (!upsertItem()) return;

            // Set result and finish
            setResult(Activity.RESULT_OK);
            if (suggestFields) setPreferredType();
            finish();
        });
    }

    public void clearEquipment(View view) {
        // Clear views of the form
        Misc.clearViews(findViewById(R.id.actEquipmentEditRoot));   //Workaround to get the root view
                                                                   // from the menu context

        // Set focus to first element
        txtEquipmentTag.requestFocus();

        // Display toast
       showToast(R.string.msgFieldsCleared);

    }

    public void copyEquipmentToView(Equipment e){
        txtEquipmentTag.setText(e.getTag());
        txtCommissioningMessage.setText(e.getComment());
        spnEquipmentType.setSelection(e.getType().ordinal());
        rdGrpEquipmentStatus.check(rdGrpEquipmentStatus.getChildAt(e.getStatus().ordinal()).getId());
        chkAcceptOutOfSpecification.setChecked(e.getAcceptedOutOfSpec());
    }

    public Equipment copyViewToEquipment(){
        Equipment newEquipment = new Equipment();

        newEquipment.setDesc("");
        newEquipment.setTag(txtEquipmentTag.getText().toString());
        newEquipment.setComment(txtCommissioningMessage.getText().toString());
        newEquipment.setType(EquipmentType.values()[spnEquipmentType.getSelectedItemPosition()]);
        int rdSelectedId = rdGrpEquipmentStatus.indexOfChild(
                rdGrpEquipmentStatus.findViewById(
                        rdGrpEquipmentStatus.getCheckedRadioButtonId()));
        newEquipment.setStatus(EquipmentStatus.values()[rdSelectedId]);
        newEquipment.setAcceptedOutOfSpec(chkAcceptOutOfSpecification.isChecked());

        return newEquipment;

    }

    public boolean validateEquipmentAction() {
        Function<String,String> createMsg = (input)->String.format(getString(R.string.msgFieldRequired), input);

        // Validate Equipment TAG
        if (!isValid(txtEquipmentTag,
              createMsg.apply(getString(R.string.lblEquipmentTag))))
            return false;

        // Validate Equipment Status
        if (!isValid(rdGrpEquipmentStatus,
                createMsg.apply(getString(R.string.lblRadioGroupEquipmentStatus))))
            return false;

        // Validate Commissioning message if equipment status is not ok
        if (rdGrpEquipmentStatus.getCheckedRadioButtonId() == R.id.radioEquipmentNOK &&
                !isValid(txtCommissioningMessage)) {
            showToast(createMsg.apply(getString(R.string.lblCommissioningMessage)));
            return false;
        }

        return true;
    }

    public void finishMe(View view){
        setResult(Activity.RESULT_CANCELED);
        if(suggestFields) setPreferredType();
        finish();
    }
    public static void start(@NonNull Startable starter) {
        // Sets target if Targetable
        if (starter instanceof Targetable)
            ((Targetable) starter).setTarget(EquipmentEditActivity.class);
        starter.start();
    }

    private void getPreferences(){
        SharedPreferences shared = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE);

        this.suggestFields = shared.getBoolean(App.KEY_PREF_SUGGEST_TYPE, App.PREF_SUGGEST_TYPE_DEFAULT);

        int lastTypeInt = shared.getInt(App.KEY_PREF_LAST_TYPE, App.PREF_LAST_TYPE_DEFAULT);
        this.lastEquipmentType = EquipmentType.values()[lastTypeInt];

    }

    private void setPreferredSuggestion(boolean suggestFields){
        SharedPreferences shared = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putBoolean(App.KEY_PREF_SUGGEST_TYPE, suggestFields);

        editor.apply();

        this.suggestFields = suggestFields;

    }

    private void setPreferredType(){
        int position = spnEquipmentType.getSelectedItemPosition();

        SharedPreferences shared = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(App.KEY_PREF_LAST_TYPE, position);

        editor.apply();

        this.lastEquipmentType = EquipmentType.values()[position];
    }

    public boolean upsertItem() {
        EquipmentDAO dao = AppDatabase.getInstance().equipmentDAO();

        String oldValue = equipment.getTag();

        Equipment equipmentFromView = copyViewToEquipment();
        equipmentFromView.setLastChange(equipment.getLastChange());
        equipmentFromView.setId(equipment.getId());
        equipmentFromView.setProjectId(equipment.getProjectId());

        if(equipment.equals(equipmentFromView)) {
            showToast(R.string.msgNoChanges);
            return false;
        }

        equipment = equipmentFromView;

        String newValue = equipment.getTag();
        if (!newValue.equals(oldValue)) {
            // If tag has changed, check for duplicates
            if (dao.projectHasTag(projectId, newValue)) {
                showToast(R.string.msgDuplicateEquipmentName);

                equipment.setTag(oldValue);
                txtEquipmentTag.requestFocus();
                Misc.log(oldValue);
                if(oldValue.trim().length() > 0){
                    txtEquipmentTag.setText(oldValue);
                    txtEquipmentTag.setSelection(oldValue.length());
                }
                return false;
            }
        }

        equipment.setLastChange();
        equipment.setProjectId(projectId);

        // Update entry
        dao.upsert(equipment);

        showToast(R.string.msgEquipmentSaved);

        return true;
    }

    private void showToast(int resId) {
        EquipmentEditActivity.this.runOnUiThread(() -> {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(getApplicationContext(),
                    resId,
                    Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private void showToast(String msg) {
        EquipmentEditActivity.this.runOnUiThread(() -> {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(getApplicationContext(),
                    msg,
                    Toast.LENGTH_SHORT);
            toast.show();
        });
    }
}