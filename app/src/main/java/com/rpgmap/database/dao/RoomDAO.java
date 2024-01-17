package com.rpgmap.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import com.rpgmap.database.entities.Room_Table;

@Dao
public interface RoomDAO {
    @Insert
    void insert(Room_Table roomTable);

    @Query("SELECT * FROM ROOM_TABLE")
    List<Room_Table> getAll();

    public default boolean insertRoom(int x, int y){
        try{
            Room_Table r=new Room_Table(x,y);
            if(!getAll().contains(r)){
                insert(r);
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
    void delete(Room_Table roomTable);
}
