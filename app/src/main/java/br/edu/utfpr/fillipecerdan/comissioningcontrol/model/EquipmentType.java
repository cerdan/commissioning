package br.edu.utfpr.fillipecerdan.comissioningcontrol.model;

import androidx.annotation.NonNull;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.util.App;

public enum EquipmentType {
    INVALID(R.string.lblLstEquipmentTypeInvalid),
    VALVE(R.string.lblLstEquipmentTypeValve),
    DSV(R.string.lblLstEquipmentTypeDSV),
    MOTOR(R.string.lblLstEquipmentTypeMotor),
    FC(R.string.lblLstEquipmentTypeFC),
    ANALOG_IN(R.string.lblLstEquipmentTypeAI),
    DIGITAL_IN(R.string.lblLstEquipmentTypeDI);



    private int textId;

    EquipmentType(int textId) {
        this.textId = textId;
    }

    @NonNull
    @Override
    public String toString() {
        return App.getContext().getString(this.textId);
    }

}
