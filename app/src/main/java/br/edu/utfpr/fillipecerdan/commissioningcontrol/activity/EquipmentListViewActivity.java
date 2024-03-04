package br.edu.utfpr.fillipecerdan.commissioningcontrol.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.appcompat.view.ActionMode;

import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.R;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Equipment;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.EquipmentStatus;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.EquipmentType;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.ActivityStarter;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.App;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.EquipmentAdapter;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Misc;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Startable;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Targetable;

public class EquipmentListViewActivity extends AppCompatActivity {
    private ListView listViewEquipments;
    private final ArrayList<Equipment> equipments = new ArrayList<>();
    private static Toast toast = null;
    private ActionMode actionMode;
    private View selectedView;
    private int listOrder;

    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_context_list_view_item, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Equipment equipment = (Equipment) mode.getTag();

            int itemId = item.getItemId();
            if (itemId == R.id.menuContextListViewItemEdit) switchToEditWithEquipment(equipment);
            else if (itemId == R.id.menuContextListViewItemDelete) deleteItemFromEquipments(equipment);

            mode.finish();

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (selectedView != null) {
                selectedView.setSelected(false);
                selectedView.setBackgroundColor(Color.TRANSPARENT);
                selectedView = null;
            }

            actionMode = null;

            listViewEquipments.setEnabled(true);
        }
    };
    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent resultIntent = result.getData();
                        if (resultIntent == null) return;

                        Equipment resultEquipment = (Equipment) resultIntent.getSerializableExtra(App.KEY_EQUIPMENT);
                        if (resultEquipment == null) return;
                        String newName = resultEquipment.getTag();

                        String lastName = resultIntent.getStringExtra(App.KEY_RENAME);
                        if (lastName == null) lastName = newName;

                        upsertItemInEquipments(resultEquipment,lastName,newName);

                        // Update listView
                        updateListViewWithResource(listViewEquipments,equipments);

                    }

                }
            });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_equipments,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.menuListEqpAdd) switchToEdit(null);
        else if (itemId == R.id.menuListEqpAbout) switchToAbout(null);
        else if (itemId == R.id.menuListEqpViewProjects) switchToProjects(null);
        else if (itemId == R.id.menuListEqpOrderDefault) setPreferredOrder(App.PREF_ORDER_DEFAULT);
        else if (itemId == R.id.menuListEqpOrderTagOnly) setPreferredOrder(App.PREF_ORDER_TAG_ONLY);
        else if (itemId == R.id.menuListEqpOrderNOK) setPreferredOrder(App.PREF_ORDER_NOK_FIRST);
        else if (itemId == R.id.menuListEqpOrderOK) setPreferredOrder(App.PREF_ORDER_OK_FIRST);
        else if (itemId == R.id.menuListEqpOrderLastChange) setPreferredOrder(App.PREF_ORDER_LAST_CHANGE);
        else if (itemId == R.id.menuListEqpSwitch) item.getSubMenu().findItem(R.id.menuListEqpViewEquipments).setChecked(true);

        item.setChecked(true);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item = null;

        switch (listOrder){
            case App.PREF_ORDER_DEFAULT:
                item = menu.findItem(R.id.menuListEqpOrderDefault);
                break;
            case App.PREF_ORDER_TAG_ONLY:
                item = menu.findItem(R.id.menuListEqpOrderTagOnly);
                break;
            case App.PREF_ORDER_NOK_FIRST:
                item = menu.findItem(R.id.menuListEqpOrderNOK);
                break;
            case App.PREF_ORDER_OK_FIRST:
                item = menu.findItem(R.id.menuListEqpOrderOK);
                break;
            case App.PREF_ORDER_LAST_CHANGE:
                item = menu.findItem(R.id.menuListEqpOrderLastChange);
                break;
        }

        if (item != null) item.setChecked(true);

        return true;//super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferences();

        setContentView(R.layout.activity_equipment_list_view);

        setTitle(getResources().getString(R.string.lblStringListEquipment));

        listViewEquipments = findViewById(R.id.listViewEquipments);

        listViewEquipments.setLongClickable(true);

        //Todo: Remove comment to add items to list
        getEquipmentsFromResources();

        populateListViewWithEquipments(listViewEquipments, equipments);

        listViewEquipments.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewEquipments.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Equipment item = (Equipment) listViewEquipments.getItemAtPosition(position);

                        if (actionMode != null) return;


                        selectedView = view;
                        selectedView.setSelected(true);
                        selectedView.setBackgroundColor(MaterialColors.getColor(getApplicationContext(),
                                android.R.attr.colorActivatedHighlight, Color.CYAN));
                        actionMode = startSupportActionMode(mActionModeCallback);

                        actionMode.setTag(item);

                        listViewEquipments.setEnabled(false);

                    }
                }

        );

        listViewEquipments.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Equipment item = (Equipment) listViewEquipments.getItemAtPosition(position);
                        switchToEditWithEquipment(item);

                        return true;
                    }
                }

        );

    }

    private void populateListViewWithEquipments(ListView list, List<Equipment> resource) {
        updateListViewWithResource(null, resource);
        EquipmentAdapter equipmentAdapter = new EquipmentAdapter(this.getApplicationContext(), resource);
        list.setAdapter(equipmentAdapter);
    }

    private List<Equipment> getEquipmentsFromResources() {
        List<Equipment> result = equipments;

        String[] tags = getResources().getStringArray(R.array.resEquipmentTAG);
        int[] types = getResources().getIntArray(R.array.resEquipmentType);
        int[] statuses = getResources().getIntArray(R.array.resEquipmentStatus);
        String[] comments = getResources().getStringArray(R.array.resEquipmentComment);
        int[] acceptances = getResources().getIntArray(R.array.resEquipmentAcceptedOutOfSpec);
        String[] lastChange = getResources().getStringArray(R.array.resEquipmentChangeDate);

        EquipmentType[] equipmentTypes = EquipmentType.values();  // Get ENUM values as an array
        EquipmentStatus[] equipmentStatuses = EquipmentStatus.values(); // Get ENUM values as an array

        for (int i = 0; i < tags.length; i++) {
            result.add(new Equipment(
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

    public void finishMe(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    public static void start(@NonNull Startable starter) {
        // Sets target if Targetable
        if (starter instanceof Targetable)
            ((Targetable) starter).setTarget(EquipmentListViewActivity.class);
        starter.start();
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

    public void switchToEditWithEquipment(Equipment item){
        if (item.getType() == EquipmentType.INVALID) {
            if (toast != null)
                toast.cancel(); // Cancel previous toast to show new message.
            toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.msgInvalidEquipmentTryAgain), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        EquipmentEditActivity.start(new ActivityStarter()
                .setContext(getApplicationContext())
                .setIntent(new Intent(getApplicationContext(), EquipmentEditActivity.class)
                        .putExtra(App.KEY_EQUIPMENT, item))
                .setLauncher(launcher));

    }

    public void switchToProjects(View view){
        ProjectListViewActivity.start(new ActivityStarter()
                .setContext(getApplicationContext())
                .setLauncher(launcher));
    }

    public void deleteItemFromEquipments(Equipment equipment){
        DialogInterface.OnClickListener onClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    equipments.remove(equipment);
                    updateListViewWithResource(listViewEquipments, equipments);
                    break;
                default:
                    break;
            }
        };

        String msg = String.format(getString(R.string.lblStringRemoveItemConfirmationMsg), equipment.getTag());
        Misc.confirmAction(this, msg, onClickListener);
    }

    public void upsertItemInEquipments(Equipment equipment, String lastName, String newName){
        int lastNamePos = App.NOT_FOUND;
        int newNamePos = App.NOT_FOUND;

        for (Equipment e : equipments) {

            String tag = e.getTag();
            if(tag.equals(lastName)) lastNamePos = equipments.indexOf(e);
            if(tag.equals(newName)) newNamePos = equipments.indexOf(e);

            if (lastNamePos != -1 && newNamePos != -1) break;

        }

        // If none of the names exists, add a new entry
        if (newNamePos == App.NOT_FOUND && lastNamePos == App.NOT_FOUND) {
            equipments.add(equipment);
            return;
        }

        // If both names already exists and are not the same entry,
        // update last entry maintaining last name;
        if (newNamePos != App.NOT_FOUND && newNamePos != lastNamePos) {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.msgDuplicateEquipmentName),
                    Toast.LENGTH_SHORT);
            toast.show();
            equipment.setTag(lastName);
        }

        // Update entry
        equipments.set(lastNamePos, equipment);
    }

    private void updateListViewWithResource(ListView listView,List<Equipment> resource) {

        switch (listOrder){
            case App.PREF_ORDER_TAG_ONLY:
                Collections.sort(resource, Equipment.BY_TAG);
                break;
            case App.PREF_ORDER_NOK_FIRST:
                Collections.sort(resource, Equipment.BY_STATUS_NOK);
                break;
            case App.PREF_ORDER_OK_FIRST:
                Collections.sort(resource, Equipment.BY_STATUS_OK);
                break;
            case App.PREF_ORDER_LAST_CHANGE:
                Collections.sort(resource, Equipment.BY_LAST_CHANGE);
                break;
            case App.PREF_ORDER_DEFAULT:
            default:
                Collections.sort(resource);
                break;
        }

        if (listView != null) ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

    }

    private void getPreferences(){
        SharedPreferences shared = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE);

        listOrder = shared.getInt(App.KEY_PREF_ORDER_EQUIPMENT, App.PREF_ORDER_DEFAULT);
    }

    private void setPreferredOrder(int listOrder){
        SharedPreferences shared = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(App.KEY_PREF_ORDER_EQUIPMENT, listOrder);

        editor.commit();

        this.listOrder = listOrder;

        updateListViewWithResource(listViewEquipments, equipments);

    }
}