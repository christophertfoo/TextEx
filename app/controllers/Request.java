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
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * The {@link Controller} for the {@link models.Request} type.
 * 
 * @author Christopher Foo
 * 
 */
public class Request extends Controller {

  /**
   * Gets the information for all {@link models.Request} currently in the database.
   * 
   * @return A 200 {@link Status} containing the information for all Requests in the database.
   */
  public static Result index() {
    List<models.Request> requests = models.Request.find().findList();
    return ok((requests.size() == 0) ? "No requests" : requests.toString());
  }

  /**
   * Gets the information for the given {@link models.Request}.
   * 
   * @param requestId The ID of the target Request.
   * @return A 200 {@link Status} containing the target Request's information or a 404 Status if it
   * is not in the database.
   */
  public static Result details(String requestId) {
    models.Request request = models.Request.find().where().eq("requestId", requestId).findUnique();
    return (request == null) ? notFound("No request found") : ok(request.toString());
  }

  /**
   * Creates a new {@link models.Request} from the data in the POST request and adds it to the
   * database.
   * 
   * @return A 200 {@link Status} containing the created Request or a 400 Status if the provided
   * information is incorrect.
   */
  public static Result newRequest() {
    Helpers.registerBinders();

    Form<models.Request> requestForm = Form.form(models.Request.class).bindFromRequest();
    StringBuilder errorString = new StringBuilder();
    if (requestForm.data().get("price") == null) {
      errorString.append("MissingPrice: The price for the request is missing.\n");
    }
    if (requestForm.hasErrors() || errorString.length() > 0) {
      errorString.append(Helpers.generateErrorString(requestForm));

      return badRequest(errorString.toString());
    }

    models.Request request = requestForm.get();
    request.save();
    return ok(request.toString());
  }

  /**
   * Deletes the given {@link models.Request} from the database. Does nothing if the Request is not
   * in the database.
   * 
   * @param requestId The ID of the target Request.
   * @return A 200 {@link Status}.
   */
  public static Result delete(String requestId) {
    models.Request request = models.Request.find().where().eq("requestId", requestId).findUnique();
    if (request != null) {
      request.delete();
    }
    return ok();
  }
}
