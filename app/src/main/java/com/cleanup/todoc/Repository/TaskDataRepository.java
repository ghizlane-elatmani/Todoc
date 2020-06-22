package com.cleanup.todoc.Repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    //CREATE
    public void createTask(Task task){
        taskDao.insertTask(task);
    }

    //DELETE
    public void deleteTask(long id){
        taskDao.deleteTask(id);
    }

    //GET TASK
    public LiveData<Task> getTask(long id){
        return taskDao.getTask(id);
    }

    //GET ALL TASKS
    public LiveData<List<Task>> getAllTasks(){
        return taskDao.getAllTasks();
    }

}
