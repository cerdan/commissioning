package br.edu.utfpr.fillipecerdan.comissioningcontrol.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;

public class MainActivity extends AppCompatActivity {
    private EditText txtEquipmentTag;
    private EditText txtCommissioningMessage;
    private Spinner spnEquipmentType;
    private RadioGroup rdGrpEquipmentStatus;
    private CheckBox chkAcceptOutOfSpecification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEquipmentTag = findViewById(R.id.txtEquipmentTag);
        txtCommissioningMessage = findViewById(R.id.txtCommissioningMessage);
        spnEquipmentType = findViewById(R.id.spnEquipmentType);
        rdGrpEquipmentStatus = findViewById(R.id.rdGrpEquipmentStatus);
        chkAcceptOutOfSpecification = findViewById(R.id.chkAcceptedOutOfSpecification);
    }

    public void saveEquipment(View view) {
        if (!validateEquipmentAction()) return;
        //TODO implement entity save;
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

    public boolean validateEquipmentAction() {

        // Validate Equipment TAG
        if (txtEquipmentTag == null || txtEquipmentTag.getText().toString().trim().length() == 0) {
            Toast.makeText(this, String.format(getString(R.string.msgFieldRequired), getString(R.string.lblEquipmentTag)), Toast.LENGTH_SHORT).show();
            txtEquipmentTag.requestFocus();
            return false;
        }
        // Validate Equipment Status
        if (findViewById(rdGrpEquipmentStatus.getCheckedRadioButtonId()) == null) {
            Toast.makeText(this, String.format(getString(R.string.msgFieldRequired), getString(R.string.lblRadioGroupEquipmentStatus)), Toast.LENGTH_SHORT).show();
            rdGrpEquipmentStatus.requestFocus();
            return false;
        }
        // Validate Commissioning message if equipment status is not ok
        if (rdGrpEquipmentStatus.getCheckedRadioButtonId() == R.id.radioEquipmentNOK &&
                (txtCommissioningMessage == null || txtCommissioningMessage.getText().toString().trim().length() == 0)) {
            Toast.makeText(this, String.format(getString(R.string.msgFieldRequired), getString(R.string.lblCommissioningMessage)), Toast.LENGTH_SHORT).show();
            txtCommissioningMessage.requestFocus();
            return false;
        }

        return true;
    }
}