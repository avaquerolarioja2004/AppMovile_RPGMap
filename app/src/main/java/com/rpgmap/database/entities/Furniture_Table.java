package com.rpgmap.database.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "furniture_table")
public class Furniture_Table {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "x")
    private int x;

    @ColumnInfo(name = "y")
    private int y;

    @ColumnInfo(name = "image_info")
    private byte[] imageInfo;

    @ColumnInfo(name = "covers_visibility")
    private boolean coversVisibility;

    public Furniture_Table(String name, int x, int y, byte[] imageInfo, boolean coversVisibility) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.imageInfo = imageInfo;
        this.coversVisibility = coversVisibility;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public byte[] getImageInfo() {
        return imageInfo;
    }

    public boolean isCoversVisibility() {
        return coversVisibility;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImageInfo(byte[] imageInfo) {
        this.imageInfo = imageInfo;
    }

    public void setCoversVisibility(boolean coversVisibility) {
        this.coversVisibility = coversVisibility;
    }
    @Override
    public String toString() {
        return id + ";" + name + ";" + x + ";" + y + ";" + Arrays.toString(imageInfo) +
                ";" + coversVisibility;
    }
}
