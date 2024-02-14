package br.edu.utfpr.fillipecerdan.comissioningcontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentEntity;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentStatus;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentType;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.EquipmentAdapter;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Misc;

public class EquipmentListViewActivity extends AppCompatActivity {
    private ListView listViewEquipments;
    private ArrayList<EquipmentEntity> equipments;

    private static Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list_view);
        listViewEquipments = findViewById(R.id.listViewEquipments);

        listViewEquipments.setLongClickable(true);

        populateListViewWithEquipments(listViewEquipments, getEquipmentsFromResources());

        listViewEquipments.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        EquipmentEntity item = (EquipmentEntity) listViewEquipments.getItemAtPosition(position);

                        if (toast != null) toast.cancel();  // Cancel previous toast to show new message.

                        toast = Toast.makeText(getApplicationContext(),
                                String.format(getString(R.string.msgEquipmentClicked),
                                        item.getTag(),
                                        item.getLastChange()),
                                Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }

        );

        listViewEquipments.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        EquipmentEntity item = (EquipmentEntity) listViewEquipments.getItemAtPosition(position);

                        if(item.getType() == EquipmentType.INVALID){
                            if (toast != null) toast.cancel(); // Cancel previous toast to show new message.
                            toast.makeText(getApplicationContext(),
                                   getString(R.string.msgInvalidEquipmentTryAgain), Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        Intent intent = new Intent(getApplicationContext(), EquipmentEditActivity.class)
                                .putExtra(Misc.KEY_EQUIPMENT, item);

                        startActivity(intent);

                        return true;
                    }
                }
        );

    }

    private void populateListViewWithEquipments(ListView list, ArrayList<EquipmentEntity> resource) {
        EquipmentAdapter equipmentAdapter = new EquipmentAdapter(this.getApplicationContext(), resource);
        list.setAdapter(equipmentAdapter);
    }

    private ArrayList<EquipmentEntity> getEquipmentsFromResources() {
        ArrayList<EquipmentEntity> result = new ArrayList<>();

        String[] tags = getResources().getStringArray(R.array.resEquipmentTAG);
        int[] types = getResources().getIntArray(R.array.resEquipmentType);
        int[] statuses = getResources().getIntArray(R.array.resEquipmentStatus);
        String[] comments = getResources().getStringArray(R.array.resEquipmentComment);
        int[] acceptances = getResources().getIntArray(R.array.resEquipmentAcceptedOutOfSpec);
        String[] lastChange = getResources().getStringArray(R.array.resEquipmentChangeDate);

        EquipmentType[] equipmentTypes = EquipmentType.values();  // Get ENUM values as an array
        EquipmentStatus[] equipmentStatuses = EquipmentStatus.values(); // Get ENUM values as an array

        for (int i = 0; i < tags.length; i++) {
            result.add(new EquipmentEntity(
                    tags[i],
                    equipmentTypes[types[i]],
                    equipmentStatuses[statuses[i]],
                    comments[i],
                    acceptances[i] == 1,
                    Misc.parseDate(lastChange[i])
            ));
        }

        return result;
    }

    public void switchToAbout(View view){
        startActivity(new Intent(getApplicationContext(), AppInfo.class));
    }
}