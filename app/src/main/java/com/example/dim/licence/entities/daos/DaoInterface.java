package com.example.dim.licence.entities.daos;

import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

public interface DaoInterface<T> {

    public long insert(SQLiteDatabase sqLiteDatabase, T item);
    public int update(SQLiteDatabase sqLiteDatabase, T item);
    public int delete(SQLiteDatabase sqLiteDatabase, T item);
    public SparseArray<T> getAll(SQLiteDatabase sqLiteDatabase);
}
