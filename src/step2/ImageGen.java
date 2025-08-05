package step2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ImageGen {
    // 실행
    public static void main(String[] args) {
        ImageGen gen = new ImageGen(2);
//        gen.inputData(5);
        gen.inputData();
        gen.makeImagePrompt();
        gen.generateImage();
    }

    // 빈 리스트
    final private List<String> favoriteList = new ArrayList<>();

    final private Scanner scanner = new Scanner(System.in);

    final private int size;

    public ImageGen(int size) {
        this.size = size;
    }

    // 입력 받기
//    void inputData(int size) {
    void inputData() {
        for (int i = 0; i < size; i++) {
            System.out.print("좋아하는 동물을 입력해주세요 : ");
            String input = scanner.nextLine();
            // trim() -> space, enter 문자열 앞뒤에 제거
            if (input.trim().isEmpty()) {
                System.out.println("제대로 입력해주세요!");
                i--;
                continue;
            }
            favoriteList.add(input);
        }
        System.out.println(favoriteList);
    }

    private final HttpClient httpClient = HttpClient.newHttpClient();
    // 네트워크 통신을 위한 객체

    private final List<String> imagePromptList = new ArrayList<>();

    private final String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");

    private String callAPI(String url, String body) {
        if (GEMINI_API_KEY == null) {
            throw new RuntimeException("GEMINI_API_KEY가 없습니다!");
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json",
                        "X-goog-api-key", GEMINI_API_KEY)
                .POST(
                        HttpRequest.BodyPublishers.ofString(
                                body
                        )
                )
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );
            return httpResponse.body();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    void makeImagePrompt() {
        // 내부에 있는 favoriteList -> 프롬프트를 가장 잘 만드는 방법 -> AI한테 시키는 것.
        // favoriteList <- 이미지 생성용 프롬프트 변환.
        // https://aistudio.google.com/apikey
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent"; // 인터넷링크
        for (String v : favoriteList) {
            // 무언가 생성작업을 해서
            String result = callAPI(url, """
                                {
                                    "contents": [
                                      {
                                        "parts": [
                                          {
                                            "text": "%s(을)를 이미지로 나타내기 위한 200자 이내의 상세한 프롬프트를 작성해줘. 결과만 작성해줘."
                                          }
                                        ]
                                      }
                                    ]
                                  }
                                """.formatted(v));
            String prompt = result
                    .split("\"text\": \"")[1] // 0, 1, 2....
                    .split("}")[0]
                    .replace("\\n", "")
                    .replace("\"", "")
                    .trim();
            imagePromptList.add(prompt);
        }
//        System.out.println(imagePromptList);
        for (String s : imagePromptList) {
            System.out.println(s);
        }
    }

    void generateImage() {
        String model = "gemini-2.0-flash-preview-image-generation";
        String url = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent".formatted(model); // 인터넷링크
        // gemini-2.0-flash -> gemini-2.0-flash-preview-image-generation
        for (String prompt : imagePromptList) {
            String result = callAPI(url, """
                    {
                        "contents": [
                          {
                            "role": "user",
                            "parts": [
                              {
                                "text": "%s"
                              },
                            ]
                          },
                        ],
                        "generationConfig": {
                          "responseModalities": ["IMAGE", "TEXT"],
                        },
                    }
                    """.formatted(prompt));
//            System.out.println(result);
            String image64 = result
                    .split("\"data\": \"")[1] // 0, 1, 2....
                    .split("}")[0]
                    .replace("\\n", "")
                    .replace("\"", "")
                    .trim();
//            System.out.println(image64);
            byte[] imageBytes = Base64.getDecoder().decode(image64);
//            String outputPath = "%s.png".formatted(LocalTime.now());
//            String outputPath = "%s.png".formatted(
//                    LocalTime.now().toString().replace(":", "-").replace(".", "-")
//            );
//            String outputPath = "%s.png".formatted(
//                    LocalTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
//            );
//            String outputPath = "%s.png".formatted(System.currentTimeMillis());
            String outputPath = "%s.png".formatted(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
            );
            Path filePath = Paths.get(outputPath);
            try {
                Files.write(filePath, imageBytes);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }
}