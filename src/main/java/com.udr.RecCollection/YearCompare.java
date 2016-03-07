package com.udr.RecCollection;

import java.util.Comparator;

public class YearCompare implements Comparator<Record> {
    @Override
    public int compare(Record one, Record two)
    {
        return ((Integer)one.getYearOfRelease()).compareTo((Integer)two.getYearOfRelease()) ;
    }


}
