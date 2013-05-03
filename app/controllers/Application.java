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
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.register;

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
    return ok(index.render(null, new DynamicForm(), false));
  }
  
  public static Result login() {
    DynamicForm loginForm = Form.form().bindFromRequest();
    if(!loginForm.get("email").equals("test@hawaii.edu") && !loginForm.get("password").equals("password")){
      return badRequest(index.render(null, loginForm, true));
    }
    return ok(index.render(new models.Student("tet", "test", "student", "test@hawaii.edu", "password"), new DynamicForm(), false));
  }
  
  public static Result register() {
    return ok(views.html.register.render(null, new DynamicForm(), false, new Form<models.Student>(models.Student.class), false, false));
  }

}
