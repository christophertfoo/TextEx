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
 * A {@link FluentPage} used to facilitate testing of the register page of the TextEx application.
 * @author Christopher Foo
 *
 */
public class RegisterPage extends FluentPage {
    
  /**
   * The URL of the page.
   */
  private String url;  
  
  /**
   * Creates a new {@link RegisterPage}.
   * @param webDriver The {@link WebDriver} used by this page.
   * @param port The port of the localhost.
   */
  public RegisterPage (WebDriver webDriver, int port) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/register";
  }
  
  /**
   * Gets the URL of this {@link RegisterPage}.
   */
  @Override
  public String getUrl() {
    return this.url;
  }
  
  /**
   * Determines if this page is currently at the register page.
   */
  @Override
  public void isAt() {
    assert(this.title().equals("Register for TextEx"));
  }
  
  /**
   * Adds a test student to the database through the register page.
   */
  public void addTestStudent() {
      fill("#firstName").with("Test");
      fill("#lastName").with("Student");
      fill("#userName").with("tests");
      fill("#email").with("test@hawaii.edu");
      fill("#password").with("passwordlong");
      fill("#confirmPassword").with("passwordlong");
      click("#registerSubmit");
  }
  
  /**
   * Logs in as the test student from the register page's signin form.
   */
  public void loginAsTest() {
      click("#signinButton");
      fill("#loginEmail").with("test@hawaii.edu");
      fill("#loginPassword").with("passwordlong");
      click("#loginSubmit");
  }
  
  /**
   * Tests that a user is successfully added when valid information is given.
   */
  public void validInfo() {
      this.addTestStudent();
      assertTrue(this.pageSource().contains("Successfully registered!"));
      assertTrue(models.Student.find().findList().size() == 1);
  }
  
  /**
   * Tests that a user is not added and an error message is raised when invalid information is
   * given.
   */
  public void invalidInfo() {
      
      int numInDb = models.Student.find().findList().size();
      
      this.clearForm();
      this.checkError();
      
      // Missing first name
      fill("#lastName").with("Student");
      fill("#userName").with("tests");
      fill("#email").with("test@hawaii.edu");
      fill("#password").with("passwordlong");
      fill("#confirmPassword").with("passwordlong");
      this.checkError();
      
      // Missing last name
      this.clearForm();
      fill("#firstName").with("Test");
      fill("#userName").with("tests");
      fill("#email").with("test@hawaii.edu");
      fill("#password").with("passwordlong");
      fill("#confirmPassword").with("passwordlong");
      this.checkError();
      
      // Missing user name
      this.clearForm();
      fill("#firstName").with("Test");
      fill("#lastName").with("Student");
      fill("#email").with("test@hawaii.edu");
      fill("#password").with("passwordlong");
      fill("#confirmPassword").with("passwordlong");
      this.checkError();
      
      // Invalid email
      this.clearForm();
      fill("#firstName").with("Test");
      fill("#lastName").with("Student");
      fill("#userName").with("tests");
      fill("#email").with("test");
      fill("#password").with("passwordlong");
      fill("#confirmPassword").with("passwordlong");
      this.checkError();
      
      // Missing email
      this.clearForm();
      fill("#firstName").with("Test");
      fill("#lastName").with("Student");
      fill("#userName").with("tests");
      fill("#password").with("passwordlong");
      fill("#confirmPassword").with("passwordlong");
      this.checkError();
      
      // Password too short
      this.clearForm();
      fill("#firstName").with("Test");
      fill("#lastName").with("Student");
      fill("#userName").with("tests");
      fill("#email").with("test@hawaii.edu");
      fill("#password").with("password");
      fill("#confirmPassword").with("password");
      this.checkError();
      
      // Missing password
      this.clearForm();
      fill("#firstName").with("Test");
      fill("#lastName").with("Student");
      fill("#userName").with("tests");
      fill("#email").with("test@hawaii.edu");
      fill("#confirmPassword").with("passwordlong");
      this.checkError();
      
      // Passwords do not match
      // TODO Implement check
      
      // Duplicate user names
      this.clearForm();
      fill("#firstName").with("Another");
      fill("#lastName").with("Student");
      fill("#userName").with("tests");
      fill("#email").with("test2@hawaii.edu");
      fill("#password").with("passwordlong");
      fill("#confirmPassword").with("passwordlong");
      this.checkError();
      
      assertTrue(numInDb == models.Student.find().findList().size());

  }
  
  /**
   * Tests that the user can go back to the home page from the register page.
   */
  public void gotoHome() {
      click("#homeNav");
      assertTrue(this.title().equals("Welcome to TextEx"));
  }
  
  /**
   * Checks that the invalid information caused an error message to be raised.
   */
  private void checkError() {
      click("#registerSubmit");
      assertTrue(this.pageSource().contains("There are errors in the form."));
  }
  
  /**
   * Clears all of the fields of the register form.
   */
  private void clearForm() {
      clear("#firstName");
      clear("#lastName");
      clear("#userName");
      clear("#email");
      clear("#password");
      clear("#confirmPassword");
  }
}
