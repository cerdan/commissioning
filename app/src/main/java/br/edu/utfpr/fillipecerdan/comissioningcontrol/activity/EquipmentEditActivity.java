package br.edu.utfpr.fillipecerdan.comissioningcontrol.activity;

import static br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.ValidationHelper.isValid;

import android.app.Activity;
import android.content.Intent;
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

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentEntity;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentStatus;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentType;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Misc;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Startable;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Targetable;

public class EquipmentEditActivity extends AppCompatActivity {
    private EditText txtEquipmentTag;
    private EditText txtCommissioningMessage;
    private Spinner spnEquipmentType;
    private RadioGroup rdGrpEquipmentStatus;
    private CheckBox chkAcceptOutOfSpecification;

    private static EquipmentEntity equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_edit);

        setTitle(getResources().getString(R.string.lblStringEdit));

        txtEquipmentTag = findViewById(R.id.txtEquipmentTag);
        txtCommissioningMessage = findViewById(R.id.txtCommissioningMessage);
        spnEquipmentType = findViewById(R.id.spnEquipmentType);
        rdGrpEquipmentStatus = findViewById(R.id.rdGrpEquipmentStatus);
        chkAcceptOutOfSpecification = findViewById(R.id.chkAcceptedOutOfSpecification);


        equipment = (EquipmentEntity) getIntent()
                .getSerializableExtra(Misc.KEY_EQUIPMENT);

        if (equipment != null) copyEquipmentToView(equipment);
        else equipment = new EquipmentEntity();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuEditClear)       clearEquipment(null);
        else if (itemId == R.id.menuEditSave)   saveEquipment(null);
        else if (itemId == R.id.menuEditReturn)   finishMe(null);

        return true;
    }

    public void saveEquipment(View view) {
        if (!validateEquipmentAction()) return;

        String lastName = equipment.getTag();

        copyViewToEquipment(equipment);

        equipment.setLastChange();

        Toast.makeText(this, getString(R.string.msgEquipmentSaved), Toast.LENGTH_SHORT).show();

        // Set result and finish
        setResult(Activity.RESULT_OK, (new Intent())
                .putExtra(Misc.KEY_EQUIPMENT, equipment)
                .putExtra(Misc.KEY_RENAME, lastName));
        finish();
    }

    public void clearEquipment(View view) {
        // Clear views of the form
        Misc.clearViews(findViewById(R.id.actEquipmentEditRoot));   //Workaround to get the root view
                                                                   // from the menu context

        // Set focus to first element
        txtEquipmentTag.requestFocus();

        // Display toast
        Toast.makeText(this, R.string.msgFieldsCleared, Toast.LENGTH_SHORT).show();

    }

    public void copyEquipmentToView(EquipmentEntity e){
        txtEquipmentTag.setText(e.getTag());
        txtCommissioningMessage.setText(e.getComment());
        spnEquipmentType.setSelection(e.getType().ordinal());
        rdGrpEquipmentStatus.check(rdGrpEquipmentStatus.getChildAt(e.getStatus().ordinal()).getId());
        chkAcceptOutOfSpecification.setChecked(e.getAcceptedOutOfSpecification());
    }

    public void copyViewToEquipment(EquipmentEntity e){
        e.setTag(txtEquipmentTag.getText().toString());
        e.setComment(txtCommissioningMessage.getText().toString());
        e.setType(EquipmentType.values()[spnEquipmentType.getSelectedItemPosition()]);
        int rdSelectedId = rdGrpEquipmentStatus.indexOfChild(
                rdGrpEquipmentStatus.findViewById(
                        rdGrpEquipmentStatus.getCheckedRadioButtonId()));
        e.setStatus(EquipmentStatus.values()[rdSelectedId]);
        e.setAcceptedOutOfSpecification(chkAcceptOutOfSpecification.isChecked());
    }

    public boolean validateEquipmentAction() {
        Function<String,String> createMsg = (input)->{
            return String.format(getString(R.string.msgFieldRequired), input);
        };

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
            Toast.makeText(this, createMsg.apply(getString(R.string.lblCommissioningMessage)), Toast.LENGTH_SHORT).show();
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
            ((Targetable) starter).setTarget(EquipmentEditActivity.class);
        starter.start();
    }
}