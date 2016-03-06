package com.udr.RecCollection;

/**
 * Created by udr013 on 2-3-2016.
 */
public class Record implements Comparable<Record>{
    private String artistName;
    private String albumName;
    private int yearOfRelease;
    private String recordlabelName;

    public Record(String artistName, String albumName, int yearOfRelease, String recordlabelName) {
        this.artistName = artistName;
        this.albumName = albumName;
        this.yearOfRelease = yearOfRelease;
        this.recordlabelName = recordlabelName;
    }

    @Override
    public String toString() {
        return
                artistName +
                ":" + albumName +
                ":" + yearOfRelease +
                ":" + recordlabelName + "#";
    }

    @Override
    public int compareTo(Record o) {
        return 0;
    }
}
