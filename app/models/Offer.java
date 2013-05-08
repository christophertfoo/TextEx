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
 * A {@link Model} representing an offer to sell a {@link Book}.
 * 
 * @author Christopher Foo
 * 
 */
@Entity
public class Offer extends Model {

  /**
   * Automatically generated ID number.
   */
  private static final long serialVersionUID = 227738921651011503L;

  /**
   * Creates a {@link Finder} used to query the {@link Offer} table.
   * 
   * @return A Finder to be used with the Offer table.
   */
  public static Finder<Long, Offer> find() {
    return new Finder<>(Long.class, Offer.class);
  }

  /**
   * The {@link Book} that is for sale.
   */
  @Required
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Book book;

  /**
   * The current {@link Condition} of the {@link Book}(s).
   */
  @Required
  private Condition condition;

  /**
   * The natural ID of this {@link Offer}.
   */
  @Required
  @MinLength(0)
  @Column(unique = true)
  private String offerId;

  /**
   * The asking price of the seller.
   */
  @Required
  private double price;

  /**
   * The row ID number and primary key of this {@link Offer}.
   */
  @Id
  private Long primaryKey;

  /**
   * The number of {@link Book}s for sale.
   */
  @Required
  @Min(1)
  private int quantity;

  /**
   * The {@link Student} who submitted this {@link Offer}.
   */
  @Required
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Student student;

  /**
   * Creates a new {@link Offer} with the given values.
   * 
   * @param offerId The natural ID of the Offer.
   * @param student The {@link Student} who submitted the Offer.
   * @param book The {@link Book} for sale.
   * @param condition The {@link Condition} of the Book(s).
   * @param price The asking price for the Book(s).
   * @param quantity The number of Books for sale.
   */
  public Offer(String offerId, Student student, Book book, Condition condition, double price,
      int quantity) {
    this.offerId = offerId;
    this.student = student;
    this.book = book;
    this.condition = condition;
    this.price = price;
    this.quantity = quantity;
  }

  /**
   * Gets the {@link #book} that is for sale in this {@link Offer}.
   * 
   * @return The book for sale.
   */
  public Book getBook() {
    return this.book;
  }

  /**
   * Gets the current {@link #condition} of the {@link Book}(s) for sale.
   * 
   * @return The current Condition of the Book(s).
   */
  public Condition getCondition() {
    return this.condition;
  }

  /**
   * Gets the natural ID of this {@link Offer}.
   * 
   * @return The natural ID of this Offer.
   */
  public String getOfferId() {
    return this.offerId;
  }

  /**
   * Gets the current asking {@link #price} for the {@link Book}(s).
   * 
   * @return The current asking price.
   */
  public double getPrice() {
    return this.price;
  }

  /**
   * Gets the {@link #primaryKey} number of this {@link Offer}.
   * 
   * @return The id number of this Offer.
   */
  public Long getPrimaryKey() {
    return this.primaryKey;
  }

  /**
   * Gets the number ({@link #quantity}) of {@link Book}s for sale.
   * 
   * @return The number of Books for sale.
   */
  public int getQuantity() {
    return this.quantity;
  }

  /**
   * Gets the {@link #student} who submitted this {@link Offer}.
   * 
   * @return The student who submitted this Offer.
   */
  public Student getStudent() {
    return this.student;
  }

  /**
   * Sets the {@link #book} that is for sale in this {@link Offer}.
   * 
   * @param book The new book.
   */
  public void setBook(Book book) {
    this.book = book;
  }

  /**
   * Updates the current {@link #condition} of the {@link Book}(s) for sale.
   * 
   * @param condition The new Condition of the Book(s).
   */
  public void setCondition(Condition condition) {
    this.condition = condition;
  }

  /**
   * Sets the natural ID of this {@link Offer}.
   * 
   * @param offerId The new natural ID of this Offer.
   */
  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  /**
   * Updates the asking {@link #price} for the {@link Book}(s).
   * 
   * @param price The new asking price.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Updates the number ({@link #quantity}) of {@link Book}s for sale.
   * 
   * @param quantity The new number of Books for sale.
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Sets the {@link #student} who submitted this {@link Offer}.
   * 
   * @param student The new student.
   */
  public void setStudent(Student student) {
    this.student = student;
  }

  /**
   * Returns the {@link String} representation of this {@link Offer}.
   */
  @Override
  public String toString() {
    return String.format("[Offer %s %s %s %s %f %d]", this.offerId, this.student.toString(),
        this.book.toString(), this.condition.toString(), this.price, this.quantity);
  }

  /**
   * Validates the fields of this {@link Offer}.
   * 
   * @return A {@link List} of {@link ValidationError}s describing the problems with this Offer or
   * null if there are no errors.
   */
  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();
    if (Offer.find().where().eq("offerId", this.offerId).findUnique() != null) {
      errors.add(new ValidationError("AlreadyExists", String.format(
          "An offer with ID '%s' already exists in the database.", this.offerId)));
    }

    if (this.price < 0) {
      errors.add(new ValidationError("InvalidPrice", String.format(
          "The price of the offer must be > than 0.  Given: %f", this.price)));
    }
    return (errors.size() == 0) ? null : errors;
  }
}
