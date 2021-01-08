package com.example.pristine_approval_portal.sql_lite_process.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pristine_approval_portal.sql_lite_process.dao.ExceptionDao;
import com.example.pristine_approval_portal.sql_lite_process.dao.UserDao;
import com.example.pristine_approval_portal.sql_lite_process.model.ExceptionTableModel;
import com.example.pristine_approval_portal.sql_lite_process.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, ExceptionTableModel.class}, version = 1, exportSchema = false)
public abstract class PristineDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ExceptionDao exceptionDao();

    private static PristineDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static PristineDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (PristineDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PristineDatabase.class, "pristine_seed_database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}