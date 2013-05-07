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
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.db.ebean.Model;

/**
 * A {@link Model} representing a textbook.
 * 
 * @author Christopher Foo
 * 
 */
@Entity
public class Book extends Model {

  /**
   * Automatically generated ID number.
   */
  private static final long serialVersionUID = -178231088310485181L;

  /**
   * The row ID number and primary key of this {@link Book}.
   */
  @Id
  private Long primaryKey;

  /**
   * The ISBN of this {@link Book}.
   */
  @Required
  @MinLength(1)
  @Column(unique = true)
  private String isbn;

  /**
   * The name of this {@link Book}.
   */
  @Required
  @MinLength(1)
  private String name;

  /**
   * The edition of this {@link Book}. Default value is 1.
   */
  @Min(1)
  private int edition;

  /**
   * The price of a new copy of this {@link Book} in the bookstore..
   */
  @Required
  @Min(0)
  private Double price;
  
  /**
   * The names of the authors of this {@link Book}.
   */
  @Required
  @MinLength(1)
  private String authors;
  
  /**
   * The name of the publisher of this {@link Book}.
   */
  @Required
  @MinLength(1)
  private String publisher;

  /**
   * The {@link Request}s to buy this {@link Book}.
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
  private List<Request> requests = new ArrayList<>();

  /**
   * The {@link Offer}s to sell this {@link Book}.
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
  private List<Offer> offers = new ArrayList<>();

  /**
   * Creates a new {@link Book} with the given values.
   * 
   * @param isbn The ISBN of the Book.
   * @param name The name of the Book.
   * @param authors The names of the authors of the Book.
   * @param publisher The name of the publisher of the Book.
   * @param price The price of the Book.
   * @param edition The edition of the Book.
   */
  public Book(String isbn, String name, String authors, String publisher, Double price, int edition) {
    this.isbn = isbn;
    this.name = name;
    this.price = price;
    this.edition = edition;
    this.authors = authors;
    this.publisher = publisher;
  }

  /**
   * Creates a new {@link Book} with the given values and the default edition number of 1.
   * 
   * @param isbn The ISBN of the Book.
   * @param name The name of the Book.
   * @param authors The names of the authors of the Book.
   * @param publisher The name of the publisher of the Book.
   * @param price The price of the Book.
   */
  public Book(String isbn, String name, String authors, String publisher, Double price) {
    this(isbn, name, authors, publisher, price, 1);
  }

  /**
   * Gets a {@link Finder} that can be used to query the {@link Book}s table.
   * 
   * @return A Finder to be used with the Books table.
   */
  public static Finder<Long, Book> find() {
    return new Finder<>(Long.class, Book.class);
  }

  /**
   * Performs extra validation for this {@link Book}.
   * 
   * @return A {@link List} of {@link ValidationError}s describing the errors or null if there are
   * no errors.
   */
  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();
    if (Book.find().where().eq("isbn", this.isbn).findUnique() != null) {
      errors.add(new ValidationError("AlreadyExists", String.format(
          "A Book with an ISBN of '%s' already exists.", this.isbn)));
    }
    return (errors.size() == 0) ? null : errors;
  }

  /**
   * Returns the {@link String} representation of this {@link Book};
   */
  @Override
  public String toString() {
    return String.format("[Book %s %s %s %s %f %d]", this.isbn, this.name, this.authors, this.publisher, this.price, this.edition);
  }

  /**
   * Gets the {@link #primaryKey} (primary key) of this {@link Book}.
   * 
   * @return The id of this Book.
   */
  public Long getPrimaryKey() {
    return this.primaryKey;
  }

  /**
   * Gets the {@link #isbn} of this {@link Book}.
   * 
   * @return The ISBN of this Book.
   */
  public String getIsbn() {
    return this.isbn;
  }

  /**
   * Sets the {@link #isbn} of this {@link Book} to the given value.
   * 
   * @param isbn The new ISBN of this Book.
   */
  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  /**
   * Gets the {{@link #name} of this {@link Book}.
   * 
   * @return The name of this Book.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the {@link #name} of this {@link Book} to the given value.
   * 
   * @param name The new name of this Book.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the {@link #edition} of this {@link Book}.
   * 
   * @return The edition of this Book.
   */
  public int getEdition() {
    return this.edition;
  }

  /**
   * Sets the {@link #edition} of this {@link Book} to the given value.
   * 
   * @param edition The new edition of this Book.
   */
  public void setEdition(int edition) {
    this.edition = edition;
  }

  /**
   * Gets the current {@link #price} of a new copy of this {@link Book} at the bookstore.
   * 
   * @return The current price of this Book.
   */
  public Double getPrice() {
    return this.price;
  }

  /**
   * Sets the new {@link #price} of this {@link Book}.
   * 
   * @param price The new price of this Book.
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Gets the {@link #requests} for this {@link Book}. See: {@link Request}.
   * 
   * @return A {@link List} of Requests to buy this Book.
   */
  public List<Request> getRequests() {
    return this.requests;
  }

  /**
   * Sets the {@link #requests} for this {@link Book}. See: {@link Request}.
   * 
   * @param requests The new requests for this Book.
   */
  public void setRequests(List<Request> requests) {
    this.requests = requests;
  }

  /**
   * Gets the {@link #offers} for this {@link Book}. See: {@link Offer}.
   * 
   * @return A {@link List} of Offers to sell this Book.
   */
  public List<Offer> getOffers() {
    return this.offers;
  }

  /**
   * Sets the {@link #offers} for this {@link Book}. See: {@link Offer}.
   * 
   * @param offers The new offers for this Book.
   */
  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  /**
   * Gets the names of the {@link #authors} of this {@link Book}.
   * 
   * @return The names of the authors of this Book.
   */
  public String getAuthors() {
    return this.authors;
  }
  
  /**
   * Sets the names of the {@link #authors} of this {@link Book}.
   * 
   * @param authors The names of the new authors.
   */
  public void setAuthors(String authors) {
    this.authors = authors;
  }
  
  /**
   * Gets the name of the {@link #publisher} of this {@link Book}.
   * 
   * @return The name of the publisher of this book.
   */
  public String getPublisher() {
    return this.publisher;
  }
  
  /**
   * Sets the name of the {@link #publisher} of this {@link Book}.
   * 
   * @param publisher The new publisher.
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }
}
