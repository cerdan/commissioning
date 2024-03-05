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
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Project;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.persistence.AppDatabase;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.persistence.ProjectDAO;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.ActivityStarter;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.App;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Misc;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.ProjectAdapter;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Startable;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.Targetable;

public class ProjectListViewActivity extends AppCompatActivity {
    private ListView listViewProjects;
    private final List<Project> projects = new ArrayList<>();
    private static Toast toast = null;
    private ActionMode actionMode;
    private View selectedView;
    private int listOrder;

    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            mode.getMenuInflater().inflate(R.menu.menu_context_list_view_item,menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Project project = (Project) mode.getTag();

            int itemId = item.getItemId();
            if (itemId == R.id.menuContextListViewItemEdit) switchToEditWithProject(project);
            else if (itemId == R.id.menuContextListViewItemDelete) deleteItemFromProjects(project);

            mode.finish();

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(selectedView != null) {
                selectedView.setBackgroundColor(Color.TRANSPARENT);
                selectedView.setSelected(false);
                selectedView = null;
            }
            actionMode = null;
            listViewProjects.setEnabled(true);
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menuListPrjViewEquipments).setChecked(false);
        menu.findItem(R.id.menuListPrjViewProject).setChecked(true);

        MenuItem item = null;

        switch (listOrder){
            case App.PREF_ORDER_DEFAULT:
                item = menu.findItem(R.id.menuListPrjOrderDefault);
                break;
            case App.PREF_ORDER_PRJ_CUSTOMER:
                item = menu.findItem(R.id.menuListPrjCustomer);
                break;
            case App.PREF_ORDER_PRJ_LOCATION:
                item = menu.findItem(R.id.menuListPrjLocation);
                break;
            case App.PREF_ORDER_PRJ_YEAR:
                item = menu.findItem(R.id.menuListPrjYear);
                break;
        }

        if (item != null) item.setChecked(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_projects, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuListPrjAdd) switchToEdit(null);
        else if (itemId == R.id.menuListPrjAbout) switchToAbout(null);
        else if (itemId == R.id.menuListPrjViewEquipments) switchToEquipments(null);
        else if (itemId == R.id.menuListPrjOrderDefault) setPreferredOrder(App.PREF_ORDER_DEFAULT);
        else if (itemId == R.id.menuListPrjCustomer) setPreferredOrder(App.PREF_ORDER_PRJ_CUSTOMER);
        else if (itemId == R.id.menuListPrjLocation) setPreferredOrder(App.PREF_ORDER_PRJ_LOCATION);
        else if (itemId == R.id.menuListPrjYear) setPreferredOrder(App.PREF_ORDER_PRJ_YEAR);
        else if (itemId == R.id.menuListPrjSwitch) item.getSubMenu().findItem(R.id.menuListPrjViewProject).setChecked(true);

        item.setChecked(true);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferences();

        setContentView(R.layout.activity_project_list);

        setTitle(getResources().getString(R.string.lblStringListProject));

        listViewProjects = findViewById(R.id.listViewProjects);

        listViewProjects.setLongClickable(true);

        updateLocalProjectsWith(getProjectsFromDB());

        populateListViewWithResources(listViewProjects, projects);

