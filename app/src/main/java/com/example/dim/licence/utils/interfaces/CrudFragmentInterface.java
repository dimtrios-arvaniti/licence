package com.example.dim.licence.utils.interfaces;

public interface CrudFragmentInterface<T> {

    public void onFilterClick(String currentFilterType);
    public void updateDetailFragment(T item);
    public void updateEditFragment(T item);
    public void onDeleteClick();
    public void onNewClick();
    public void onSaveClick(T item);

}
