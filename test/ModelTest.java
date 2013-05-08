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

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import java.util.List;
import models.Book;
import models.Offer;
import models.Request;
import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.FakeApplication;
import controllers.Condition;

/**
 * Tests the models of the TextEx Play application.
 * 
 * @author Christopher Foo
 * 
 */
public class ModelTest {

  /**
   * The {@link FakeApplication} used to run the tests.
   */
  public FakeApplication application;

  /**
   * Start the application before each test.
   */
  @Before
  public void startApp() {
    this.application = fakeApplication(inMemoryDatabase());
    start(this.application);
  }

  /**
   * Stop the application after each test.
   */
  @After
  public void stopApp() {
    stop(this.application);
  }

  /**
   * Test each of the models.
   */
  @Test
  public void testModel() {
    // Create 2 Books and 2 Students
    Book book1 = new Book("123412321", "Test Book 1", "Dude", "UHM Publishing", 50.32);
    Book book2 = new Book("934323421", "Test Book 2", "Chick", "UHM Publishing", 100.99, 4);
    Student student1 = new Student("Student-01", "Tester", "1", "test1@hawaii.edu", "password");
    Student student2 = new Student("Student-02", "Tester", "2", "test2@hawaii.edu", "password");

    // Create 2 Requests and 2 Offers
    Request request1 =
        new Request("Request-01", student1, book1, 45.00, 1, Condition.SLIGHTLY_USED);
    Request request2 = new Request("Request-02", student2, book2, 95.00, 1, Condition.NEW);

    Offer offer1 = new Offer("Offer-01", student1, book2, Condition.SLIGHTLY_USED, 90.00, 1);
    Offer offer2 = new Offer("Offer-02", student2, book1, Condition.HEAVILY_USED, 30.00, 1);

    // Persist the sample model by saving all entities and relationships
    book1.save();
    book2.save();
    student1.save();
    student2.save();
    request1.save();
    request2.save();
    offer1.save();
    offer2.save();

    // Retrieve the entire model from the database.
    List<Book> books = Book.find().findList();
    List<Student> students = Student.find().findList();
    List<Request> requests = Request.find().findList();
    List<Offer> offers = Offer.find().findList();

    // Check that we've recovered all our entities.
    assertEquals("Checking books", books.size(), 2);
    assertEquals("Checking students", students.size(), 2);
    assertEquals("Checking requests", requests.size(), 2);
    assertEquals("Checking offers", offers.size(), 2);

    // Check that we've recovered all relationships.
    assertEquals("Book-Offer", books.get(0).getOffers().get(0), offers.get(1));
    assertEquals("Offer-Book", offers.get(0).getBook(), books.get(1));
    assertEquals("Book-Offer", books.get(1).getOffers().get(0), offers.get(0));
    assertEquals("Offer-Book", offers.get(1).getBook(), books.get(0));

    assertEquals("Book-Request", books.get(0).getRequests().get(0), requests.get(0));
    assertEquals("Request-Book", requests.get(0).getBook(), books.get(0));
    assertEquals("Book-Request", books.get(1).getRequests().get(0), requests.get(1));
    assertEquals("Request-Book", requests.get(1).getBook(), books.get(1));

    assertEquals("Student-Offer", students.get(0).getOffers().get(0), offers.get(0));
    assertEquals("Offer-Student", offers.get(0).getStudent(), students.get(0));
    assertEquals("Student-Offer", students.get(1).getOffers().get(0), offers.get(1));
    assertEquals("Offer-Student", offers.get(1).getStudent(), students.get(1));

    assertEquals("Student-Request", students.get(0).getRequests().get(0), requests.get(0));
    assertEquals("Request-Student", requests.get(0).getStudent(), students.get(0));
    assertEquals("Student-Request", students.get(1).getRequests().get(0), requests.get(1));
    assertEquals("Request-Student", requests.get(1).getStudent(), students.get(1));

    // Check deletions
    books.get(0).delete();
    assertEquals("Check deleted book", 1, Book.find().findList().size());
    requests = Request.find().findList();
    assertEquals("Check cascaded delete", 1, requests.size());

    requests.get(0).delete();
    assertEquals("Check deleted request", 0, Request.find().findList().size());
    assertEquals("Check that delete did NOT cascade", 2, Student.find().findList().size());

  }
}
