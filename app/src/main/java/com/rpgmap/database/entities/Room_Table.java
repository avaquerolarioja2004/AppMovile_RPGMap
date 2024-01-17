package com.rpgmap.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "room_table",primaryKeys = {"x","y"})
public class Room_Table implements Serializable {

    @ColumnInfo(name = "x")
    private int x;

    @ColumnInfo(name = "y")
    private int y;

    public Room_Table(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room_Table roomTable = (Room_Table) o;
        return x == roomTable.x && y == roomTable.y || x == roomTable.y && y == roomTable.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
