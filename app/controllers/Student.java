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

import java.util.List;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.register;

/**
 * The {@link Controller} for the {@link models.Student} type.
 * 
 * @author Christopher Foo
 * 
 */
public class Student extends Controller {

  /**
   * Gets all {@link models.Student}s currently in the database.
   * 
   * @return A 200 {@link Status} containing all of the students in the database.
   */
  public static Result index() {
    List<models.Student> students = models.Student.find().findList();
    return ok((students.size() == 0) ? "No students" : students.toString());
  }

  /**
   * Gets the information for the given {@link models.Student}.
   * 
   * @param studentId The ID of the target Student.
   * @return A 200 {@link Status} containing the Student's information or a 404 Status if the
   * Student is not in the database.
   */
  public static Result details(String studentId) {
    models.Student student = models.Student.find().where().eq("studentId", studentId).findUnique();
    return (student == null) ? notFound("No student found") : ok(student.toString());
  }

  /**
   * Creates a new {@link models.Student} from the POST request's data and adds it to the database.
   * 
   * @return A 200 {@link Status} to the register page with a success message or a 400 Status to the register page with an error message if
   * the data in the request is incorrect.
   */
  public static Result newStudent() {
    Form<models.Student> studentForm = Form.form(models.Student.class).bindFromRequest();
    if (studentForm.hasErrors()) {
      return badRequest(register.render(new DynamicForm(), studentForm, true, false));
    }

    models.Student student = studentForm.get();
    student.setPassword(Crypto.encryptAES(student.getPassword()));
    student.save();
    return ok(register.render(new DynamicForm(), studentForm, false, true));
  }

  /**
   * Deletes the given {@link models.Student} from the database. Does nothing if the Student is not
   * in the database.
   * 
   * @param studentId The ID of the target Student.
   * @return A 200 {@link Status}.
   */
  public static Result delete(String studentId) {
    models.Student student = models.Student.find().where().eq("studentId", studentId).findUnique();
    if (student != null) {
      student.delete();
    }
    return ok();
  }
}
