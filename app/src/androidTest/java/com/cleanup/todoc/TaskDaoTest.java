package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void test_GetTask() throws InterruptedException{
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
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

    @Test
    public void test_InsertAndCompareListofTask() throws InterruptedException {
        List<Task> tasksList = new ArrayList<>();
        Task task0 = new Task(projects[0].getId(), "Appeler le client", new Date().getTime());
        Task task1 = new Task(projects[2].getId(), "Modifier la couleur des textes", new Date().getTime());
        Task task2 = new Task(projects[1].getId(), "Ajouter un header sur le site", new Date().getTime());

        //add tasks to the list
        tasksList.add(task0);
        tasksList.add(task1);
        tasksList.add(task2);

        //insert tasks
        this.database.taskDao().insertTask(task0);
        this.database.taskDao().insertTask(task1);
        this.database.taskDao().insertTask(task2);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertEquals(tasksList.size(), tasks.size());

        assertEquals(tasksList.get(0).getName(), tasks.get(0).getName());
        assertEquals(tasksList.get(1).getName(), tasks.get(1).getName());
        assertEquals(tasksList.get(2).getName(), tasks.get(2).getName());
    }
}
