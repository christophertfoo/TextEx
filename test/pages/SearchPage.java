package pages;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class SearchPage extends FluentPage {
    
    private String url;
    
    public SearchPage(WebDriver driver, int port) {
        super(driver);
        this.url = "http://localhost:" + port + "/books";
    }
    
    @Override
    public String getUrl() {
        return this.url;
    }
    
    @Override
    public void isAt() {
        assert(this.title().equals("Browse Books"));
    }
    
    public void testSearch() {
        click("#searchSubmit");
        assertTrue("Should have 2 Books.", this.pageSource().contains("222-222-2222") && this.pageSource().contains("111-111-1111"));
        
        fill("#title").with("My");
        click("#searchSubmit");
        assertTrue("Should have 1 Books.", !this.pageSource().contains("222-222-2222") && this.pageSource().contains("111-111-1111"));
    }
}
