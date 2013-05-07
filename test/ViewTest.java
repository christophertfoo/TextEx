import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import org.junit.Test;

import pages.AddPage;
import pages.IndexPage;
import pages.RegisterPage;
import pages.SearchPage;
import play.libs.F.Callback;
import play.test.TestBrowser;


public class ViewTest {

  @Test
  public void testIndexPage() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        IndexPage homePage = new IndexPage(browser.getDriver(), 3333);
        browser.goTo(homePage);
        homePage.isAt();
        homePage.gotoRegister();
      }
    });
  }
  
  @Test
  public void testRegisterPage() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        RegisterPage register = new RegisterPage(browser.getDriver(), 3333);
        browser.goTo(register);
        register.isAt();
        register.validInfo();
        register.invalidInfo();
        register.gotoHome();
      }
    });
  }
  
  @Test
  public void testAddSearchPage() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        AddPage addpage = new AddPage(browser.getDriver(), 3333);
        browser.goTo(addpage);
        addpage.isAt();
        addpage.validAdd();
        addpage.invalidAdd();
        SearchPage searchpage = new SearchPage(browser.getDriver(), 3333);
        browser.goTo(searchpage);
        searchpage.testSearch();
      }
    });
  }

}
