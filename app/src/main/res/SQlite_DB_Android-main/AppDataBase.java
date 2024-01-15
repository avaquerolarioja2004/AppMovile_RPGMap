package com.example.heroquest.db_sqlite;

import androidx.room.*;
import androidx.room.RoomDatabase;

import com.example.heroquest.db_sqlite.dao.EnemyDAO;
import com.example.heroquest.db_sqlite.dao.FurnitureDAO;
import com.example.heroquest.db_sqlite.dao.ObjectDAO;
import com.example.heroquest.db_sqlite.dao.RoomDAO;
import com.example.heroquest.db_sqlite.entities.Enemy_Table;
import com.example.heroquest.db_sqlite.entities.Furniture_Table;
import com.example.heroquest.db_sqlite.entities.Object_Table;
import com.example.heroquest.db_sqlite.entities.Room_Table;

@Database(entities = {Enemy_Table.class, Furniture_Table.class, Object_Table.class, Room_Table.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract EnemyDAO enemyDAO();
    public abstract FurnitureDAO furnitureDAO();
    public abstract ObjectDAO  objectDAO();
    public abstract RoomDAO roomDAO();
}
