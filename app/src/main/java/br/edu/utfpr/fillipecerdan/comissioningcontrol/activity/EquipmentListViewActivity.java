package br.edu.utfpr.fillipecerdan.comissioningcontrol.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.edu.utfpr.fillipecerdan.comissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentEntity;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentStatus;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.model.EquipmentType;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.ActivityStarter;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.EquipmentAdapter;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Misc;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Startable;
import br.edu.utfpr.fillipecerdan.comissioningcontrol.utils.Targetable;

public class EquipmentListViewActivity extends AppCompatActivity {
    private ListView listViewEquipments;
    private final ArrayList<EquipmentEntity> equipments = new ArrayList<>();

    private static Toast toast = null;

    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent resultIntent = result.getData();
                        if (resultIntent == null) return;

                        EquipmentEntity resultEquipment = (EquipmentEntity) resultIntent.getSerializableExtra(Misc.KEY_EQUIPMENT);
                        if (resultEquipment == null) return;
                        String newName = resultEquipment.getTag();

                        String lastName = resultIntent.getStringExtra(Misc.KEY_RENAME);
                        if (lastName == null) lastName = newName;

                        BaseAdapter adapter = ((BaseAdapter) listViewEquipments.getAdapter());

                        int lastNamePos = Misc.NOT_FOUND;
                        int newNamePos = Misc.NOT_FOUND;


                        for (EquipmentEntity e : equipments) {

                            if(e.getTag().equals(lastName)) lastNamePos = equipments.indexOf(e);
                            if(e.getTag().equals(newName)) newNamePos = equipments.indexOf(e);

                            if (lastNamePos != -1 && newNamePos != -1) break;

                        }

                        // If none of the names exists, add a new entry
                        if (newNamePos == Misc.NOT_FOUND && lastNamePos == Misc.NOT_FOUND) {
                            equipments.add(resultEquipment);
                            adapter.notifyDataSetChanged();
                            return;
                        }

                        // If both names already exists and are not the same entry,
                        // update last entry maintaining last name;
                        if (newNamePos != Misc.NOT_FOUND && newNamePos != lastNamePos) {
                            if (toast != null) toast.cancel();
                            toast = Toast.makeText(getApplicationContext(),
                                    getString(R.string.msgDuplicateEquipmentName),
                                    Toast.LENGTH_SHORT);
                            toast.show();
                            resultEquipment.setTag(lastName);
                        }

                        // Update entry
                        equipments.set(lastNamePos, resultEquipment);
                        adapter.notifyDataSetChanged();


                    }

                }
            });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_view,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuListAdd) switchToEdit(null);
        else if (item.getItemId() == R.id.menuListAbout) switchToAbout(null);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list_view);

        listViewEquipments = findViewById(R.id.listViewEquipments);

        listViewEquipments.setLongClickable(true);

        //Todo: Remove comment to add items to list
         getEquipmentsFromResources();

        populateListViewWithEquipments(listViewEquipments, equipments);

        listViewEquipments.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        EquipmentEntity item = (EquipmentEntity) listViewEquipments.getItemAtPosition(position);

                        if (toast != null)
                            toast.cancel();  // Cancel previous toast to show new message.

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
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        EquipmentEntity item = (EquipmentEntity) listViewEquipments.getItemAtPosition(position);

                        if (item.getType() == EquipmentType.INVALID) {
                            if (toast != null)
                                toast.cancel(); // Cancel previous toast to show new message.
                            toast.makeText(getApplicationContext(),
                                    getString(R.string.msgInvalidEquipmentTryAgain), Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        EquipmentEditActivity.start(new ActivityStarter()
                                .setContext(getApplicationContext())
                                .setIntent(new Intent(getApplicationContext(), EquipmentEditActivity.class)
                                        .putExtra(Misc.KEY_EQUIPMENT, item))
                                .setLauncher(launcher));

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
        ArrayList<EquipmentEntity> result = equipments;

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

    public void switchToAbout(View view) {
            AppInfoActivity.start(new ActivityStarter()
                .setContext(getApplicationContext())
                );
    }

    public void switchToEdit(View view){
        EquipmentEditActivity.start(new ActivityStarter()
                .setContext(getApplicationContext())
                .setLauncher(launcher));

    }

    public void finishMe(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    public static void start(@NonNull Startable starter) {
        // Sets target if Targetable
        if (starter instanceof Targetable)
                ((Targetable) starter).setTarget(EquipmentEditActivity.class);
        starter.start();
    }

}