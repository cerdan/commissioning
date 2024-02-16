package br.edu.utfpr.fillipecerdan.comissioningcontrol.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.color.MaterialColors;

import java.text.NumberFormat;
import java.util.List;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentEntity;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentStatus;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentType;

public class EquipmentAdapter extends BaseAdapter {
    private Context context;
    private List<EquipmentEntity> equipments;
    private NumberFormat numberFormat;

    private static class EquipmentHolder {
        public TextView txtAdptEqpTag;
        public TextView txtAdptEqpType;
        public TextView txtAdptEqpStatus;
        public TextView txtAdptEqpOoS;

        public ImageView imgAdaptEqpIndicator;
    }

    public EquipmentAdapter(Context context, List<EquipmentEntity> equipments) {
        super();
        this.context = context;
        this.equipments = equipments;
    }

    @Override
    public int getCount() {
        return equipments.size();
    }

    @Override
    public Object getItem(int position) {
        return equipments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        EquipmentHolder holder;

        context.getTheme().applyStyle(R.style.Theme_ComissioningControl,false);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_equipment_adapter, parent, false);
            holder = new EquipmentHolder();

            holder.txtAdptEqpTag = view.findViewById(R.id.txtAdptEqpTag);
            holder.txtAdptEqpType = view.findViewById(R.id.txtAdptEqpType);
            holder.txtAdptEqpStatus = view.findViewById(R.id.txtAdptEqpStatus);
            holder.txtAdptEqpOoS = view.findViewById(R.id.txtAdptEqpOoS);
            holder.imgAdaptEqpIndicator = view.findViewById(R.id.imgAdaptEqpIndicator);

            view.setTag(holder);

        } else {
            holder = (EquipmentHolder) view.getTag();
        }

        EquipmentEntity equipment = equipments.get(position);
        holder.txtAdptEqpTag.setText(equipment.getTag());
        holder.txtAdptEqpType.setText(equipment.getType().toString());
        holder.txtAdptEqpStatus.setText(equipment.getStatus().toString());
        holder.txtAdptEqpOoS.setText(equipment.getAcceptedOutOfSpecification() ?
                context.getString(R.string.lblStringAcceptedOutOfSpec) : "");
        holder.imgAdaptEqpIndicator.setImageResource(R.drawable.circle);

        GradientDrawable d = (GradientDrawable) holder.imgAdaptEqpIndicator.getDrawable().mutate();
        d.setColor(getColor(equipment));

        return view;
    }

    private int getColor(EquipmentEntity equipment) {
        int color;
        if (equipment.getType() == EquipmentType.INVALID) {
            color = MaterialColors.getColor(context, R.attr.colorBackgroundEquipmentInvalid, Color.BLACK);
        } else if (equipment.getAcceptedOutOfSpecification()) {
            color = MaterialColors.getColor(context, R.attr.colorBackgroundEquipmentOoS, Color.BLACK);
        } else if (equipment.getStatus() == EquipmentStatus.NOK) {
            color = MaterialColors.getColor(context, R.attr.colorBackgroundEquipmentNOK, Color.BLACK);
        } else if (equipment.getStatus() == EquipmentStatus.OK) {
            color = MaterialColors.getColor(context, R.attr.colorBackgroundEquipmentOK, Color.BLACK);
        } else {
            color = MaterialColors.getColor(context, R.attr.colorBackgroundEquipmentNotDefined, Color.BLACK);
        }
        return color;
    }
}
