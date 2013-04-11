package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import com.avaje.ebean.validation.Range;
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
   * The row ID number and primary key of this {@link Offer}.
   */
  @Id
  public Long id;

  /**
   * The {@link Student} who submitted this {@link Offer}.
   */
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  public Student student;

  /**
   * The {@link Book} that is for sale.
   */
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  public Book book;

  /**
   * The current {@link Condition} of the {@link Book}(s).
   */
  public Condition condition;

  /**
   * The asking price of the seller.
   */
  @Range(min = 0)
  public double price;

  /**
   * The number of {@link Book}s for sale.
   */
  @Range(min = 0)
  public int quantity;

  /**
   * Creates a new {@link Offer} with the given values.
   * 
   * @param student The {@link Student} who submitted the Offer.
   * @param book The {@link Book} for sale.
   * @param condition The {@link Condition} of the Book(s).
   * @param price The asking price for the Book(s).
   * @param quantity The number of Books for sale.
   */
  public Offer(Student student, Book book, Condition condition, double price, int quantity) {
    this.student = student;
    this.book = book;
    this.condition = condition;
    this.price = price;
    this.quantity = quantity;
  }

  /**
   * Creates a {@link Finder} used to query the {@link Offer} table.
   * 
   * @return A Finder to be used with the Offer table.
   */
  public static Finder<Long, Offer> find() {
    return new Finder<>(Long.class, Offer.class);
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
   * Updates the current {@link #condition} of the {@link Book}(s) for sale.
   * 
   * @param condition The new Condition of the Book(s).
   */
  public void setCondition(Condition condition) {
    this.condition = condition;
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
   * Updates the asking {@link #price} for the {@link Book}(s).
   * 
   * @param price The new asking price.
   */
  public void setPrice(double price) {
    this.price = price;
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
   * Updates the number ({@link #quantity}) of {@link Book}s for sale.
   * 
   * @param quantity The new number of Books for sale.
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the {@link #id} number of this {@link Offer}.
   * 
   * @return The id number of this Offer.
   */
  public Long getId() {
    return this.id;
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
   * Gets the {@link #book} that is for sale in this {@link Offer}.
   * 
   * @return The book for sale.
   */
  public Book getBook() {
    return this.book;
  }
}
