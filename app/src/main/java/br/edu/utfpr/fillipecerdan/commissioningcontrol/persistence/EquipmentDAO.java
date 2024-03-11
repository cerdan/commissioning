package br.edu.utfpr.fillipecerdan.commissioningcontrol.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Equipment;

@Dao
public interface EquipmentDAO {
    @Insert
    long insert(Equipment equipment);

    @Delete
    int delete(Equipment equipment);

    @Update
    int update(Equipment equipment);

    @Upsert
    long upsert(Equipment equipment);

    @Query("SELECT * FROM equipment WHERE id = :id")
    Equipment findById(long id);

    @Query("SELECT * FROM equipment")
    List<Equipment> findAll();

    @Query("SELECT * FROM equipment WHERE projectId = :projectId")
    List<Equipment> findByProjectId(long projectId);

    @Query("SELECT * FROM equipment WHERE tag = :tag")
    Equipment findByTag(String tag);

    @Query("SELECT * FROM equipment WHERE tag = :tag AND projectId = :projectId")
    Equipment findByTagAndProjectId(String tag, long projectId);

    @Query("SELECT EXISTS(SELECT * FROM equipment WHERE tag = :tag)")
    boolean hasTag(String tag);

    @Query("SELECT EXISTS(SELECT * FROM equipment WHERE tag = :tag AND projectId = :projectId)")
    boolean projectHasTag(long projectId, String tag);

    @Query("SELECT EXISTS(SELECT * FROM equipment WHERE projectId = :projectId)")
    boolean hasProjectId(long projectId);

}
