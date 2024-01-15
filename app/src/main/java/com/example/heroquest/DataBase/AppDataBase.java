package com.example.heroquest.DataBase;

import androidx.room.*;
import androidx.room.RoomDatabase;

import com.example.heroquest.DataBase.dao.EnemyDAO;
import com.example.heroquest.DataBase.dao.FurnitureDAO;
import com.example.heroquest.DataBase.dao.ObjectDAO;
import com.example.heroquest.DataBase.dao.RoomDAO;
import com.example.heroquest.DataBase.entities.Enemy_Table;
import com.example.heroquest.DataBase.entities.Furniture_Table;
import com.example.heroquest.DataBase.entities.Object_Table;
import com.example.heroquest.DataBase.entities.Room_Table;

@Database(entities = {Enemy_Table.class, Furniture_Table.class, Object_Table.class, Room_Table.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract EnemyDAO enemyDAO();
    public abstract FurnitureDAO furnitureDAO();
    public abstract ObjectDAO objectDAO();
    public abstract RoomDAO roomDAO();
}
