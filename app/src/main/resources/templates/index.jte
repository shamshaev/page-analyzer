@template.layout.page(
    content = @`
        <section class="container-fluid bg-dark p-5">
            <div class="col-md-10 col-lg-8 mx-auto px-lg-1">
                <p class="display-3 text-white m-0">Анализатор страниц</p>
                <p class="fs-5 fw-light text-white">Бесплатно проверяйте сайты на SEO пригодность</p>
                <form class="d-flex form-floating" role="search">
                    <input class="form-control me-4" type="check" id="floatingLink" placeholder="Ссылка">
                    <label for="floatingLink">Ссылка</label>
                    <button class="btn btn-primary btn-lg px-sm-5" type="submit">Проверить</button>
                </form>
                <p class="text-secondary pt-2 m-0">Пример: https://www.example.com</p>
            </div>
        </section>
    `
)