package step1;

import java.util.ArrayList;
import java.util.List;

public class Solution04 {
    public static void main(String[] args) {
        // 람다 문법
//        List<String> list = new ArrayList<>();
        List<String> list = List.of("바나나", "사과", "배", "귤", "포도");
        System.out.println(list);
        // 바나나입니다
//        System.out.println(list.stream().map(Solution04::change).toList());
        System.out.println(list.stream().map(
//                "🐒 %s 🐒"::formatted
//                x -> "🐒 %s 🐒".formatted(x) // 개수가 맞는 매개변수 -> (return에 들어갈 표현식)
                x -> {return "🐒 %s 🐒".formatted(x);}
                // Python : lambda x : x + "입니다"
                // JS : x => x + "입니다"
        ).toList());
    }

    static String change(String x) {
        return  x + "입니다";
    }
}