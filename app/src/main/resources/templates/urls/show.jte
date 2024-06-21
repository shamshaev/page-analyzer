@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@import hexlet.code.util.Utils
@param UrlPage page

@template.layout.page(
    page = page,
    content = @`
        <section class="container-lg pt-5">
            <p class="h1 mb-3">Сайт: ${page.getUrl().getName()}</p>
            <table class="table table-bordered text-dark table-hover mb-3">
                <tbody>
                <tr>
                    <td class="col-5">ID</td>
                    <td class="col-7">${page.getUrl().getId()}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${page.getUrl().getName()}</td>
                </tr>
                <tr>
                    <td>Дата создания</td>
                    <td>${Utils.formatTime(page.getUrl().getCreatedAt())}</td>
                </tr>
                </tbody>
            </table>
            <p class="h2 mt-5">Проверки</p>
            <form action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}" method="post">
                <button type="submit" class="btn btn-primary mb-3">Запустить проверку</button>
            </form>
            <table class="table table-bordered text-dark table-hover">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th class="col-1">Код ответа</th>
                    <th class="col-2">title</th>
                    <th class="col-2">h1</th>
                    <th class="col-4">description</th>
                    <th class="col-2">Дата проверки</th>
                </tr>
                </thead>
                <tbody>
                @for(var urlCheck : page.getUrlChecks())
                    <tr>
                        <td>${urlCheck.getId()}</td>
                        <td>${urlCheck.getStatusCode()}</td>
                        <td>${urlCheck.getTitle()}</td>
                        <td>${urlCheck.getH1()}</td>
                        <td>${urlCheck.getDescription()}</td>
                        <td>${Utils.formatTime(urlCheck.getCreatedAt())}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </section>
    `
)