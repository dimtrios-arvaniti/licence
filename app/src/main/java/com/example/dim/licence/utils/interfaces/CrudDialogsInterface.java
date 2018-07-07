package com.example.dim.licence.utils.interfaces;

public interface CrudDialogsInterface<T> {

    public boolean isNewMode();
    public void applyFilter(String type, String value);
    public void goToPage(int page);
    public void save(T item);
    public void delete();
}
