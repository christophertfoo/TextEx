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
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
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
   * Gets a {@link Finder} that can be used to query the {@link Student}s table.
   * 
   * @return A Finder to be used with the Students table.
   */
  public static Finder<Long, Student> find() {
    return new Finder<>(Long.class, Student.class);
  }

  /**
   * This {@link Student}'s email address.
   */
  @Required
  @Email
  @MinLength(1)
  private String email;

  /**
   * This {@link Student}'s first name.
   */
  @Required
  @MinLength(1)
  private String firstName;

  /**
   * This {@link Student}'s last name.
   */
  @Required
  @MinLength(1)
  private String lastName;

  /**
   * The {@link Offer}s submitted by this {@link Student}.
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
  private List<Offer> offers = new ArrayList<>();

  /**
   * This {@link Student}'s password.
   */
  @Required
  @MinLength(value = 10)
  private String password;

  /**
   * The row ID number and primary key of this {@link Student}.
   */
  @Id
  private Long primaryKey;

  /**
   * The {@link Request}s submitted by this {@link Student}.
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
  private List<Request> requests = new ArrayList<>();

  /**
   * The natural ID of this {@link Student}.
   */
  @Required
  @MinLength(1)
  @Column(unique = true)
  private String studentId;

  /**
   * Creates a new {@link Student} with the given values.
   * 
   * @param studentId The natural ID of the Student.
   * @param firstName The first name of the Student.
   * @param lastName The last name of the Student.
   * @param email The email address of the Student.
   * @param password The password of the Student.
   */
  public Student(String studentId, String firstName, String lastName, String email, String password) {
    this.studentId = studentId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
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
   * Gets the {@link #firstName} of this {@link Student}.
   * 
   * @return This Student's first name.
   */
  public String getFirstName() {
    return this.firstName;
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
   * Gets the {@link #offers} submitted by this {@link Student}.
   * 
   * @return The {@link List} of offers submitted by this Student.
   */
  public List<Offer> getOffers() {
    return this.offers;
  }

  /**
   * Gets the {@link #password} of this {@link Student}.
   * 
   * @return The password of this Student.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Gets the {@link #primaryKey} number of this {@link Student}.
   * 
   * @return The id number of this Student.
   */
  public Long getPrimaryKey() {
    return this.primaryKey;
  }

  /**
   * Gets the {@link #requests} submitted by this {@link Student}.
   * 
   * @return The {@link List} of requests submitted by this Student.
   */
  public List<Request> getRequests() {
    return this.requests;
  }

  /**
   * Gets the current natural ID of this {@link Student}.
   * 
   * @return The current natural ID of this Student.
   */
  public String getStudentId() {
    return this.studentId;
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
   * Updates the {@link #firstName} of this {@link Student}.
   * 
   * @param firstName The new first name of this Student.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
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
   * Sets the {@link #offers} submitted by this {@link Student}.
   * 
   * @param offers The new offers.
   */
  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  /**
   * Sets the {@link #password} of this {@link Student}.
   * 
   * @param password The Student's new password.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Sets the {@link #requests} submitted by this {@link Student}.
   * 
   * @param requests The new requests.
   */
  public void setRequests(List<Request> requests) {
    this.requests = requests;
  }

  /**
   * Sets the natural ID of this {@link Student}.
   * 
   * @param studentId The new natural ID of this Student.
   */
  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  /**
   * Returns the {@link String} representation of this {@link Student}.
   */
  @Override
  public String toString() {
    return String.format("[Student %s %s %s %s]", this.studentId, this.firstName, this.lastName,
        this.email);
  }

  /**
   * Validates this {@link Student}'s fields.
   * 
   * @return A {@link List} of {@link ValidationError}s describing the errors in this Student's
   * fields or null if there are no errors.
   */
  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();
    if (Student.find().where().eq("studentId", this.studentId).findUnique() != null) {
      errors.add(new ValidationError("AlreadyExists", String.format(
          "A Student with ID '%s' already exists.", this.studentId)));
    }
    return (errors.size() == 0) ? null : errors;
  }
}
