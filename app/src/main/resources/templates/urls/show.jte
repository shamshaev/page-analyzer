@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.layout.page(
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
                    <td>${page.getUrl().getCreatedAt().toString()}</td>
                </tr>
                </tbody>
            </table>
            <p class="h2 mt-5">Проверки</p>
            <button type="button" class="btn btn-primary mb-3">Запустить проверку</button>
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
                <%--<tbody>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>--%>
            </table>
        </section>
    `
)