package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.util.List;
import java.util.Map;

public final class UrlsPage extends BasePage {
    private List<Url> urls;
    private Map<Long, UrlCheck> lastChecks;

    public UrlsPage(List<Url> urls, Map<Long, UrlCheck> lastChecks) {
        this.urls = urls;
        this.lastChecks = lastChecks;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public Map<Long, UrlCheck> getLastChecks() {
        return lastChecks;
    }
}
