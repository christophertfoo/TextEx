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

package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.register;
import views.html.search;
import views.html.add;
import views.html.myoffers;

/**
 * The main {@link Controller} for the TextEx application.
 * 
 * @author Christopher Foo
 * 
 */
public class Application extends Controller {

  /**
   * The default landing page for the top level of the TextEx application.
   * 
   * @return A 200 {@link Status} with the default top level page.
   */
  public static Result index() {
    return ok(index.render(new DynamicForm()));
  }
  
  /**
   * Handles the login action for the application.
   * 
   * @return A 200 {@link Status} if successfully logged in or a 400 Status if the login failed.
   */
  public static Result login() {
    DynamicForm loginForm = Form.form().bindFromRequest();
    models.Student user = models.Student.find().where().eq("email", loginForm.get("email")).eq("password", Crypto.encryptAES(loginForm.get("password"))).findUnique();
    if(user == null) {
        user = models.Student.find().where().eq("studentId", loginForm.get("email")).eq("password", Crypto.encryptAES(loginForm.get("password"))).findUnique();
    }
    if(user == null){
        flash("loginFail", "t");
      return badRequest(index.render(loginForm));
    }
    session("username", user.getStudentId());
    return ok(index.render(new DynamicForm()));
  }
  
  public static Result logout() {
      session().clear();
      return ok(index.render(new DynamicForm()));
  }
  
  /**
   * Takes the requester to the default register page.
   * 
   * @return A 200 {@link Status} to the default register page.
   */
  public static Result register() {
    return ok(register.render(new DynamicForm(), new Form<models.Student>(models.Student.class), false, false));
  }
  
  /**
   * Takes the requester to the default search / browse books page.
   * 
   * @return A 200 {@link Status} to the default search / browse books page.
   */
  public static Result search() {
      return ok(search.render(new DynamicForm(), new DynamicForm(), null));
  }
  
  /**
   * Takes the requester to the default add book page.
   * 
   * @return A 200 {@link Status} to the default add book page.
   */
  public static Result addBook() {
      return ok(add.render(new DynamicForm(), new Form<models.Book>(models.Book.class), false, false));
  }
  
  public static Result myOffers() {
      return ok(myoffers.render(new DynamicForm(), null, null));
  }
}
