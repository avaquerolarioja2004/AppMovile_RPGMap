package com.rpgmap.database;

import androidx.room.*;
import androidx.room.RoomDatabase;

import com.rpgmap.database.dao.EnemyDAO;
import com.rpgmap.database.dao.FurnitureDAO;
import com.rpgmap.database.dao.ObjectDAO;
import com.rpgmap.database.dao.RoomDAO;
import com.rpgmap.database.entities.Enemy_Table;
import com.rpgmap.database.entities.Furniture_Table;
import com.rpgmap.database.entities.Object_Table;
import com.rpgmap.database.entities.Room_Table;

@Database(entities = {Enemy_Table.class, Furniture_Table.class, Object_Table.class, Room_Table.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract EnemyDAO enemyDAO();
    public abstract FurnitureDAO furnitureDAO();
    public abstract ObjectDAO objectDAO();
    public abstract RoomDAO roomDAO();
}
