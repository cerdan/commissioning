package br.edu.utfpr.fillipecerdan.comissioningcontrol.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentEntity;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentStatus;

public class EquipmentAdapter extends BaseAdapter {
    private Context context;
    private List<EquipmentEntity> equipments;
    private NumberFormat numberFormat;

    private static class EquipmentHolder {
        public TextView txtAdptEqpTag;
        public TextView txtAdptEqpType;
        public TextView txtAdptEqpStatus;
        public TextView txtAdptEqpOoS;
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
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_equipment_adapter, parent, false);
            holder = new EquipmentHolder();

            holder.txtAdptEqpTag = view.findViewById(R.id.txtAdptEqpTag);
            holder.txtAdptEqpType = view.findViewById(R.id.txtAdptEqpType);
            holder.txtAdptEqpStatus = view.findViewById(R.id.txtAdptEqpStatus);
            holder.txtAdptEqpOoS = view.findViewById(R.id.txtAdptEqpOoS);

            view.setTag(holder);

        } else {
            holder = (EquipmentHolder) view.getTag();
        }

        EquipmentEntity equipment = equipments.get(position);
        holder.txtAdptEqpTag.setText(equipment.getTag());
        holder.txtAdptEqpType.setText(equipment.getType().toString());
        holder.txtAdptEqpStatus.setText(equipment.getStatus().toString());
        holder.txtAdptEqpOoS.setText(equipment.getAcceptedOutOfSpecification() ?
                context.getString(R.string.msgAcceptedOutOfSpec) : "");

        int color;
        if (equipment.getAcceptedOutOfSpecification()) {
            color = context.getResources().getColor(R.color.yellow50);
        } else if (equipment.getStatus() == EquipmentStatus.NOK) {
            color = context.getResources().getColor(R.color.red50);
        } else {
            color = context.getResources().getColor(R.color.green50);
        }

        view.setBackgroundColor(color);

        return view;
    }
}
