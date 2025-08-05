package step1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
        // Sort(sorted) 정렬.
        int[] arr = {1, 5, 7, 9, 2, 4};
        int[] sortedArr = Arrays.stream(arr).sorted().toArray();
        // 배열도 스트림화가 가능.
        System.out.println(Arrays.toString(sortedArr));
        Arrays.sort(arr); // 원본에 영향을 줌
        System.out.println(Arrays.toString(arr));
        // 데이터가 커지는 방향과 다름 데이터가 등장하는 방향이 일치하게 정렬
        // -> 오름차순 (ascending) / 내림차순 (descending)
        List<Integer> sortedArr2 = Arrays.stream(arr)
                .boxed() // int는 서로 다른 형태의 비교 X. boxed -> int (Integer)
                .sorted(
                        // 정렬 -> 2개를 뽑아서 기준에 따라가지고 배치
//                        (a, b) -> a - b // b가 더 크면 -. a가 더 크면 +.
                        // +면 둘의 위치를 바꾸고, -면 그대로 두는 것.
                        (a, b) -> b - a
                        // a가 크면 -. b가 크면 +. => +면 둘에 위치를 바꾼다
                        // a가 앞에 있는 수, b가 뒤에 있는 수
                        // n1, n2, n3.... -> n1, n2 => ...?
                ).toList();
        System.out.println(sortedArr2);
        // 배열도 스트림화가 가능.
        System.out.println(Arrays.toString(sortedArr));
        // 문자열
        List<String> stringList = List.of("사과", "귤", "바나나");
        System.out.println(stringList.stream().sorted().toList()); // 기본 정렬은 오름차순
        // 가나다순
        System.out.println(stringList.stream().sorted(
//                (a, b) -> a.compareTo(b)
                (a, b) -> b.compareTo(a) // 역순
        ).toList());
        // 길이
        System.out.println(stringList.stream().sorted(
                (a, b) -> a.length() - b.length()
        ).toList());
        System.out.println(stringList.stream().sorted(
                (a, b) -> b.length() - a.length()
        ).toList());
    }
}