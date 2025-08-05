package step1;

//import java.util.List;
import java.util.*; // java.util이라는 경로(패키지) 아래 있는 클래스들을 모두 가져오겠다 (*)

public class Solution01 {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>(); // ArrayList는 List는 구현한 친구
        // Array처럼 내부에 순서가 있고, 해당 순서에 따라 index가 부여되고,
        // 타입도 다 동일하고, 근데 '길이 제한'이 명시적으로 주지 않는 이상 알아서 늘어난다.
//        List<int> intList = new ArrayList<>(); // ?
        // List<...> 제네릭. => 특정 객체 타입을 넣으면 유동적으로 해당 타입으로 변환
        // int? -> 원시 타입. (클래스가 아님)
        // int, Integer -> Wrapper 감싸주는 효과
        List<Integer> intList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("입력해주세요 : ");
            String input = scanner.nextLine();
            if (input.equals("종료")) {
                break;
            }
            // 이게 숫자로 변환이 되지 않는 값이 여기 들어오면?
            try {
                intList.add(Integer.parseInt(input)); // Exception
            } catch (NumberFormatException e) {
                // 예외처리로 막을 건지...
                // 다른 조건문 같은 걸로...
                System.err.println("숫자 포맷을 입력해주세요!");
                stringList.add(input); // 이 경우에는 숫자가 아닐 때만...
            }

            System.out.println(stringList);
            System.out.println(intList);
        }
    }
}