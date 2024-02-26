package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Project;

public class ProjectAdapter extends BaseAdapter {
    private Context context;
    private List<Project> projects;

    public ProjectAdapter(Context context, List<Project> projects) {
        super();
        this.context = context;
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ProjectHolder holder = new ProjectHolder();

        context.getTheme().applyStyle(R.style.Theme_CommissioningControl,false);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_project_adapter, parent, false);

            holder.txtAdptPrjCode = view.findViewById(R.id.txtAdptPrjCode);
            holder.txtAdptPrjName = view.findViewById(R.id.txtAdptPrjName);
            holder.txtAdptPrjCustomer = view.findViewById(R.id.txtAdptPrjCustomer);
            holder.txtAdptPrjYear = view.findViewById(R.id.txtAdptPrjYear);
            holder.txtAdptPrjLocation = view.findViewById(R.id.txtAdptPrjLocation);

            view.setTag(holder);

        }
        else{
            holder = (ProjectHolder) view.getTag();
        }

        Project p = projects.get(position);

        holder.txtAdptPrjCode.setText(p.getCode());
        holder.txtAdptPrjName.setText(p.getName());
        holder.txtAdptPrjCustomer.setText(p.getCustomerName());
        holder.txtAdptPrjYear.setText(((Integer) p.getStartYear()).toString());
        holder.txtAdptPrjLocation.setText(p.getLocation());

        return view;
    }

    private static class ProjectHolder {
        public TextView txtAdptPrjCode;
        public TextView txtAdptPrjName;
        public TextView txtAdptPrjCustomer;
        public TextView txtAdptPrjYear;
        public TextView txtAdptPrjLocation;

    }


}