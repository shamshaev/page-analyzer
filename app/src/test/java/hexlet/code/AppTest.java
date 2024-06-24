package hexlet.code;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import static org.assertj.core.api.Assertions.assertThat;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

class AppTest {
    Javalin app;
    private static MockWebServer mockServer;

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        Path filePath = getFixturePath(fileName);
        return Files.readString(filePath).trim();
    }

    @BeforeAll
    public static void setUpMockServer() throws Exception {
        mockServer = new MockWebServer();

        var body = readFixture("test.html");
        MockResponse response = new MockResponse().setBody(body);

        mockServer.enqueue(response);
        mockServer.start();
    }

    @AfterAll
    public static void closeMockServer() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public final void setUpApp() throws SQLException {
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
    public void testUrlsPage() throws SQLException {
        var url = new Url("https://www.sports.ru");
        UrlRepository.save(url);

        var urlCheck = new UrlCheck(url.getId(), 200, "Спорт", "Новости спорта", "Все самое интересное");
        UrlCheckRepository.save(urlCheck);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.sports.ru", "200");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.rbc.ru/news/";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.rbc.ru");
            assertThat(UrlRepository.getEntities()).hasSize(1);
        });
    }

    @Test
    public void testShowUrl() throws SQLException {
        var url = new Url("https://www.example.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Сайт: https://www.example.com");
        });
    }

    @Test
    public void testCheckUrl() throws SQLException {
        var name = mockServer.url("/").toString();
        var url = new Url(name);
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/" + url.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Example Domain", "Super Duper h1",
                    "this one is for testing");
        });
    }

    @Test
    void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
