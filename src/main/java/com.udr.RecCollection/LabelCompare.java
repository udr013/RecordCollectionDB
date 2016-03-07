package com.udr.RecCollection;

import java.util.Comparator;

public class LabelCompare implements Comparator<Record> {
    @Override
    public int compare(Record one, Record two)
    {
        return one.getRecordlabelName().compareTo(two.getRecordlabelName()) ;
    }


}
