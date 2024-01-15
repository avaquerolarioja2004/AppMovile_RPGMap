package com.example.heroquest.db_sqlite.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "enemy_table")
public class Enemy_Table {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String nombre;

    @ColumnInfo(name = "dificulty")
    private int dificulty;

    @ColumnInfo(name = "image_data")
    private byte[] imageData;

    public Enemy_Table(String nombre, int dificulty, byte[] imageData) {
        this.nombre = nombre;
        this.dificulty = dificulty;
        this.imageData = imageData;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDificulty() {
        return dificulty;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDificulty(int dificulty) {
        this.dificulty = dificulty;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return id + ";" + nombre + ";" + dificulty + ";" + Arrays.toString(imageData);
    }
}
