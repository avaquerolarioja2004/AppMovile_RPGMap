package com.rpgmap.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "object_table")
public class Object_Table {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image_info")
    private byte[] imageInfo;

    public Object_Table(String name, byte[] imageInfo) {
        this.name = name;
        this.imageInfo = imageInfo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImageInfo() {
        return imageInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageInfo(byte[] imageInfo) {
        this.imageInfo = imageInfo;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + Arrays.toString(imageInfo) ;
    }
}
