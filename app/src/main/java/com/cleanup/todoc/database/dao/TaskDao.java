package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    // Create a new task
    @Insert
    long insertTask(Task task);

    // Delete a task
    @Query("DELETE FROM Task WHERE id = :id")
    int deleteTask(long id);

    // Get all tasks
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();

}
