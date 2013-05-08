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
 * The {@link Controller} for the {@link models.Offer} type.
 * 
 * @author Christopher Foo
 * 
 */
public class Offer extends Controller {

  /**
   * Deletes the given {@link models.Offer} from the database. Does nothing if the Offer does not
   * exist in the database.
   * 
   * @param offerId The ID of the target Offer.
   * @return A 200 {@link Status}.
   */
  public static Result delete(String offerId) {
    models.Offer offer = models.Offer.find().where().eq("offerId", offerId).findUnique();
    if (offer != null) {
      offer.delete();
    }
    return ok();
  }

  /**
   * Gets the information of the given {@link models.Offer}.
   * 
   * @param offerId The ID of the target Offer.
   * @return A 200 {@link Status} containing the information of the target Offer or a 404 Status if
   * it is not in the database.
   */
  public static Result details(String offerId) {
    models.Offer offer = models.Offer.find().where().eq("offerId", offerId).findUnique();
    return (offer == null) ? notFound("No offer found") : ok(offer.toString());
  }

  /**
   * Gets all of the {@link models.Offer}s currently in the database.
   * 
   * @return A 200 {@link Status} containing the information of all of the Offers in the database.
   */
  public static Result index() {
    List<models.Offer> offers = models.Offer.find().findList();
    return ok((offers.size() == 0) ? "No offers" : offers.toString());
  }

  /**
   * Creates a new {@link models.Offer} using the POST request's data and adds it to the database.
   * 
   * @return A 200 {@link Status} containing the new Offer's information or a 400 Status if there is
   * an error in the given information.
   */
  public static Result newOffer() {

    Helpers.registerBinders();

    Form<models.Offer> offerForm = Form.form(models.Offer.class).bindFromRequest();
    StringBuilder errorString = new StringBuilder();
    if (offerForm.data().get("price") == null) {
      errorString.append("MissingPrice: The price for the offer is missing.\n");
    }
    if (offerForm.hasErrors() || errorString.length() > 0) {
      errorString.append(Helpers.generateErrorString(offerForm));

      return badRequest(errorString.toString());
    }

    models.Offer offer = offerForm.get();
    offer.save();
    return ok(offer.toString());
  }

}
