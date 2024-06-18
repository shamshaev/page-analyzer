package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        try {
            var rawName = ctx.formParam("url");
            var validatedName = new URI(rawName).toURL();

            var port = validatedName.getPort() != -1 ? ":" + validatedName.getPort() : "";
            var name = validatedName.getProtocol() + "://" + validatedName.getHost() + port;
            var createdAt = Timestamp.valueOf(LocalDateTime.now());

            var url = new Url(name, createdAt);
            UrlRepository.save(url);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (IllegalArgumentException | URISyntaxException | MalformedURLException ex) {
            var page = new BasePage();
            ctx.sessionAttribute("flash", "Некорректный URL");
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", model("page", page));
        } catch (JdbcSQLIntegrityConstraintViolationException e) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Post not found"));

        var page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }




//    public static void build(Context ctx) {
//        var page = new BuildUserPage();
//        ctx.render("users/build.jte", model("page", page));
//    }



}
