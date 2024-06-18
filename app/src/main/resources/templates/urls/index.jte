@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`
        <section class="container-lg pt-5">
            <p class="h1 mb-3">Сайты</p>
            <table class="table table-bordered text-dark table-hover">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th class="col-8">Имя</th>
                    <th class="col-2">Последняя проверка</th>
                    <th class="col-1">Код ответа</th>
                </tr>
                </thead>
                <tbody>
                @for(var url : page.getUrls())
                    <tr>
                        <td>${url.getId()}</td>
                        <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                        <td></td>
                        <td></td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </section>
    `
)