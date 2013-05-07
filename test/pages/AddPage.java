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


package pages;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

/**
 * A {@link FluentPage} used to facilitate the testing of the TextEx application's add book page.
 * @author Christopher
 *
 */
public class AddPage extends FluentPage{
    
    /**
     * The URL of this {@link AddPage}.
     */
    private String url;
    
    /**
     * Creates a new {@link AddPage}.
     * @param webDriver The {@link WebDriver} used by the AddPage.
     * @param port The port of localhost.
     */
    public AddPage (WebDriver webDriver, int port) {
        super(webDriver);
            this.url = "http://localhost:" + port + "/addbook";

    }
   
    /**
     * Determines if this page is actually at the add book page.
     */
    @Override
    public void isAt() {
        assert(this.title().equals("Add Book"));
    }
    

    /**
     * Gets the URL of this {@link AddPage}.
     */
    @Override
    public String getUrl() {
        return this.url;
    }
    
    /**
     * Tests that valid books are successfully added.
     */
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
    }
    
    /**
     * Tests that invalid books are not added and an error message is raised.
     */
    public void invalidAdd() {

        int numInDb = models.Book.find().findList().size();
        
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
        
        assertTrue("Should still have 2 books in database.", models.Book.find().findList().size() == numInDb);
    }
    
    /**
     * Clears all of the fields of the add book form.
     */
    private void addFormClear() {
        clear("#isbnInput");
        clear("#titleInput");
        clear("#authorsInput");
        clear("#publisherInput");
        clear("#editionInput");
        clear("#priceInput");
    }
    
    /**
     * Checks that an error message is raised when the user tries to add a bad book.
     * @param test The test that cause the failure.
     */
    private void checkAddFail(String test) {
        click("#addSubmit");
        assertTrue("Should print error message when could not add book. " + test, this.pageSource().contains("Error: Could not add the book!  Are there errors in the form?"));
    }
    
    /**
     * Checks that the user can navigate to the search / browse books page from the add book page.
     */
    public void gotoSearch() {
        this.click("#searchNav");
        assertTrue("Should be at search page.", this.title().equals("Browse Books"));
    }
    

}
