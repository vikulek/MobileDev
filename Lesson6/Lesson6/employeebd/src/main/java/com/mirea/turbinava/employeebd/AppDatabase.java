package com.mirea.turbinava.employeebd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Superhero.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SuperheroDao superheroDao();
}
