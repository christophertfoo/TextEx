/*
 *   Copyright (C) 2013  Christopher Foo
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * Tests the views of the TextEx application.
 * 
 * @author Christopher Foo
 * 
 */
public class ViewTest {

  /**
   * Tests the add and search pages of the TextEx application. Requires that the login system works.
   */
  @Test
  public void testAddSearchPage() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
        new Callback<TestBrowser>() {
          public void invoke(TestBrowser browser) {
            // Need to login to access the page
            RegisterPage registerPage = new RegisterPage(browser.getDriver(), 3333);
            browser.goTo(registerPage);
            registerPage.addTestStudent();
            registerPage.loginAsTest();

            // Try adding some valid and invalid books
            AddPage addPage = new AddPage(browser.getDriver(), 3333);
            browser.goTo(addPage);
            addPage.isAt();
            addPage.validAdd();
            addPage.invalidAdd();
            addPage.gotoSearch();

            // Make sure the books show up in the search
            SearchPage searchPage = new SearchPage(browser.getDriver(), 3333);
            browser.goTo(searchPage);
            searchPage.testSearch();
          }
        });
  }

  /**
   * Tests the index / landing page of the TextEx application.
   */
  @Test
  public void testIndexPage() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
        new Callback<TestBrowser>() {
          public void invoke(TestBrowser browser) {
            IndexPage homePage = new IndexPage(browser.getDriver(), 3333);
            browser.goTo(homePage);
            homePage.isAt();
            homePage.gotoRegister();
          }
        });
  }

  /**
   * Tests the register page of the TextEx application.
   */
  @Test
  public void testRegisterPage() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
        new Callback<TestBrowser>() {
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

}
