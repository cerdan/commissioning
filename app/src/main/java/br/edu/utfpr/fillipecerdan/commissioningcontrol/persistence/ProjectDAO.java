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
    void delete(Project project);

    @Update
    void update(Project project);

    @Upsert
    long upsert(Project project);

    @Query("SELECT * FROM project WHERE id = :id")
    Project findById(long id);

    @Query("SELECT * FROM project")
    List<Project> findAll();

    @Query("SELECT * FROM project WHERE code LIKE '%'||:code||'%'")
    List<Project> findByCode(String code);

    @Query("SELECT * FROM project WHERE name LIKE '%'||:name||'%'")
    Project findByName(String name);
    @Query("SELECT * FROM project WHERE customerName LIKE '%'||:customerName||'%'")
    Project findByCustomerName(String customerName);
}
