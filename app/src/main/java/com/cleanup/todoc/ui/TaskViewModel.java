package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    //DATA
    @Nullable
    private LiveData<List<Project>> projects;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init() {
        if (projects == null)
            projects = projectDataSource.getAllProjects();
    }

    // -------------
    // FOR PROJECT
    // -------------
    public LiveData<List<Project>> getAllProjects(){
        return projects;
    }

    // -------------
    // FOR TASK
    // -------------

    public LiveData<List<Task>> getAllTasks() {
        return taskDataSource.getAllTasks();
    }

    public void createTask(final Task task) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                taskDataSource.createTask(task);
            }
        };
        executor.execute(runnable);
    }

    public void deleteTask(final long taskId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                taskDataSource.deleteTask(taskId);
            }
        };
        executor.execute(runnable);
    }


}
