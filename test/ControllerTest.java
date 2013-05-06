import java.util.HashMap;
import java.util.Map;
import models.Book;
import models.Offer;
import models.Request;
import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import controllers.Condition;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import static play.test.Helpers.status;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;

/**
 * Tests the controllers for the TextEx application.
 * 
 * @author Christopher Foo
 * 
 */
public class ControllerTest {

  /**
   * A {@link FakeApplication} used to run the tests.
   */
  private FakeApplication application;

  /**
   * Start the {@link FakeApplication} before every test.
   */
  @Before
  public void startApp() {
    this.application = fakeApplication(inMemoryDatabase());
    start(this.application);
  }

  /**
   * Stop the {@link FakeApplication} after every test.
   */
  @After
  public void stopApp() {
    stop(this.application);
  }

  /**
   * Tests the {@link controllers.Book} controller.
   */
  @Test
  public void testBookController() {
    // Test GET /books on an empty database.
    Result result = callAction(controllers.routes.ref.Book.index());
    assertTrue("Empty books", contentAsString(result).contains("No books"));

    // Test GET /books on a database containing a single book.
    String isbn = "11111-11-111";
    Book book = new Book(isbn, "Test Book", "Dude", "Awesome Publishing", 20.99);
    book.save();
    result = callAction(controllers.routes.ref.Book.index());
    assertTrue("One book", contentAsString(result).contains(isbn));

    // Test GET /books/11111-11-111
    result = callAction(controllers.routes.ref.Book.details(isbn));
    assertTrue("Book detail", contentAsString(result).contains(isbn));

    // Test GET /books/BadIsbn and make sure we get a 404
    result = callAction(controllers.routes.ref.Book.details("BadIsbn"));
    assertEquals("Book detail (bad)", NOT_FOUND, status(result));

    // Test POST /books (with simulated, valid form data).
    Map<String, String> bookData = new HashMap<>();
    bookData.put("isbn", "22222-22-222");
    bookData.put("name", "Test Book 2");
    bookData.put("price", "19.99");
    bookData.put("edition", "4");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create new book", OK, status(result));

    // Test POST /books (negative price)
    bookData.clear();
    bookData.put("isbn", "33333-33-333");
    bookData.put("name", "Bad Book");
    bookData.put("price", "-1");
    request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create book with negative price fails", BAD_REQUEST, status(result));

    // Test POST /books (missing price)
    bookData.clear();
    bookData.put("isbn", "33333-33-333");
    bookData.put("name", "Bad Book");
    request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create book with missing price fails", BAD_REQUEST, status(result));

    // Test POST /books (zero edition)
    bookData.clear();
    bookData.put("isbn", "33333-33-333");
    bookData.put("name", "Bad Book");
    bookData.put("price", "9.99");
    bookData.put("edition", "0");
    request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create book with zero edition fails", BAD_REQUEST, status(result));

    // Test POST /books (duplicate ISBN)
    bookData.clear();
    bookData.put("isbn", "22222-22-222");
    bookData.put("name", "Bad Book");
    bookData.put("price", "50.99");
    request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create duplicate book fails", BAD_REQUEST, status(result));

    // Test POST /books (empty ISBN)
    bookData.clear();
    bookData.put("isbn", "");
    bookData.put("name", "Bad Book");
    bookData.put("price", "50.99");
    request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create book with empty ISBN fails", BAD_REQUEST, status(result));

    // Test POST /books (missing ISBN)
    bookData.clear();
    bookData.put("name", "Bad Book");
    bookData.put("price", "50.99");
    request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create book with missing ISBN fails", BAD_REQUEST, status(result));

    // Test POST /books (empty name)
    bookData.clear();
    bookData.put("isbn", "33333-33-333");
    bookData.put("name", "");
    bookData.put("price", "50.99");
    request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create book with empty name fails", BAD_REQUEST, status(result));

    // Test POST /books (missing name)
    bookData.clear();
    bookData.put("isbn", "33333-33-333");
    bookData.put("price", "50.99");
    request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create book with missing name fails", BAD_REQUEST, status(result));

    // Test DELETE /books/11111-11-111 (a valid isbn)
    result = callAction(controllers.routes.ref.Book.delete(isbn));
    assertEquals("Delete current book OK", OK, status(result));
    result = callAction(controllers.routes.ref.Book.details(isbn));
    assertEquals("Deleted book gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Book.delete(isbn));
    assertEquals("Delete missing book also OK", OK, status(result));
  }

  /**
   * Tests the {@link controllers.Student} controller.
   */
  @Test
  public void testStudentController() {
    // Test GET /students on an empty database.
    Result result = callAction(controllers.routes.ref.Student.index());
    assertTrue("Empty students", contentAsString(result).contains("No students"));

    // Test GET /students on a database containing a single student.
    String studentId = "Student-01";
    Student student = new Student(studentId, "Test", "Student", "test@hawaii.edu", "password");
    student.save();
    result = callAction(controllers.routes.ref.Student.index());
    assertTrue("One student", contentAsString(result).contains(studentId));

    // Test GET /students/Student-01
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertTrue("Student detail", contentAsString(result).contains(studentId));

    // Test GET /students/BadStudentId and make sure we get a 404
    result = callAction(controllers.routes.ref.Student.details("BadStudentId"));
    assertEquals("Student detail (bad)", NOT_FOUND, status(result));

    // Test POST /students (with simulated, valid form data).
    Map<String, String> studentData = new HashMap<>();
    studentData.put("studentId", "Student-02");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "mypassword");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create new student", OK, status(result));

    // Test POST /students (empty studentId)
    studentData.clear();
    studentData.put("studentId", "");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "mypassword");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with empty studentId fails", BAD_REQUEST, status(result));

