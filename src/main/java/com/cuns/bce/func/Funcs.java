package com.cuns.bce.func;

import com.cuns.bce.dto.response.comics.ChapterDto;
import com.cuns.bce.dto.response.comics.ComicsDto;
import com.cuns.bce.entities.Chapter;
import com.cuns.bce.entities.Comic;
import org.modelmapper.ModelMapper;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Funcs {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static String getTimeAgo(OffsetDateTime dateTime) {
        return new PrettyTime(new Locale("vi")).format(Date.from(dateTime.toInstant()));
    }
    public static String getTextSlug(String text) {
        String slug = text.replaceAll("đ", "d");
        slug = slug.replaceAll("Đ", "D");
        return Final.SLUG.slugify(slug);
    }
    // Hàm để trích xuất số từ chuỗi (bao gồm số thập phân)
    public static Double extractNumber(String text) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String numberStr = matcher.group();
            return Double.parseDouble(numberStr);
        }

        return 0.0; // Trả về 0.0 nếu không tìm thấy số
    }
    public static Integer extractNumberInt(String text) {
        Pattern pattern = Pattern.compile("\\d+"); // Tìm các chữ số trong chuỗi
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String numberStr = matcher.group();
            return Integer.parseInt(numberStr);
        } else {
            return -1;
        }
    }

    public static int getChapterIndex(Set<Chapter> chapters, Chapter chapter) {
        int index = 0;
        for (Chapter c : chapters) {
            if (c.getId().equals(chapter.getId())) {
                return index;
            }
            index++;
        }
        return 0;
    }

    public static List<List<Chapter>> chunkChapters(Set<Chapter> chapters, int size) {
        List<List<Chapter>> chunks = new ArrayList<>();

        for (int i = 0; i < chapters.size(); i += size) {
            int endIndex = Math.min(i + size, chapters.size());
            List<Chapter> chunk = chapters.stream().skip(i).limit(endIndex - i).toList();
            chunks.add(chunk);
        }
        return chunks;
    }
    public static List<List<ChapterDto>> chunkListChapters(List<Chapter> chapters, int size) {
        List<List<ChapterDto>> chunks = new ArrayList<>();

        for (int i = 0; i < chapters.size(); i += size) {
            int endIndex = Math.min(i + size, chapters.size());
            List<Chapter> chunk = chapters.stream().skip(i).limit(endIndex - i).toList(); // list: 50
            List<ChapterDto> chunkDto = chunk.stream().map(c -> modelMapper.map(c, ChapterDto.class)).toList();
            chunks.add(chunkDto);
        }
        return chunks;
    }
}
