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
    // Hàm biến đổi một chuỗi thành chuỗi slug
    public static String handleSlugify(String text, String separator) {
        Map<Character, Character> diacriticsMap = new HashMap<>();
        diacriticsMap.put('á', 'a'); diacriticsMap.put('à', 'a'); diacriticsMap.put('ả', 'a'); diacriticsMap.put('ã', 'a'); diacriticsMap.put('ạ', 'a');
        diacriticsMap.put('ă', 'a'); diacriticsMap.put('ắ', 'a'); diacriticsMap.put('ằ', 'a'); diacriticsMap.put('ẳ', 'a'); diacriticsMap.put('ẵ', 'a'); diacriticsMap.put('ặ', 'a');
        diacriticsMap.put('â', 'a'); diacriticsMap.put('ấ', 'a'); diacriticsMap.put('ầ', 'a'); diacriticsMap.put('ẩ', 'a'); diacriticsMap.put('ẫ', 'a'); diacriticsMap.put('ậ', 'a');
        diacriticsMap.put('đ', 'd');
        diacriticsMap.put('é', 'e'); diacriticsMap.put('è', 'e'); diacriticsMap.put('ẻ', 'e'); diacriticsMap.put('ẽ', 'e'); diacriticsMap.put('ẹ', 'e');
        diacriticsMap.put('ê', 'e'); diacriticsMap.put('ế', 'e'); diacriticsMap.put('ề', 'e'); diacriticsMap.put('ể', 'e'); diacriticsMap.put('ễ', 'e'); diacriticsMap.put('ệ', 'e');
        diacriticsMap.put('í', 'i'); diacriticsMap.put('ì', 'i'); diacriticsMap.put('ỉ', 'i'); diacriticsMap.put('ĩ', 'i'); diacriticsMap.put('ị', 'i');
        diacriticsMap.put('ó', 'o'); diacriticsMap.put('ò', 'o'); diacriticsMap.put('ỏ', 'o'); diacriticsMap.put('õ', 'o'); diacriticsMap.put('ọ', 'o');
        diacriticsMap.put('ô', 'o'); diacriticsMap.put('ố', 'o'); diacriticsMap.put('ồ', 'o'); diacriticsMap.put('ổ', 'o'); diacriticsMap.put('ỗ', 'o'); diacriticsMap.put('ộ', 'o');
        diacriticsMap.put('ơ', 'o'); diacriticsMap.put('ớ', 'o'); diacriticsMap.put('ờ', 'o'); diacriticsMap.put('ở', 'o'); diacriticsMap.put('ỡ', 'o'); diacriticsMap.put('ợ', 'o');
        diacriticsMap.put('ú', 'u'); diacriticsMap.put('ù', 'u'); diacriticsMap.put('ủ', 'u'); diacriticsMap.put('ũ', 'u'); diacriticsMap.put('ụ', 'u');
        diacriticsMap.put('ư', 'u'); diacriticsMap.put('ứ', 'u'); diacriticsMap.put('ừ', 'u'); diacriticsMap.put('ử', 'u'); diacriticsMap.put('ữ', 'u'); diacriticsMap.put('ự', 'u');
        diacriticsMap.put('ý', 'y'); diacriticsMap.put('ỳ', 'y'); diacriticsMap.put('ỷ', 'y'); diacriticsMap.put('ỹ', 'y'); diacriticsMap.put('ỵ', 'y');
        diacriticsMap.put('Á', 'A'); diacriticsMap.put('À', 'A'); diacriticsMap.put('Ả', 'A'); diacriticsMap.put('Ã', 'A'); diacriticsMap.put('Ạ', 'A');
        diacriticsMap.put('Ă', 'A'); diacriticsMap.put('Ắ', 'A'); diacriticsMap.put('Ằ', 'A'); diacriticsMap.put('Ẳ', 'A'); diacriticsMap.put('Ẵ', 'A'); diacriticsMap.put('Ặ', 'A');
        diacriticsMap.put('Â', 'A'); diacriticsMap.put('Ấ', 'A'); diacriticsMap.put('Ầ', 'A'); diacriticsMap.put('Ẩ', 'A'); diacriticsMap.put('Ẫ', 'A'); diacriticsMap.put('Ậ', 'A');
        diacriticsMap.put('Đ', 'D');
        diacriticsMap.put('É', 'E'); diacriticsMap.put('È', 'E'); diacriticsMap.put('Ẻ', 'E'); diacriticsMap.put('Ẽ', 'E'); diacriticsMap.put('Ẹ', 'E');
        diacriticsMap.put('Ê', 'E'); diacriticsMap.put('Ế', 'E'); diacriticsMap.put('Ề', 'E'); diacriticsMap.put('Ể', 'E'); diacriticsMap.put('Ễ', 'E'); diacriticsMap.put('Ệ', 'E');
        diacriticsMap.put('Í', 'I'); diacriticsMap.put('Ì', 'I'); diacriticsMap.put('Ỉ', 'I'); diacriticsMap.put('Ĩ', 'I'); diacriticsMap.put('Ị', 'I');
        diacriticsMap.put('Ó', 'O'); diacriticsMap.put('Ò', 'O'); diacriticsMap.put('Ỏ', 'O'); diacriticsMap.put('Õ', 'O'); diacriticsMap.put('Ọ', 'O');
        diacriticsMap.put('Ô', 'O'); diacriticsMap.put('Ố', 'O'); diacriticsMap.put('Ồ', 'O'); diacriticsMap.put('Ổ', 'O'); diacriticsMap.put('Ỗ', 'O'); diacriticsMap.put('Ộ', 'O');
        diacriticsMap.put('Ơ', 'O'); diacriticsMap.put('Ớ', 'O'); diacriticsMap.put('Ờ', 'O'); diacriticsMap.put('Ở', 'O'); diacriticsMap.put('Ỡ', 'O'); diacriticsMap.put('Ợ', 'O');
        diacriticsMap.put('Ú', 'U'); diacriticsMap.put('Ù', 'U'); diacriticsMap.put('Ủ', 'U'); diacriticsMap.put('Ũ', 'U'); diacriticsMap.put('Ụ', 'U');
        diacriticsMap.put('Ư', 'U'); diacriticsMap.put('Ứ', 'U'); diacriticsMap.put('Ừ', 'U'); diacriticsMap.put('Ử', 'U'); diacriticsMap.put('Ữ', 'U'); diacriticsMap.put('Ự', 'U');
        diacriticsMap.put('Ý', 'Y'); diacriticsMap.put('Ỳ', 'Y'); diacriticsMap.put('Ỷ', 'Y'); diacriticsMap.put('Ỹ', 'Y'); diacriticsMap.put('Ỵ', 'Y');

        for (Map.Entry<Character, Character> entry : diacriticsMap.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }

        String slug = text.replaceAll("[^a-zA-Z0-9]", separator);

        return Final.SLUG.slugify(slug);
    }
}
