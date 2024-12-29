package me.hyptex.spotifylocalfiles.tag;

public record MusicRecord(String title, String artist, String album,
                          String year, byte[] albumCover) {
}
