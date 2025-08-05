package step2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ImageGenStreamClean {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = 3;
        HttpClient httpClient = HttpClient.newHttpClient();
        String urlTemplate = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent";
        String promptTemplate = """
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
        """;
        String imageTemplate = """
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
                """;
        IntStream
                .range(0, size)
                .boxed() // 자유롭게 쓸 수 있음
                .map(x -> {
                    System.out.print("좋아하는 캐릭터는? : ");
                    return sc.nextLine();
                })
                .map(x -> {
                    try {
                        HttpResponse<String> response = httpClient.send(
                                HttpRequest.newBuilder()
                                        .uri(URI.create(urlTemplate.formatted("gemini-2.0-flash")))
                                        .headers("Content-Type", "application/json",
                                                "X-goog-api-key", System.getenv("GEMINI_API_KEY"))
                                        .POST(
                                                HttpRequest.BodyPublishers.ofString(promptTemplate.formatted(x))
                                        )
                                        .build()
                                , HttpResponse.BodyHandlers.ofString());
                        return response.body();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    return null;
                })
                .filter(x -> x != null) // 조금 안전하게
                .map(x -> x.split("\"text\": \"")[1] // 0, 1, 2....
                        .split("}")[0]
                        .replace("\\n", "")
                        .replace("\"", "")
                        .trim())
                .map(x -> {
                    try {
                        HttpResponse<String> response = httpClient.send(
                                HttpRequest.newBuilder()
                                        .uri(URI.create(urlTemplate.formatted("gemini-2.0-flash-preview-image-generation")))
                                        .headers("Content-Type", "application/json",
                                                "X-goog-api-key", System.getenv("GEMINI_API_KEY"))
                                        .POST(
                                                HttpRequest.BodyPublishers.ofString(imageTemplate.formatted(x))
                                        )
                                        .build()
                                , HttpResponse.BodyHandlers.ofString());
                        return response.body();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    return null;
                })
                .filter(x -> x != null) // 조금 안전하게
                .map(x -> x.split("\"data\": \"")[1] // 0, 1, 2....
                        .split("}")[0]
                        .replace("\\n", "")
                        .replace("\"", "")
                        .trim())
                // base64 image
                .forEach(ImageGenStreamClean::handleImage);
    }

    static void handleImage(String base64) {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        String outputPath = "%s.png".formatted(System.currentTimeMillis());
        Path filePath = Paths.get(outputPath);
        try {
            Files.write(filePath, imageBytes);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}