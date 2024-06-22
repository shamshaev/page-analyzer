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
                        <th scope="row" class="col-5 fw-normal">ID</th>
                        <td class="col-7">${page.getUrl().getId()}</td>
                    </tr>
                    <tr>
                        <th scope="row" class="fw-normal">Имя</th>
                        <td>${page.getUrl().getName()}</td>
                    </tr>
                    <tr>
                        <th scope="row" class="fw-normal">Дата создания</th>
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
                        <th scope="col" class="col-1">ID</th>
                        <th scope="col" class="col-1">Код ответа</th>
                        <th scope="col">title</th>
                        <th scope="col">h1</th>
                        <th scope="col">description</th>
                        <th scope="col" class="col-2">Дата проверки</th>
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