        listViewProjects.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewProjects.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Project item = (Project) listViewProjects.getItemAtPosition(position);
                        switchToEditWithProject(item);

                    }
                }

        );

        listViewProjects.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Project item = (Project) listViewProjects.getItemAtPosition(position);

                        if (actionMode != null) return true;


                        selectedView = view;
                        selectedView.setSelected(true);
                        selectedView.setBackgroundColor(MaterialColors.getColor(getApplicationContext(),
                                android.R.attr.colorActivatedHighlight, Color.CYAN));
                        actionMode = startSupportActionMode(mActionModeCallback);

                        actionMode.setTag(item);

                        listViewProjects.setEnabled(false);

                        return true;
                    }
                }

        );

    }

    private List<Project> getProjectsFromDB() {
        ProjectDAO dao = AppDatabase.getInstance().projectDAO();
        return dao.findAll();
    }

    private void populateListViewWithResources(ListView list, List<Project> resource) {
        updateListViewWithResource(null, resource);
        ProjectAdapter projectAdapter = new ProjectAdapter(this.getApplicationContext(), resource);
        list.setAdapter(projectAdapter);
    }


    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == Activity.RESULT_OK){

                        Intent resultIntent = result.getData();
                        if (resultIntent == null) return;

                        Project resultProject = (Project) resultIntent.getSerializableExtra(App.KEY_PROJECT);
                        if (resultProject == null) return;

                        String newCode = resultProject.getCode();
                        String oldCode = resultIntent.getStringExtra(App.KEY_RENAME);
                        if (oldCode == null) oldCode = newCode;

                        upsertItemInProjects(resultProject, oldCode, newCode);

                        // Update listView
                        updateListViewWithResource(listViewProjects,getProjectsFromDB());
                    }
                }
            });


    public void finishMe(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    public static void start(@NonNull Startable starter) {
        // Sets target if Targetable
        if (starter instanceof Targetable)
            ((Targetable) starter).setTarget(ProjectListViewActivity.class);
        starter.start();
    }
    public void switchToAbout(View view) {
        AppInfoActivity.start(new ActivityStarter()
                .setContext(getApplicationContext())
        );
    }

    public void switchToEdit(View view){
        ProjectEditActivity.start(new ActivityStarter()
                .setContext(getApplicationContext())
                .setLauncher(launcher));

    }

    public void switchToEditWithProject(Project item){

        ProjectEditActivity.start(new ActivityStarter()
                .setContext(getApplicationContext())
                .setIntent(new Intent(getApplicationContext(), ProjectEditActivity.class)
                        .putExtra(App.KEY_PROJECT, item))
                .setLauncher(launcher));

    }
    public void switchToEquipments(View view){
        EquipmentListViewActivity.start(new ActivityStarter()
                .setContext(getApplicationContext())
                .setLauncher(launcher));
    }

    public void deleteItemFromProjects(Project project){

        DialogInterface.OnClickListener onClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    AppDatabase.getInstance().projectDAO().delete(project);
                    projects.remove(project);
                    updateListViewWithResource(listViewProjects, projects);
                    break;
                default:
                    break;
            }
        };

        String msg = String.format(getString(R.string.lblStringRemoveItemConfirmationMsg), project.getCode());
        Misc.confirmAction(this, msg, onClickListener);
    }


    public void upsertItemInProjects(Project project, String oldValue, String newValue){
        ProjectDAO dao = AppDatabase.getInstance().projectDAO();

        if (oldValue.trim().length() != 0 && !newValue.equals(oldValue)) {
            // If last code exist and codes are not the same entry,
            // update last entry maintaining last code;
            List<Project> results = dao.findByCode(newValue);
            if (results.size() > 0) {
                if (toast != null) toast.cancel();
                toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.msgDuplicateEquipmentName),
                        Toast.LENGTH_SHORT);
                toast.show();
                project.setCode(oldValue);
            }
        }

        // Update entry
        dao.upsert(project);
    }

    private void updateListViewWithResource(ListView listView, List<Project> resource) {

        switch (listOrder){
            case App.PREF_ORDER_PRJ_CUSTOMER:
                Collections.sort(resource, Project.BY_CUSTOMER);
                break;
            case App.PREF_ORDER_PRJ_LOCATION:
                Collections.sort(resource, Project.BY_LOCATION);
                break;
            case App.PREF_ORDER_PRJ_YEAR:
                Collections.sort(resource, Project.BY_YEAR);
                break;
            case App.PREF_ORDER_DEFAULT:
            default:
                Collections.sort(resource);
                break;
        }

        updateLocalProjectsWith(resource);

        if (listView != null) ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

    }
    private void getPreferences(){
        SharedPreferences shared = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE);

        listOrder = shared.getInt(App.KEY_PREF_ORDER_PROJECT, App.PREF_ORDER_DEFAULT);
    }
    
    private void setPreferredOrder(int listOrder){
        SharedPreferences shared = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(App.KEY_PREF_ORDER_PROJECT, listOrder);

        editor.commit();

        this.listOrder = listOrder;

        updateListViewWithResource(listViewProjects, projects);

    }

    private void updateLocalProjectsWith(List<Project> resources){
        if (resources.equals(projects)) return;

        projects.clear();
        projects.addAll(resources);
    }
}