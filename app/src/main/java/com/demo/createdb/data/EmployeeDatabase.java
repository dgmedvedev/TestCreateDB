package com.demo.createdb.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Employee.class}, version = 5, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {
    private static EmployeeDatabase database;
    private static final String DB_NAME = "employeesRoom.db";
    private static final Object LOCK = new Object();

    public static EmployeeDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, EmployeeDatabase.class, DB_NAME)
                        .allowMainThreadQueries() // ИСКЛЮЧИТЕЛЬНО для тестирования БД
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return database;
    }

    public abstract EmployeesDao employeesDao();
}