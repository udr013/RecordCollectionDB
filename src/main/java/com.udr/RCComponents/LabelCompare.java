package com.udr.RCComponents;

import java.util.Comparator;

public class LabelCompare implements Comparator<Record> {
    @Override
    public int compare(Record one, Record two)
    {
        return one.getRecordlabelName().compareTo(two.getRecordlabelName()) ;
    }


}
