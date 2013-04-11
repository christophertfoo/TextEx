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
import controllers.Condition;
import play.test.FakeApplication;

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
    Book book1 = new Book("123412321", "Test Book 1", 50.32);
    Book book2 = new Book("934323421", "Test Book 2", 100.99, 4);
    Student student1 = new Student("Tester", "1", "test1@hawaii.edu");
    Student student2 = new Student("Tester", "2", "test2@hawaii.edu");

    // Create 2 Requests and 2 Offers
    Request request1 = new Request(student1, book1, 45.00, 1, Condition.SLIGHTLY_USED);
    Request request2 = new Request(student2, book2, 95.00, 1, Condition.NEW);

    Offer offer1 = new Offer(student1, book2, Condition.SLIGHTLY_USED, 90.00, 1);
    Offer offer2 = new Offer(student2, book1, Condition.HEAVILY_USED, 30.00, 1);

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
    assertEquals("Book-Offer", books.get(0).offers.get(0), offers.get(1));
    assertEquals("Offer-Book", offers.get(0).book, books.get(1));
    assertEquals("Book-Offer", books.get(1).offers.get(0), offers.get(0));
    assertEquals("Offer-Book", offers.get(1).book, books.get(0));

    assertEquals("Book-Request", books.get(0).requests.get(0), requests.get(0));
    assertEquals("Request-Book", requests.get(0).book, books.get(0));
    assertEquals("Book-Request", books.get(1).requests.get(0), requests.get(1));
    assertEquals("Request-Book", requests.get(1).book, books.get(1));

    assertEquals("Student-Offer", students.get(0).offers.get(0), offers.get(0));
    assertEquals("Offer-Student", offers.get(0).student, students.get(0));
    assertEquals("Student-Offer", students.get(1).offers.get(0), offers.get(1));
    assertEquals("Offer-Student", offers.get(1).student, students.get(1));

    assertEquals("Student-Request", students.get(0).requests.get(0), requests.get(0));
    assertEquals("Request-Student", requests.get(0).student, students.get(0));
    assertEquals("Student-Request", students.get(1).requests.get(0), requests.get(1));
    assertEquals("Request-Student", requests.get(1).student, students.get(1));
  }
}
