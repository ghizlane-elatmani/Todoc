package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    //Insert project
    @Insert
    void insertProjects(Project... projects);

    // Get all projects
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProjects();

}
