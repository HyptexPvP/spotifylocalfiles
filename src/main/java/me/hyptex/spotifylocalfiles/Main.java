package me.hyptex.spotifylocalfiles;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import lombok.SneakyThrows;
import me.hyptex.spotifylocalfiles.tag.RecordUpdater;
import me.hyptex.spotifylocalfiles.tag.MusicRecord;

import java.io.File;
import java.nio.file.Files;

public class Main {


    @SneakyThrows
    public static void main(String[] args) {
        OptionParser parser = new OptionParser();

        parser.accepts("song").withRequiredArg().required().ofType(File.class);
        parser.accepts("name").withRequiredArg().required().ofType(String.class);

        parser.accepts("artist").withRequiredArg().required().ofType(String.class);
        parser.accepts("album").withRequiredArg().required().ofType(String.class);
        parser.accepts("year").withRequiredArg().required().ofType(Integer.class);
        parser.accepts("cover").withRequiredArg().ofType(File.class);


        OptionSet optionSet = parser.parse(args);



        byte[] albumCover = null;

        if (optionSet.has("cover")) {
            albumCover = Files.readAllBytes(((File) optionSet.valueOf("cover")).toPath());
        }

        MusicRecord record = new MusicRecord((String) optionSet.valueOf("name"),
                (String) optionSet.valueOf("artist"),
                (String) optionSet.valueOf("album"),
                Integer.toString((Integer) optionSet.valueOf("year")),

                albumCover);

        System.out.println("Processing " + record.title());

        RecordUpdater.updateRecord(record, (File) optionSet.valueOf("song"));
    }
}