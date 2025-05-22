package com.mirea.turbinava.employeebd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Superhero {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String realName;
    public String power;
    public String origin;
}
