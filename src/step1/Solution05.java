package step1;

import java.util.List;

public class Solution05 {
    // 람다와 스트림 활용 🙂
    public static void main(String[] args) {
        // map -> a => a'?
        List<String> lst1 = List.of("Hello", "Bye", "Go Go");
        System.out.println(lst1);
        System.out.println(lst1.stream().map(
//                x -> x.toUpperCase()
//                x -> x.toLowerCase()
//                String::toUpperCase // 메서드 참조
//                x -> x + "😜"
                x -> {
                    if (x.length() <= 3) {
                        return "😂";
                    }
                    return "😍";
                }
        ).toList());
        // filter -> 특정한 조건을 '만족'시키는 친구만 남기는.
        List<Double> lst2 = List.of(1.314, -3.7, -23.5555, 12.485);
        System.out.println(lst2.stream()
                .map(Math::abs) // 메서드 체이닝 -> map으로 처리한 결과를 filter로 다시 처리...
                .filter(
                        x -> x >= 10
                ).toList());

    }
}