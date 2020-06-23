package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile TodocDatabase INSTANCE;

    // --- DAO ---
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    // --- INSTANCE ---
    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Project[] projects = Project.getAllProjects();
                for (Project project : projects) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());
                    db.insert("project", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }
}
