package br.edu.utfpr.fillipecerdan.commissioningcontrol.model;

import androidx.annotation.NonNull;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.App;

public enum EquipmentStatus {
    OK(R.string.lblStringEquipmentOK),
    NOK(R.string.lblStringEquipmentNOK)
    ;

    private int textId;

    EquipmentStatus(int textId) {
        this.textId = textId;
    }

    @NonNull
    @Override
    public String toString() {
        return App.getContext().getString(this.textId);
    }
}
