package com.example.heroquest.DataBase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.heroquest.DataBase.entities.Enemy_Table;

import java.util.List;

@Dao
public interface EnemyDAO {
    @Insert
    void insert(Enemy_Table enemyTable);

    @Query("SELECT * FROM enemy_table")
    List<Enemy_Table> getAll();

    @Query("SELECT * FROM enemy_table WHERE id = :idEnemy")
    Enemy_Table getEnemyById(int idEnemy);

    @Query("SELECT * FROM enemy_table WHERE name = :name AND dificulty = :dificulty")
    Enemy_Table testIfExistsFurniture(String name, int dificulty);

    public default boolean insertEnemy(String name, int dificulty, byte[] imageInfo){
        try{
            if(testIfExistsFurniture(name,dificulty)==null){
                insert(new Enemy_Table(name,dificulty,imageInfo));
                return true;
            }else{
                System.err.println("Duplicated Value");
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Delete
    void delete(Enemy_Table enemyTable);
}