    // Test POST /students (missing studentId)
    studentData.clear();
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "mypassword");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with missing studentId fails", BAD_REQUEST, status(result));

    // Test POST /students (duplicate studentId)
    studentData.clear();
    studentData.put("studentId", "Student-02");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "mypassword");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with duplicate studentId fails", BAD_REQUEST, status(result));

    // Test POST /students (empty firstName)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("firstName", "");
    studentData.put("lastName", "Body");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "mypassword");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with empty firstName fails", BAD_REQUEST, status(result));

    // Test POST /students (missing firstName)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("lastName", "Body");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "mypassword");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with missing firstName fails", BAD_REQUEST, status(result));

    // Test POST /students (empty lastName)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "password");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with empty lastName fails", BAD_REQUEST, status(result));

    // Test POST /students (missing lastName)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("firstName", "Some");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "mypassword");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with missing lastName fails", BAD_REQUEST, status(result));

    // Test POST /students (misformed email)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("email", "bademail");
    studentData.put("password", "mypassword");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with misformed email fails", BAD_REQUEST, status(result));

    // Test POST /students (empty email)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("email", "");
    studentData.put("password", "password");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with empty email fails", BAD_REQUEST, status(result));

    // Test POST /students (missing email)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("password", "mypassword");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with missing email fails", BAD_REQUEST, status(result));
    
    // Test POST /students (too short password)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("email", "body@hawaii.edu");
    studentData.put("password", "pass");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with short password fails", BAD_REQUEST, status(result));
    
    // Test POST /students (missing password)
    studentData.clear();
    studentData.put("studentId", "Student-03");
    studentData.put("firstName", "Some");
    studentData.put("lastName", "Body");
    studentData.put("email", "body@hawaii.edu");
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create student with missing password fails", BAD_REQUEST, status(result));

    // Test DELETE /students/Student-01 (a valid studentId)
    result = callAction(controllers.routes.ref.Student.delete(studentId));
    assertEquals("Delete current student OK", OK, status(result));
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertEquals("Deleted student gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Student.delete(studentId));
    assertEquals("Delete missing student also OK", OK, status(result));
  }
  
  /**
   * Tests the {@link controllers.Offer} controller.
   */
  @Test
  public void testOfferController() {
    // Test GET /offers on an empty database.
    Result result = callAction(controllers.routes.ref.Offer.index());
    assertTrue("Empty offers", contentAsString(result).contains("No offers"));

    // Test GET /offers on a database containing a single offer.
    Student student = new Student("Student-01", "Test", "Student", "test@hawaii.edu", "password");
    Book book = new Book("11111-11-111", "Test Book", "Lady", "Okay Publishing", 20.99);
    student.save();
    book.save();
    
    String offerId = "Offer-01";
    Offer offer = new Offer(offerId, student, book, Condition.SLIGHTLY_USED, 15.99, 1);
    offer.save();
    result = callAction(controllers.routes.ref.Offer.index());
    assertTrue("One offer", contentAsString(result).contains(offerId));

    // Test GET /offers/Offer-01
    result = callAction(controllers.routes.ref.Offer.details(offerId));
    assertTrue("Offer detail", contentAsString(result).contains(offerId));

    // Test GET /offers/BadOfferId and make sure we get a 404
    result = callAction(controllers.routes.ref.Offer.details("BadOfferId"));
    assertEquals("Offer detail (bad)", NOT_FOUND, status(result));

    // Test POST /offers (with simulated, valid form data).
    Map<String, String> offerData = new HashMap<>();
    offerData.put("offerId", "Offer-02");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create new offer", OK, status(result));

    // Test POST /offers (empty offerId)
    offerData.clear();
    offerData.put("offerId", "");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with empty offerId fails", BAD_REQUEST, status(result));

    // Test POST /offers (missing offerId)
    offerData.clear();
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with missing offerId fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (duplicate offerId)
    offerData.clear();
    offerData.put("offerId", "Offer-02");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with duplicate offerId fails", BAD_REQUEST, status(result));

    // Test POST /offers (empty offerId)
    offerData.clear();
    offerData.put("offerId", "");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with empty offerId fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (duplicate offerId)
    offerData.clear();
    offerData.put("offerId", "Offer-02");
    offerData.put("firstName", "Some");
    offerData.put("lastName", "Body");
    offerData.put("email", "body@hawaii.edu");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with duplicate offerId fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (non-existent student)
    offerData.clear();
    offerData.put("offerId", "Offer-03");
    offerData.put("student", "FAKE");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with non-existent student fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (non-existent book)
    offerData.clear();
    offerData.put("offerId", "Offer-03");
    offerData.put("student", "Student-01");
    offerData.put("book", "FAKE");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with non-existent book fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (bad condition)
    offerData.clear();
    offerData.put("offerId", "Offer-03");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "Z");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with bad condition fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (missing condition)
    offerData.clear();
    offerData.put("offerId", "Offer-03");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("price", "10.99");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with missing condition fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (negative price)
    offerData.clear();
    offerData.put("offerId", "Offer-03");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "-1");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with negative price fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (missing price)
    offerData.clear();
    offerData.put("offerId", "Offer-03");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("quantity", "2");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with missing price fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (zero quantity)
    offerData.clear();
    offerData.put("offerId", "Offer-03");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    offerData.put("quantity", "0");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with zero quantity fails", BAD_REQUEST, status(result));
    
    // Test POST /offers (missing quantity)
    offerData.clear();
    offerData.put("offerId", "Offer-03");
    offerData.put("student", "Student-01");
    offerData.put("book", "11111-11-111");
    offerData.put("condition", "N");
    offerData.put("price", "10.99");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create offer with missing quantity fails", BAD_REQUEST, status(result));

    // Test DELETE /offers/Offer-01 (a valid offerId)
    result = callAction(controllers.routes.ref.Offer.delete(offerId));
    assertEquals("Delete current offer OK", OK, status(result));
    result = callAction(controllers.routes.ref.Offer.details(offerId));
    assertEquals("Deleted offer gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Offer.delete(offerId));
    assertEquals("Delete missing offer also OK", OK, status(result));
  }
  
  /**
   * Tests the {@link controllers.Request} controller.
   */
  @Test
  public void testRequestController() {
    // Test GET /requests on an empty database.
    Result result = callAction(controllers.routes.ref.Request.index());
    assertTrue("Empty requests", contentAsString(result).contains("No requests"));

    // Test GET /requests on a database containing a single request.
    Student student = new Student("Student-01", "Test", "Student", "test@hawaii.edu", "password");
    Book book = new Book("11111-11-111", "Test Book", "Dude", "Awesome Publishing", 20.99);
    student.save();
    book.save();
    
    String requestId = "Request-01";
    Request request = new Request(requestId, student, book, 15.99, 1, Condition.SLIGHTLY_USED);
    request.save();
    result = callAction(controllers.routes.ref.Request.index());
    assertTrue("One request", contentAsString(result).contains(requestId));

    // Test GET /requests/Request-01
    result = callAction(controllers.routes.ref.Request.details(requestId));
    assertTrue("Request detail", contentAsString(result).contains(requestId));

    // Test GET /requests/BadRequestId and make sure we get a 404
    result = callAction(controllers.routes.ref.Request.details("BadRequestId"));
    assertEquals("Request detail (bad)", NOT_FOUND, status(result));

    // Test POST /requests (with simulated, valid form data).
    Map<String, String> requestData = new HashMap<>();
    requestData.put("requestId", "Request-02");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    requestData.put("quantity", "2");
    FakeRequest fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create new request", OK, status(result));
    
    // Test POST /requests (missing condition, should succeed)
    requestData.clear();
    requestData.put("requestId", "Request-03");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("price", "10.99");
    requestData.put("quantity", "1");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with missing condition should succeed", OK, status(result));

    // Test POST /requests (empty requestId)
    requestData.clear();
    requestData.put("requestId", "");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with empty requestId fails", BAD_REQUEST, status(result));

    // Test POST /requests (missing requestId)
    requestData.clear();
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with missing requestId fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (duplicate requestId)
    requestData.clear();
    requestData.put("requestId", "Request-02");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with duplicate requestId fails", BAD_REQUEST, status(result));

    // Test POST /requests (empty requestId)
    requestData.clear();
    requestData.put("requestId", "");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with empty requestId fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (duplicate requestId)
    requestData.clear();
    requestData.put("requestId", "Request-02");
    requestData.put("firstName", "Some");
    requestData.put("lastName", "Body");
    requestData.put("email", "body@hawaii.edu");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with duplicate requestId fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (non-existent student)
    requestData.clear();
    requestData.put("requestId", "Request-03");
    requestData.put("student", "FAKE");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with non-existent student fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (non-existent book)
    requestData.clear();
    requestData.put("requestId", "Request-03");
    requestData.put("student", "Student-01");
    requestData.put("book", "FAKE");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with non-existent book fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (bad condition)
    requestData.clear();
    requestData.put("requestId", "Request-03");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "Z");
    requestData.put("price", "10.99");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with bad condition fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (negative price)
    requestData.clear();
    requestData.put("requestId", "Request-03");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "-1");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with negative price fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (missing price)
    requestData.clear();
    requestData.put("requestId", "Request-03");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("quantity", "2");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with missing price fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (zero quantity)
    requestData.clear();
    requestData.put("requestId", "Request-03");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    requestData.put("quantity", "0");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with zero quantity fails", BAD_REQUEST, status(result));
    
    // Test POST /requests (missing quantity)
    requestData.clear();
    requestData.put("requestId", "Request-03");
    requestData.put("student", "Student-01");
    requestData.put("book", "11111-11-111");
    requestData.put("condition", "N");
    requestData.put("price", "10.99");
    fakeRequest = fakeRequest();
    fakeRequest.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), fakeRequest);
    assertEquals("Create request with missing quantity fails", BAD_REQUEST, status(result));

    // Test DELETE /requests/Request-01 (a valid requestId)
    result = callAction(controllers.routes.ref.Request.delete(requestId));
    assertEquals("Delete current request OK", OK, status(result));
    result = callAction(controllers.routes.ref.Request.details(requestId));
    assertEquals("Deleted request gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Request.delete(requestId));
    assertEquals("Delete missing request also OK", OK, status(result));
  }
}
