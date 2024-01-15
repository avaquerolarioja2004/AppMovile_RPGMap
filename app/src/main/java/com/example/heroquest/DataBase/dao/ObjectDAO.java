package com.example.heroquest.DataBase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import com.example.heroquest.DataBase.entities.Object_Table;

@Dao
public interface ObjectDAO {
    @Insert
    void insert(Object_Table objectTable);

    @Query("SELECT * FROM OBJECT_TABLE")
    List<Object_Table> getAll();

    @Query("SELECT * FROM OBJECT_TABLE WHERE id = :idObject")
    Object_Table getObjectById(int idObject);

    @Query("SELECT * FROM OBJECT_TABLE WHERE name = :name")
    Object_Table testIfExistsObject(String name);

    public default boolean insertObject(String name, byte[] imageInfo){
        try{
            if(testIfExistsObject(name)==null){
                insert(new Object_Table(name,imageInfo));
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
