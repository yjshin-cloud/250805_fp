package step1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Solution06 {
    public static void main(String[] args) {
        // reduce
        // -> 압축시키는 것. -> {....} => 1개의 값으로 압축. 변환.
        // map => 모든 것에 대한 결과값 a -> a', b -> b'...
        // reduce => cur, acc(accumulator) => 최종값...
        List<Integer> l1 = List.of(1, 5, 4, 3, 7);
        // 이 친구를 합하려면...
        int sum = 0;
        for (Integer i : l1) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println("REDUCE");
        System.out.println(l1.stream().reduce(
                0, (cur, acc) -> cur + acc));
        // reduce(초기값, 람다)
        // 람다 : cur 순차적으로 탐색할 데이터를 의미 / acc -> 그 데이터들이 합쳐진 것.
        System.out.println(l1.stream().reduce(
                // 곱할 경우...
                1, (cur, acc) -> cur * acc));

        List<String> list = List.of("apple", "asia", "anaconda", "bear", "pear");
        System.out.println(
                list.stream().reduce(
                        // 맨 앞에 initial한 초기값을 안 주면 나열된 데이터 중 첫 데이터가 그 역할.
//                        "", (cur, acc) -> acc + "," + cur
//                        "", (cur, acc) -> "%s,%s".formatted(cur, acc)
                        "%s,%s"::formatted
                ).get()
        );

        // map, sorted, reduce, filter, +++++... // 고차함수.
        // (n1, n2, n3.....) => 변환. 처리해주는. stream.
        // stream 처리하는 방식에 대한 정의. 클래스, 객체를 통해서 메서드참조...
        // lambda 표현식으로 내가 원하는 걸 그때 그때 만들 수도 있다.
        // Spring Security나 Spring Data -> 이런 문법이 강제되거나 알아야하는...
        // 패러미터 -> 값...?

        // forEach는 왜 안 보여주나요?
        list.stream()
                // 복잡한 stream 작업을 하면 뭔가 남음.
                .forEach(x -> System.out.println(x));
//                .forEach(System.out::println); // 더 이상 체이닝할 수도 없고. 이걸 '소진'
        // 내가 넣은 메서드를(주로 void 결과인) 돌아가면서 실행해주는 친구
    }
}