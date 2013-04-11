package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.avaje.ebean.validation.Range;
import play.data.validation.Constraints.Required;
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
  public Long id;

  /**
   * The ISBN of this {@link Book}.
   */
  @Required
  public String isbn;

  /**
   * The name of this {@link Book}.
   */
  @Required
  public String name;

  /**
   * The edition of this {@link Book}. Default value is 1.
   */
  public int edition;

  /**
   * The price of a new copy of this {@link Book} in the bookstore..
   */
  @Range(min = 0)
  public double price;

  /**
   * The {@link Request}s to buy this {@link Book}.
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
  public List<Request> requests = new ArrayList<>();

  /**
   * The {@link Offer}s to sell this {@link Book}.
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
  public List<Offer> offers = new ArrayList<>();

  /**
   * Creates a new {@link Book} with the given values.
   * 
   * @param isbn The ISBN of the Book.
   * @param name The name of the Book.
   * @param price The price of the Book.
   * @param edition The edition of the Book.
   */
  public Book(String isbn, String name, double price, int edition) {
    this.isbn = isbn;
    this.name = name;
    this.price = price;
    this.edition = edition;
  }

  /**
   * Creates a new {@link Book} with the given values and the default edition number of 1.
   * 
   * @param isbn The ISBN of the Book.
   * @param name The name of the Book.
   * @param price The price of the Book.
   */
  public Book(String isbn, String name, double price) {
    this(isbn, name, price, 1);
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
   * Gets the {@link #id} (primary key) of this {@link Book}.
   * 
   * @return The id of this Book.
   */
  public Long getId() {
    return this.id;
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
   * Gets the {{@link #name} of this {@link Book}.
   * 
   * @return The name of this Book.
   */
  public String getName() {
    return this.name;
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
   * Gets the current {@link #price} of a new copy of this {@link Book} at the bookstore.
   * 
   * @return The current price of this Book.
   */
  public double getPrice() {
    return this.price;
  }

  /**
   * Sets the new {@link #price} of this {@link Book}.
   * 
   * @param price The new price of this Book.
   */
  public void setPrice(double price) {
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
   * Gets the {@link #offers} for this {@link Book}. See: {@link Offer}.
   * 
   * @return A {@link List} of Offers to sell this Book.
   */
  public List<Offer> getOffers() {
    return this.offers;
  }
}
