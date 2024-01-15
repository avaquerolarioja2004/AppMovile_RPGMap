package com.example.heroquest.db_sqlite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.heroquest.db_sqlite.entities.Furniture_Table;

import java.util.List;

@Dao
public interface FurnitureDAO {
    @Insert
    void insert(Furniture_Table furnitureTable);

    @Query("SELECT * FROM furniture_table")
    List<Furniture_Table> getAll();

    @Query("SELECT * FROM furniture_table WHERE id = :idFurniture")
    Furniture_Table getEnemyById(int idFurniture);

    @Query("SELECT * FROM furniture_table WHERE name = :name AND x = :x AND y = :y AND covers_visibility = :coversVisibility")
    Furniture_Table testIfExistsFurniture(String name, int x, int y, boolean coversVisibility);

    public default boolean insertFurniture(String name, int x, int y, byte[] imageInfo, boolean coverVisivility){
        try{
            if(testIfExistsFurniture(name,x,y,coverVisivility)==null){
                insert(new Furniture_Table(name,x,y,imageInfo,coverVisivility));
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
    void delete(Furniture_Table furnitureTable);
}
