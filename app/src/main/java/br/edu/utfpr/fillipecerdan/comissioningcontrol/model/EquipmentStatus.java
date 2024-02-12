package br.edu.utfpr.fillipecerdan.comissioningcontrol.model;

import androidx.annotation.NonNull;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.util.App;

public enum EquipmentStatus {
    OK(R.string.lblRadioEquipmentOK),
    NOK(R.string.lblRadioEquipmentNOK)
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
