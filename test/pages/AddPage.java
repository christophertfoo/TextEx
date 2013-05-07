package pages;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class AddPage extends FluentPage{
    
    private String url;
    
    private boolean validAddTested = false;
    
    public AddPage (WebDriver webDriver, int port) {
        super(webDriver);
            this.url = "http://localhost:" + port + "/addbook";

    }
   
    @Override
    public void isAt() {
        assert(this.title().equals("Add Book"));
    }
    

    
    @Override
    public String getUrl() {
        return this.url;
    }
    
    public void validAdd() {
        // Test with edition
        fill("#isbnInput").with("111-111-1111");
        fill("#titleInput").with("My Book");
        fill("#authorsInput").with("Me");
        fill("#publisherInput").with("UHM");
        fill("#editionInput").with("2");
        fill("#priceInput").with("39.99");
        click("#addSubmit");
        assertTrue("Should have success message when book is added.", this.pageSource().contains("Successfully added the book!"));
        assertTrue("New book should be in database", models.Book.find().findList().size() == 1);
        
        // Test without edition
        fill("#isbnInput").with("222-222-2222");
        fill("#titleInput").with("Your Book");
        fill("#authorsInput").with("You");
        fill("#publisherInput").with("UHM");
        fill("#priceInput").with("12.98");
        click("#addSubmit");
        assertTrue("Should have success message when book is added. (no edition)", this.pageSource().contains("Successfully added the book!"));
        assertTrue("New book should be in database", models.Book.find().findList().size() == 2);
        
        this.validAddTested = true;
    }
    
    public void invalidAdd() {
        if(!this.validAddTested) {
            this.validAdd();
            this.validAddTested = true;
        }
        
        // Missing ISBN
        this.addFormClear();
        fill("#titleInput").with("My Book");
        fill("#authorsInput").with("Me");
        fill("#publisherInput").with("UHM");
        fill("#editionInput").with("2");
        fill("#priceInput").with("39.99");
        this.checkAddFail("(missing isbn)");
        
        // Duplicate ISBN
        this.addFormClear();
        fill("#isbnInput").with("222-222-2222");
        fill("#titleInput").with("My Book");
        fill("#authorsInput").with("Me");
        fill("#publisherInput").with("UHM");
        fill("#editionInput").with("2");
        fill("#priceInput").with("39.99");
        this.checkAddFail("(duplicate isbn)");
        
        // Missing Title
        this.addFormClear();
        fill("#isbnInput").with("333-333-3333");
        fill("#authorsInput").with("Me");
        fill("#publisherInput").with("UHM");
        fill("#editionInput").with("2");
        fill("#priceInput").with("39.99");
        this.checkAddFail("(missing title)");
        
        // Missing Authors
        this.addFormClear();
        fill("#isbnInput").with("333-333-3333");
        fill("#titleInput").with("My Book");
        fill("#publisherInput").with("UHM");
        fill("#editionInput").with("2");
        fill("#priceInput").with("39.99");
        this.checkAddFail("(missing authors)");
        
        // Missing Publisher
        this.addFormClear();
        fill("#isbnInput").with("333-333-3333");
        fill("#titleInput").with("My Book");
        fill("#authorsInput").with("Me");
        fill("#editionInput").with("2");
        fill("#priceInput").with("39.99");
        this.checkAddFail("(missing publisher)");
        
        // Missing Price
        this.addFormClear();
        fill("#isbnInput").with("333-333-3333");
        fill("#titleInput").with("My Book");
        fill("#authorsInput").with("Me");
        fill("#publisherInput").with("UHM");
        fill("#editionInput").with("2");
        this.checkAddFail("(missing price)");
        
        // Bad Price
        this.addFormClear();
        fill("#isbnInput").with("333-333-3333");
        fill("#titleInput").with("My Book");
        fill("#authorsInput").with("Me");
        fill("#publisherInput").with("UHM");
        fill("#editionInput").with("2");
        fill("#priceInput").with("-1");
        this.checkAddFail("(bad price)");
        
        // Bad Edition
        this.addFormClear();
        fill("#isbnInput").with("333-333-3333");
        fill("#titleInput").with("My Book");
        fill("#authorsInput").with("Me");
        fill("#publisherInput").with("UHM");
        fill("#editionInput").with("0");
        fill("#priceInput").with("39.99");
        this.checkAddFail("(bad edition)");
        
        assertTrue("Should still have 2 books in database.", models.Book.find().findList().size() == 2);
    }
    
    private void addFormClear() {
        clear("#isbnInput");
        clear("#titleInput");
        clear("#authorsInput");
        clear("#publisherInput");
        clear("#editionInput");
        clear("#priceInput");
    }
    
    private void checkAddFail(String test) {
        click("#addSubmit");
        assertTrue("Should print error message when could not add book. " + test, this.pageSource().contains("Error: Could not add the book!  Are there errors in the form?"));
    }
    
    public void gotoSearch() {
        this.click("#searchNav");
        assertTrue("Should be at search page.", this.title().equals("Browse Books"));
    }
    

}
