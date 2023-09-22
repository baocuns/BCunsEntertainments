package com.cuns.bce.func;

import com.cuns.bce.dto.response.comics.ComicsDto;
import com.cuns.bce.entities.Chapter;
import com.cuns.bce.entities.Comic;
import org.modelmapper.ModelMapper;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Funcs {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static String getTimeAgo(OffsetDateTime dateTime) {
        return new PrettyTime().format(Date.from(dateTime.toInstant()));
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

    public static List<ComicsDto> mapList(List<Comic> comics) {
        List<ComicsDto> comicsDto = new ArrayList<>();
        comics.forEach(comic -> comicsDto.add(modelMapper.map(comic, ComicsDto.class)));
        return comicsDto;
    }
}
