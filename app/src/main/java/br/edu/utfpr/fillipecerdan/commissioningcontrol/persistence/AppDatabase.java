package br.edu.utfpr.fillipecerdan.commissioningcontrol.persistence;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Equipment;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Project;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.App;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.utils.MyTypeConverters;

@Database(entities = {Equipment.class, Project.class}, version = 1, exportSchema = false)
@TypeConverters({MyTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract EquipmentDAO equipmentDAO();
    public abstract ProjectDAO projectDAO();
    private static AppDatabase instance;

    public static final AppDatabase getInstance(){

        // Double Checked Lock | Singleton
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(App.getContext(),
                                    AppDatabase.class,
                                    "commissioning.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}
