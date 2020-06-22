package com.cleanup.todoc.Repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    //GET
    public LiveData<List<Project>> getAllProjects(){
        return projectDao.getAllProjects();
    }

}
