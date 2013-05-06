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
import java.util.Map;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.ExpressionList;

/**
 * The {@link Controller} for the {@link models.Book} type.
 * 
 * @author Christopher Foo
 * 
 */
public class Book extends Controller {

  /**
   * Gets all of the {@link models.Book}s in the database..
   * 
   * @return A 200 {@link Status} containing the Books currently in the database.
   */
  public static Result index() {
    List<models.Book> books = models.Book.find().findList();
    return ok((books.size() == 0) ? "No books" : books.toString());
  }

  /**
   * Gets the information of the {@link models.Book} with the given ISBN.
   * 
   * @param isbn The ISBN of the Book to retrieve.
   * @return A 200 {@link Status} containing the information of the found Book or a 404 Status if
   * the Book cannot be found.
   */
  public static Result details(String isbn) {
    models.Book book = models.Book.find().where().eq("isbn", isbn).findUnique();
    return (book == null) ? notFound("No book found") : ok(book.toString());
  }
  
  public static Result search() {
      DynamicForm bookForm = Form.form().bindFromRequest();
      ExpressionList<models.Book> query = models.Book.find().where();
      
      if(bookForm.get("isbn").length() > 0) {
          query = query.icontains("isbn", bookForm.get("isbn"));
      }
      
      if(bookForm.get("name").length() > 0) {
          query = query.icontains("name", bookForm.get("name"));
      }
      
      if(bookForm.get("authors").length() > 0) {
          query = query.icontains("authors", bookForm.get("authors"));
      }
      
      if(bookForm.get("publisher").length() > 0) {
          query = query.icontains("publisher", bookForm.get("publisher"));
      }
      
      if(bookForm.get("authors").length() > 0) {
          query = query.icontains("authors", bookForm.get("authors"));
      }
      
      if(bookForm.get("edition").length() > 0) {
          try {
              query = query.ge("edition", Integer.parseInt(bookForm.get("edition")));
          }
          catch(NumberFormatException e) {
              // Do nothing.
          }
      }
      
      if(bookForm.get("price").length() > 0) {
          try {
              query = query.le("price", Double.parseDouble(bookForm.get("price")));
          }
          catch (NumberFormatException e) {
              // Do nothing.
          }
      }
      
      List<models.Book> bookList = query.orderBy("isbn").findList();
      return ok(views.html.search.render(null, new DynamicForm(), false, bookForm, bookList));
  }

  /**
   * Creates a new {@link models.Book} from the information provided in the POST request and adds it
   * to the database.
   * 
   * @return A 200 {@link Status} containing the created Book's information or a 400 Status
   * containing the errors found in the provided information.
   */
  public static Result newBook() {
    Form<models.Book> bookForm = Form.form(models.Book.class).bindFromRequest();
    if(bookForm.field("edition").value().length() == 0) {
        Map<String, String> data = bookForm.data();
        data.put("edition", "1");
        bookForm = Form.form(models.Book.class).bind(data);
    }
    if (bookForm.hasErrors()) {
      return badRequest(views.html.add.render(null, new DynamicForm(), false, bookForm, true, false));
    }
    models.Book book = bookForm.get();
    book.save();
    return ok(views.html.add.render(null, new DynamicForm(), false, bookForm, false, true));
  }

  /**
   * Deletes the {@link models.Book} with the given ISBN from the database. If a Book with the given
   * ISBN does not exist, it does nothing.
   * 
   * @param isbn The ISBN of the Book to delete.
   * @return A 200 {@link Status}.
   */
  public static Result delete(String isbn) {
    models.Book book = models.Book.find().where().eq("isbn", isbn).findUnique();
    if (book != null) {
      book.delete();
    }
    return ok();
  }
}
