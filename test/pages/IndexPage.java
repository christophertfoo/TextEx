package pages;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class IndexPage extends FluentPage {
  
  private String url;
  
  public IndexPage (WebDriver webDriver, int port) {
    super(webDriver);
    this.url = "http://localhost:" + port;
  }
  
  @Override
  public void isAt() {
    assert(this.title().equals("Welcome to TextEx"));
  }
  
  @Override
  public String getUrl() {
    return this.url;
  }
  
  public void gotoRegister() {
    click("#signinButton");
    click("#registerLink");
    assert(this.title().equals("Register for TextEx"));
  }
}
