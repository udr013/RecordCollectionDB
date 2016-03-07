package com.udr.RecCollection;

import java.util.Comparator;

/**
 * Created by udr013 on 2-3-2016.
 */
public class Record {
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

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public String getRecordlabelName() {
        return recordlabelName;
    }
}

