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

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import play.data.Form;
import play.data.format.Formatters;
import play.data.validation.ValidationError;

/**
 * Contains helper functions to be used in the other controllers classes.
 * 
 * @author Christopher Foo
 * 
 */
public final class Helpers {

  /**
   * Tracks whether the custom data binders have been registered to prevent unnecessary registering.
   */
  private static boolean bindersRegistered = false;

  /**
   * Creates a {@link String} describing the errors found in the given {@link Form}.
   * 
   * @param form The Form containing the errors.
   * @return A String describing the errors in the form.
   */
  public static String generateErrorString(Form<?> form) {
    Map<String, List<ValidationError>> errors = form.errors();
    StringBuilder errorString = new StringBuilder();
    for (String errorKey : errors.keySet()) {
      List<ValidationError> errorList = errors.get(errorKey);
      for (ValidationError error : errorList) {
        errorString.append(errorKey + ": " + error.toString() + "\n");
      }
    }
    return errorString.toString();
  }

  /**
   * Registers the data binders (see: {@link Formatters}) used by the TextEx application's
   * controllers.
   */
  public static void registerBinders() {
    if (!Helpers.bindersRegistered) {

      // Register Book formatter
      Formatters.register(models.Book.class, new Formatters.SimpleFormatter<models.Book>() {

        @Override
        public models.Book parse(String isbn, Locale locale) throws ParseException {
          return models.Book.find().where().eq("isbn", isbn).findUnique();
        }

        @Override
        public String print(models.Book book, Locale locale) {
          return book.toString();
        }

      });

      // Register Student formatter
      Formatters.register(models.Student.class, new Formatters.SimpleFormatter<models.Student>() {

        @Override
        public models.Student parse(String studentId, Locale locale) throws ParseException {
          return models.Student.find().where().eq("studentId", studentId).findUnique();
        }

        @Override
        public String print(models.Student student, Locale locale) {
          return student.toString();
        }

      });

      // Register Condition formatter
      Formatters.register(Condition.class, new Formatters.SimpleFormatter<Condition>() {

        @Override
        public Condition parse(String condition, Locale locale) throws ParseException {
          switch (condition) {
          case "N":
            return Condition.NEW;
          case "S":
            return Condition.SLIGHTLY_USED;
          case "H":
            return Condition.HEAVILY_USED;
          default:
            return null;
          }
        }

        @Override
        public String print(Condition condition, Locale locale) {
          return condition.toString();
        }

      });
    }
  }

  /**
   * Private constructor to prevent instantiation of this class.
   */
  private Helpers() {
    // Empty Private constructor to prevent instantiation.
  }
}
