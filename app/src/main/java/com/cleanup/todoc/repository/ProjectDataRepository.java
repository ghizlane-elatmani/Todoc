package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // GET ALL PROJECTS
    public LiveData<List<Project>> getAllProjects(){
        return projectDao.getAllProjects();
    }

}
