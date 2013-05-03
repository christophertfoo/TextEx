import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import org.junit.Test;
import pages.IndexPage;
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

}
