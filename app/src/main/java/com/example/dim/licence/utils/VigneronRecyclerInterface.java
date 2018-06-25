package com.example.dim.licence.utils;

import com.example.dim.licence.entities.Vigneron;

public interface VigneronRecyclerInterface {

    public void updateSelectedItem(int selectedPos);

    public void locate(Vigneron selected);

    public void contact(Vigneron selected);
}
