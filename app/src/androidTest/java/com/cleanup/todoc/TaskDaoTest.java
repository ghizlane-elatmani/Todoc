package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;
    private Project[] projects = Project.getAllProjects();
    private final Task TASK = new Task(projects[0].getId(), "Appeler le client", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();

        this.database.projectDao().insertProjects(projects);
    }

    @After
    public void closeDb() throws Exception {
        this.database.close();
    }

    @Test
    public void test_InsertTask() throws InterruptedException{
        //insert the task
        this.database.taskDao().insertTask(TASK);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertFalse(tasks.isEmpty());
    }

    @Test
    public void test_InsertAndDeleteTask() throws InterruptedException {
        //insert the task
        this.database.taskDao().insertTask(TASK);

        //delete the task
        this.database.taskDao().deleteTask(TASK.getId());

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertFalse(tasks.contains(TASK));
    }
}
