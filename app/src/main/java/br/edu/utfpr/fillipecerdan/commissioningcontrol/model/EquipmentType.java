package br.edu.utfpr.fillipecerdan.commissioningcontrol.model;

import androidx.annotation.NonNull;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.App;

public enum EquipmentType {
    VALVE(R.string.lblLstEquipmentTypeValve),
    DSV(R.string.lblLstEquipmentTypeDSV),
    MOTOR(R.string.lblLstEquipmentTypeMotor),
    FC(R.string.lblLstEquipmentTypeFC),
    ANALOG_IN(R.string.lblLstEquipmentTypeAI),
    DIGITAL_IN(R.string.lblLstEquipmentTypeDI),
    INVALID(R.string.lblLstEquipmentTypeInvalid);



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
