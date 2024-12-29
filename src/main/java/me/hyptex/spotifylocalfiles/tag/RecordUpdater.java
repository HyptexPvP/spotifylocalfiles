package me.hyptex.spotifylocalfiles.tag;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import java.io.File;
import java.util.regex.Pattern;

public class RecordUpdater {

    private static final Pattern PATTERN = Pattern.compile("v=[a-zA-Z0-9]{11}");

    public static Mp3File getMp3File(File file) {
        try {
            return new Mp3File(file);
        } catch (Exception e) {
            return null;
        }
    }

    public static void updateRecord(MusicRecord record, File file) throws Exception {
        Mp3File mp3File = getMp3File(file);

        if (mp3File == null) {
            throw new Exception("Unsupported file format!");
        }

        ID3v2 id3v2Tag;
        if (mp3File.hasId3v2Tag()) {
            id3v2Tag = mp3File.getId3v2Tag();
        } else {
            id3v2Tag = new ID3v24Tag();
            mp3File.setId3v2Tag(id3v2Tag);
        }

        id3v2Tag.setArtist(record.artist());
        id3v2Tag.setAlbumArtist(record.artist());
        id3v2Tag.setTitle(record.title());
        id3v2Tag.setAlbum(record.album());

        if (record.year() != null && !record.year().isEmpty()) {
            id3v2Tag.setYear(record.year());
        }

        if (record.albumCover() != null) {
            id3v2Tag.setAlbumImage(record.albumCover(), "mime/type");
        }



        mp3File.save(record.artist() + " - " + record.title() + ".mp3");
        System.out.println("Saved as " + record.artist() + " - " + record.title() + ".mp3");


    }
}
