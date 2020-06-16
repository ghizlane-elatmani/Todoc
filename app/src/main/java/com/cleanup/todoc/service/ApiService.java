package com.cleanup.todoc.service;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public interface ApiService {

    List<Project> getAllProjects();
    void addTask(Task task);
    void deleteTask(Task task);
    List<Task> sortAZTask();
    List<Task> sortZATask();
    List<Task> sortRecentTask();
    List<Task> sortOldTask();
}
