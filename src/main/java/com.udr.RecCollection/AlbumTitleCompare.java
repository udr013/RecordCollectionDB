package com.udr.RecCollection;

import java.util.Comparator;

public class AlbumTitleCompare implements Comparator<Record> {
    @Override
    public int compare(Record one, Record two)
    {
        return one.getAlbumName().compareTo(two.getAlbumName()) ;
    }


}
