package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var lastChecks = UrlCheckRepository.getLastChecks();
        var page = new UrlsPage(urls, lastChecks);

        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        try {
            var rawName = ctx.formParam("url");
            var validatedName = new URI(rawName).toURL();

            var port = validatedName.getPort() != -1 ? ":" + validatedName.getPort() : "";
            var name = validatedName.getProtocol() + "://" + validatedName.getHost() + port;

            if (UrlRepository.isUnique(name)) {
                var createdAt = Timestamp.from(Instant.now());
                var url = new Url(name, createdAt);
                UrlRepository.save(url);

                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.redirect(NamedRoutes.urlsPath());
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(NamedRoutes.urlsPath());
            }
        } catch (IllegalArgumentException | URISyntaxException | MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void show(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));
        var urlChecks = UrlCheckRepository.getEntitiesByUrlId(urlId);
        var page = new UrlPage(url, urlChecks);

        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void check(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();

        try {
            var url = UrlRepository.find(urlId)
                    .orElseThrow(() -> new NotFoundResponse("Url not found"));
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            Document doc = Jsoup.parse(response.getBody());

            var statusCode = response.getStatus();
            var title = doc.title();

            var elementH1 = doc.selectFirst("h1");
            var h1 = elementH1 != null ? elementH1.text() : null;

            var elementDescription = doc.getElementsByAttributeValue("name", "description").first();
            var description =  elementDescription != null ? elementDescription.attr("content") : null;

            var createdAt = Timestamp.from(Instant.now());

            var urlCheck = new UrlCheck(urlId, statusCode, title, h1, description, createdAt);
            UrlCheckRepository.save(urlCheck);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
