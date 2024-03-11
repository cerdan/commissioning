package br.edu.utfpr.fillipecerdan.commissioningcontrol.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.Project;

@Dao
public interface ProjectDAO {
    @Insert
    long insert(Project project);

    @Delete
    int delete(Project project);

    @Update
    int update(Project project);

    @Upsert
    long upsert(Project project);

    @Query("SELECT * FROM project WHERE id = :id")
    Project findById(long id);

    @Query("SELECT * FROM project")
    List<Project> findAll();

    @Query("SELECT * FROM project WHERE code LIKE '%'||:code||'%'")
    List<Project> findByCode(String code);

    @Query("SELECT EXISTS(SELECT * FROM project WHERE code = :code)")
    boolean hasCode(String code);

    @Query("SELECT * FROM project WHERE name LIKE '%'||:name||'%'")
    Project findByName(String name);

    @Query("SELECT * FROM project WHERE customerName LIKE '%'||:customerName||'%'")
    Project findByCustomerName(String customerName);

    @Query("SELECT code FROM project WHERE id = :id")
    String getCodeFromId(long id);
}
