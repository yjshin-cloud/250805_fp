package step1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solution02 {
    public static void main(String[] args) {
        List<Integer> numberList = new ArrayList<>();
        // 10~20개 사이의, 0~100 사이의 숫자를 집어넣고 싶다
        Random rd = new Random();
        for (int i = 0; i < rd.nextInt(11) + 10; i++) {
            // 10개에서 20개 사이가 되는 것.
            numberList.add(rd.nextInt(101));
        }
        System.out.println(numberList);
        // 일괄적으로 무언가 계산을 한다 => 10배
        // Array => length. String => length(). 나머지는 size().
        for (int i = 0; i < numberList.size(); i++) {
            // arr[idx] = ?
            numberList.set(i, numberList.get(i) * 10);
        } // 원본을 건드리는 것
        System.out.println(numberList); // side effect -> 어떻게 영향을 미칠지 모름
        List<Integer> numberList2 = new ArrayList<>();
        for (int v : numberList) { // Wrapping -> 원본과 호환(자동변환, 캐스팅)
            numberList2.add(v * 10);
        }
        System.out.println(numberList2);
        System.out.println(numberList);

        // 사상. mapping. => 짝지어서 하나하나 변환처리
        // 데이터가 하나 딱. 거의 없음. 대부분 묶음.
        List<Integer> numberList3 = numberList // int v? x?
                .stream()
                .map(Solution02::multiplyTen) // x -> multiplyTen(x) = return
                .toList(); // 깔끔하게 하나의 리터럴 비슷하게 표현. 구문이 아님.
        System.out.println(numberList3);
    }

    static int multiplyTen(int x) {
        return x * 10;
    }
}