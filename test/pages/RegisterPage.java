package pages;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends FluentPage {
  private String url;  
  
  public RegisterPage (WebDriver webDriver, int port) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/register";
  }
  
  @Override
  public String getUrl() {
    return this.url;
  }
  
  @Override
  public void isAt() {
    assert(this.title().equals("Register for TextEx"));
  }
  
  public void validInfo() {
      fill("#firstName").with("Test");
      fill("#lastName").with("Student");
      fill("#userName").with("tests");
      fill("#email").with("test@hawaii.edu");
      fill("#password").with("passwordlong");
      fill("#confirmPassword").with("passwordlong");
      click("#registerSubmit");
      assert(text("#messageAlert").get(0).contains("Successfully registered!"));
  }
  
  public void invalidInfo() {
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

  }
  
  public void gotoHome() {
      click("#homeNav");
      assert(this.title().equals("Welcome to TextEx"));
  }
  
  private void checkError() {
      click("#registerSubmit");
      assert(text("#messageAlert").get(0).contains("There are errors in the form."));
  }
  
  private void clearForm() {
      clear("#firstName");
      clear("#lastName");
      clear("#userName");
      clear("#email");
      clear("#password");
      clear("#confirmPassword");
  }
}
