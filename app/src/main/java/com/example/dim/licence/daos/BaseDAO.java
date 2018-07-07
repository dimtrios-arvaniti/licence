package com.example.dim.licence.daos;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface BaseDAO<T> {

    public long insert(SQLiteDatabase sqLiteDatabase, T item);
    public boolean update(SQLiteDatabase sqLiteDatabase, T item);
    public boolean delete(SQLiteDatabase sqLiteDatabase, T item);
    public T getById(SQLiteDatabase sqLiteDatabase, int itemId);
    public List<T> getAll(SQLiteDatabase sqLiteDatabase);


}
