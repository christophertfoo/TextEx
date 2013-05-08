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

package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.db.ebean.Model;
import controllers.Condition;

/**
 * A {@link Model} representing a request to buy a {@link Book}.
 * 
 * @author Christopher Foo
 * 
 */
@Entity
public class Request extends Model {

  /**
   * Automatically generated ID number.
   */
  private static final long serialVersionUID = 7490409966063340450L;

  /**
   * Gets a {@link Finder} used to query the {@link Request}s table.
   * 
   * @return A Finder to be used with the Requests table.
   */
  public static Finder<Long, Request> find() {
    return new Finder<>(Long.class, Request.class);
  }

  /**
   * The {@link Book} that this {@link Request} is asking for.
   */
  @Required
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Book book;

  /**
   * The target {@link Condition} of the {@link Book}. Defaults to null if not provided.
   */
  private Condition condition;

  /**
   * The target price of the {@link Book}.
   */
  @Required
  @Min(0)
  private double price;

  /**
   * The row ID number and primary key of this {@link Request}.
   */
  @Id
  private Long primaryKey;

  /**
   * The number of {@link Book}s to be purchased.
   */
  @Required
  @Min(0)
  private int quantity;

  /**
   * The natural ID of this {@link Request}.
   */
  @Required
  @MinLength(0)
  @Column(unique = true)
  private String requestId;

  /**
   * The {@link Student} who submitted this {@link Request}.
   */
  @Required
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Student student;

  /**
   * Creates a new {@link Request} with the given values. Uses the default condition.
   * 
   * @param requestId The natural ID of the Request.
   * @param student The {@link Student} who posted the Request.
   * @param book The {@link Book} to be purchased.
   * @param price The target price of the Book(s).
   * @param quantity The number Books to purchase.
   */
  public Request(String requestId, Student student, Book book, double price, int quantity) {
    this(requestId, student, book, price, quantity, null);
  }

  /**
   * Creates a new {@link Request} with the given values.
   * 
   * @param requestId The natural ID of the Request.
   * @param student The {@link Student} who posted the Request.
   * @param book The {@link Book} to be purchased.
   * @param price The target price of the Book(s).
   * @param quantity The number Books to purchase.
   * @param condition The desired {@link Condition} of the Books.
   */
  public Request(String requestId, Student student, Book book, double price, int quantity,
      Condition condition) {
    this.requestId = requestId;
    this.student = student;
    this.book = book;
    this.condition = condition;
    this.price = price;
    this.quantity = quantity;
  }

  /**
   * Gets the {@link #book} to be purchased.
   * 
   * @return The book to be purchased.
   */
  public Book getBook() {
    return this.book;
  }

  /**
   * Gets the desired {@link #condition} of the {@link #book}.
   * 
   * @return The desired condition of the book.
   */
  public Condition getCondition() {
    return this.condition;
  }

  /**
   * Gets the target {@link #price} of the {@link #book}.
   * 
   * @return The target price of the book.
   */
  public double getPrice() {
    return this.price;
  }

  /**
   * Gets the {@link #primaryKey} number of this {@link Request}.
   * 
   * @return The id number of this Request.
   */
  public Long getPrimaryKey() {
    return this.primaryKey;
  }

  /**
   * Gets the number of {@link #book}s to be purchased ({@link #quantity}).
   * 
   * @return The number of books to be purchased.
   */
  public int getQuantity() {
    return this.quantity;
  }

  /**
   * Gets the natural ID of this {@link Request}.
   * 
   * @return The current natural ID of this Request.
   */
  public String getRequestId() {
    return this.requestId;
  }

  /**
   * Gets the {@link #student} who submitted this {@link Request}.
   * 
   * @return The student show submitted this Request.
   */
  public Student getStudent() {
    return this.student;
  }

  /**
   * Sets the {@link #book} to be purchased.
   * 
   * @param book The new book.
   */
  public void setBook(Book book) {
    this.book = book;
  }

  /**
   * Updates the desired {@link #condition} of the {@link #book}.
   * 
   * @param condition The new desired condition of the book.
   */
  public void setCondition(Condition condition) {
    this.condition = condition;
  }

  /**
   * Updates the target {@link #price} of the {@link #book}.
   * 
   * @param price The new target price of the book.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Updates the number of {@link #book}s to be purchased ({@link #quantity}).
   * 
   * @param quantity The new number of books to purchase.
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Sets the natural ID of this {@link Request}.
   * 
   * @param requestId The new natural ID.
   */
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  /**
   * Sets the {@link #student} who submitted this {@link Request}.
   * 
   * @param student The new student.
   */
  public void setStudent(Student student) {
    this.student = student;
  }

  /**
   * Returns the {@link String} representation of this {@link Request}.
   */
  @Override
  public String toString() {
    return String.format("[Request %s %s %s %s %f %d]", this.requestId, this.student.toString(),
        this.book.toString(), (this.condition == null) ? "NULL" : this.condition.toString(),
        this.price, this.quantity);
  }

  /**
   * Validates the fields of this {@link Offer}.
   * 
   * @return A {@link List} of {@link ValidationError}s describing the problems with this Offer or
   * null if there are no errors.
   */
  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();
    if (Request.find().where().eq("requestId", this.requestId).findUnique() != null) {
      errors.add(new ValidationError("AlreadyExists", String.format(
          "An request with ID '%s' already exists in the database.", this.requestId)));
    }

    if (this.price < 0) {
      errors.add(new ValidationError("InvalidPrice", String.format(
          "The price of the request must be > than 0.  Given: %f", this.price)));
    }
    return (errors.size() == 0) ? null : errors;
  }

}
