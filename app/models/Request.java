package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.avaje.ebean.validation.Range;
import controllers.Condition;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

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
   * The row ID number and primary key of this {@link Request}.
   */
  @Id
  public Long id;

  /**
   * The {@link Student} who submitted this {@link Request}.
   */
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  public Student student;

  /**
   * The {@link Book} that this {@link Request} is asking for.
   */
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  public Book book;

  /**
   * The target {@link Condition} of the {@link Book}. Defaults to null if not provided.
   */
  public Condition condition;

  /**
   * The target price of the {@link Book}.
   */
  @Range(min = 0)
  public double price;

  /**
   * The number of {@link Book}s to be purchased.
   */
  @Range(min = 0)
  public int quantity;

  /**
   * Creates a new {@link Request} with the given values.
   * 
   * @param student The {@link Student} who posted the Request.
   * @param book The {@link Book} to be purchased.
   * @param price The target price of the Book(s).
   * @param quantity The number Books to purchase.
   * @param condition The desired {@link Condition} of the Books.
   */
  public Request(Student student, Book book, double price, int quantity, Condition condition) {
    this.student = student;
    this.book = book;
    this.condition = condition;
    this.price = price;
    this.quantity = quantity;
  }

  /**
   * Creates a new {@link Request} with the given values. Uses the default condition.
   * 
   * @param student The {@link Student} who posted the Request.
   * @param book The {@link Book} to be purchased.
   * @param price The target price of the Book(s).
   * @param quantity The number Books to purchase.
   */
  public Request(Student student, Book book, double price, int quantity) {
    this(student, book, price, quantity, null);
  }

  /**
   * Gets a {@link Finder} used to query the {@link Request}s table.
   * 
   * @return A Finder to be used with the Requests table.
   */
  public static Finder<Long, Request> find() {
    return new Finder<>(Long.class, Request.class);
  }

  /**
   * Gets the {@link #id} number of this {@link Request}.
   * 
   * @return The id number of this Request.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Gets the {@link #student} who submitted this {@link Request}.
   * 
   * @return The student show submitted this Request.
   */
  public Student getStudent() {
    return student;
  }

  /**
   * Gets the {@link #book} to be purchased.
   * 
   * @return The book to be purchased.
   */
  public Book getBook() {
    return book;
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
   * Updates the desired {@link #condition} of the {@link #book}.
   * 
   * @param condition The new desired condition of the book.
   */
  public void setCondition(Condition condition) {
    this.condition = condition;
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
   * Updates the target {@link #price} of the {@link #book}.
   * 
   * @param price The new target price of the book.
   */
  public void setPrice(double price) {
    this.price = price;
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
   * Updates the number of {@link #book}s to be purchased ({@link #quantity}).
   * 
   * @param quantity The new number of books to purchase.
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

}
