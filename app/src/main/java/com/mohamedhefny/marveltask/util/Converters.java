package com.mohamedhefny.marveltask.util;

import androidx.room.TypeConverter;

import com.mohamedhefny.marveltask.data.entities.CharLinks;
import com.mohamedhefny.marveltask.data.entities.CharacterDetails;
import com.mohamedhefny.marveltask.data.entities.Comics;
import com.mohamedhefny.marveltask.data.entities.ItemRes;
import com.mohamedhefny.marveltask.data.entities.Thumbnail;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public static Thumbnail stringToThumbnail(String thumbnailLink) {
        Thumbnail thumbnail = new Thumbnail();
        String[] thumbnailData = thumbnailLink.split("&&");
        thumbnail.setPath(thumbnailData[0]);
        thumbnail.setExtension(thumbnailData[1]);
        return thumbnail;
    }

    @TypeConverter
    public static String thumbnailToString(Thumbnail thumbnail) {
        return thumbnail.getPath().concat("&&").concat(thumbnail.getExtension());
    }

    @TypeConverter
    public static List<CharLinks> stringToCharLink(String link) {
        String[] linkArray = link.split("&&");
        List<CharLinks> charLinksList = new ArrayList<>();

        if (linkArray.length < 2)
            return charLinksList;

        for (int i = 0; i < linkArray.length; i += 2)
            charLinksList.add(new CharLinks(linkArray[i], linkArray[i+1]));

        return charLinksList;
    }

    @TypeConverter
    public static String linkToString(List<CharLinks> charLinksList) {
        StringBuilder charLinks = new StringBuilder();

        for (CharLinks charLink : charLinksList)
            charLinks.append("&&").append(charLink.getType()).append("&&").append(charLink.getUrl());

        if (charLinks.length() > 2)
            charLinks.delete(0, 2);

        return charLinks.toString();
    }
}
