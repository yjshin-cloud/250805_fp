package step1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Solution03 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Random rd = new Random();
        for (int i = 0 ; i < 100; i++) {
            // 정규 분포로...
            list.add((int) (rd.nextGaussian() * 100));
        }
        // map -> method -> return -> 변환
        // int(Integer) x.
        System.out.println(list);

        // (클래스 혹은 객체 이름)::메서드이름
        // 클래스::static메서드
        // 객체::메서드
        List<Integer> list2 = list.stream()
                // abs -> absolute (절대값)
                .map(Math::abs) // int x -> 다룰 수 있는 메서드.
                .map(Solution03::addOne) // 메서드 체이닝
                .map(rd::nextInt) // 기존에 있는 객체를 참조해서 메서드를...
                .toList(); // 실행 순서를 명확화.

        List<Integer> list3 = new ArrayList<>();
        for (int i : list) {
            int v = rd.nextInt(addOne(Math.abs(i))); // 이친구도 변수로 뽑아내면?
            // 메모리...
            // () 지옥 () () () ()...
            list3.add(v);
        }
        // Math. -> static(정적) 메서드
        System.out.println(list2); // 별도의 사본을 만들고...
        System.out.println(System.identityHashCode(list2));
        System.out.println(list);
        System.out.println(System.identityHashCode(list));
    }

    static int addOne(int x) {
        return x + 1;
    }
}