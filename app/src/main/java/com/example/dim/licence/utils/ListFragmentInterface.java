package com.example.dim.licence.utils;

public interface ListFragmentInterface<T> {

    public void updateRecyclerAdpater(T item, boolean isDelete);
    public void updateRecyclerAdapter();

}
