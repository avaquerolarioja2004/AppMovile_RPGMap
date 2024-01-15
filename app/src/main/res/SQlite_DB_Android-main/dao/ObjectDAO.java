package com.example.heroquest.db_sqlite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.heroquest.db_sqlite.entities.Object_Table;

import java.util.List;

@Dao
public interface ObjectDAO {
    @Insert
    void insert(Object_Table objectTable);

    @Query("SELECT * FROM OBJECT_TABLE")
    List<Object_Table> getAll();

    @Query("SELECT * FROM OBJECT_TABLE WHERE id = :idObject")
    Object_Table getObjectById(int idObject);

    @Query("SELECT * FROM OBJECT_TABLE WHERE name = :name AND x = :x AND y = :y AND covers_visibility = :coversVisibility")
    Object_Table testIfExistsObject(String name, int x, int y, boolean coversVisibility);

    public default boolean insertObject(String name, int x, int y, byte[] imageInfo, boolean coverVisivility){
        try{
            if(testIfExistsObject(name,x,y,coverVisivility)==null){
                insert(new Object_Table(name,x,y,imageInfo,coverVisivility));
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
    void delete(Object_Table objectTable);
}
