package hexlet.code;

import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import static org.assertj.core.api.Assertions.assertThat;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;

class AppTest {
    Javalin app;

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        Path filePath = getFixturePath(fileName);
        return Files.readString(filePath).trim();
    }

    @BeforeEach
    public final void setUp() throws SQLException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Бесплатно проверяйте сайты на SEO пригодность");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Последняя проверка");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.sports.ru/football/";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.sports.ru");
            assertThat(UrlRepository.getEntities()).hasSize(1);
        });
    }

    @Test
    public void testShowUrl() throws SQLException {
        var url = new Url("https://www.example.com", Timestamp.valueOf("2024-06-19 12:06:22"));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Сайт: https://www.example.com");
        });
    }

    @Test
    public void testCheckUrl() throws Exception {
        MockWebServer server = new MockWebServer();
        var body = readFixture("test.html");
        MockResponse response = new MockResponse().setBody(body);
        server.enqueue(response);
        server.start();
        var urlName = server.url("/").toString();

        var url = new Url(urlName, Timestamp.valueOf("2024-06-19 12:06:22"));
        UrlRepository.save(url);

        JavalinTest.test(app, (serverAA, client) -> {
            var responseAA = client.post("/urls/" + url.getId() + "/checks");
            assertThat(responseAA.code()).isEqualTo(200);
            assertThat(responseAA.body().string()).contains("Example Domain", "Correct Example Domain",
                    "this one is for testing");
        });

        server.shutdown();
    }


    @Test
    void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
