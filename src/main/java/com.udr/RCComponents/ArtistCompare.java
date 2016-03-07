package com.udr.RCComponents;

import java.util.Comparator;

// various compare methods for all different fields
 public class ArtistCompare implements Comparator<Record> {
    @Override
    public int compare(Record one, Record two)
    {
        return one.getArtistName().compareTo(two.getArtistName()) ;
    }


}
