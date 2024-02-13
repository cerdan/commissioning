package br.edu.utfpr.fillipecerdan.comissioningcontrol.activity;

import static br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.ValidationHelper.isValid;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentEntity;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentStatus;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentType;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Misc;

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

    public void saveEquipment(View view) {
        if (!validateEquipmentAction()) return;
        copyViewToEquipment(equipment);
        Toast.makeText(this, getString(R.string.msgEquipmentSaved), Toast.LENGTH_SHORT).show();
    }

    public void clearEquipment(View view) {
        // Clear text components
        txtEquipmentTag.getText().clear();
        txtCommissioningMessage.getText().clear();

        // Clear spinner components
        spnEquipmentType.setSelection(0);

        // Clear radio buttons
        //rdGrpEquipmentStatus.check(R.id.radioEquipmentOK);
        rdGrpEquipmentStatus.clearCheck();

        // Clear checkboxes
        chkAcceptOutOfSpecification.setChecked(false);

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
}