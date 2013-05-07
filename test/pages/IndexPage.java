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


package pages;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

/**
 * {@link FluentPage} used to test the index page of the TextEx application.
 * @author Christopher Foo
 *
 */
public class IndexPage extends FluentPage {
  
    /**
     * The URL of the page.
     */
  private String url;
  
  /**
   * Creates a new {@link IndexPage}.
   * @param webDriver The {@link WebDriver} used to open the page.
   * @param port The port number of the localhost server.
   */
  public IndexPage (WebDriver webDriver, int port) {
    super(webDriver);
    this.url = "http://localhost:" + port;
  }
  
  /**
   * Checks if the page is currently at the index page.
   */
  @Override
  public void isAt() {
    assert(this.title().equals("Welcome to TextEx"));
  }
  
  /**
   * Gets the URL of the page.
   */
  @Override
  public String getUrl() {
    return this.url;
  }
  
  /**
   * Goes to the register page by clicking the sign-in button and the register link.
   */
  public void gotoRegister() {
    click("#signinButton");
    click("#registerLink");
    assertTrue("Should be at register page", this.title().equals("Register for TextEx"));
  }
}
