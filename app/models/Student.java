package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * A {@link Model} representing a student.
 * 
 * @author Christopher Foo
 * 
 */
@Entity
public class Student extends Model {

  /**
   * Automatically generated ID number.
   */
  private static final long serialVersionUID = -1729390609083717186L;

  /**
   * The row ID number and primary key of this {@link Student}.
   */
  @Id
  public Long id;

  /**
   * This {@link Student}'s first name.
   */
  @Required
  public String firstName;

  /**
   * This {@link Student}'s last name.
   */
  @Required
  public String lastName;

  /**
   * This {@link Student}'s email address.
   */
  @Required
  @Email
  public String email;

  /**
   * The {@link Request}s submitted by this {@link Student}.
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
  public List<Request> requests = new ArrayList<>();

  /**
   * The {@link Offer}s submitted by this {@link Student}.
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
  public List<Offer> offers = new ArrayList<>();

  /**
   * Creates a new {@link Student} with the given values.
   * 
   * @param firstName The first name of the Student.
   * @param lastName The last name of the Student.
   * @param email The email address of the Student.
   */
  public Student(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  /**
   * Gets a {@link Finder} that can be used to query the {@link Student}s table.
   * 
   * @return A Finder to be used with the Students table.
   */
  public static Finder<Long, Student> find() {
    return new Finder<>(Long.class, Student.class);
  }

  /**
   * Gets the {@link #firstName} of this {@link Student}.
   * 
   * @return This Student's first name.
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Updates the {@link #firstName} of this {@link Student}.
   * 
   * @param firstName The new first name of this Student.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the {@link #lastName} of this {@link Student}.
   * 
   * @return The last name of this Student.
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Updates the {@link #lastName} of this {@link Student}.
   * 
   * @param lastName The new last name of this Student.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the {@link #email} address of this {@link Student}.
   * 
   * @return The email address of this Student.
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Updates the {@link #email} address of this {@link Student}.
   * 
   * @param email The new email address of this Student.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the {@link #requests} submitted by this {@link Student}.
   * 
   * @return The {@link List} of requests submitted by this Student.
   */
  public List<Request> getRequests() {
    return requests;
  }

  /**
   * Gets the {@link #offers} submitted by this {@link Student}.
   * 
   * @return The {@link List} of offers submitted by this Student.
   */
  public List<Offer> getOffers() {
    return offers;
  }

  /**
   * Gets the {@link #id} number of this {@link Student}.
   * 
   * @return The id number of this Student.
   */
  public Long getId() {
    return id;
  }
}
