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

import static org.junit.Assert.assertTrue;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

/**
 * A {@link FluentPage} used to facilitate the testing of the TextEx application's search / browse books page.
 * @author Christopher Foo
 *
 */
public class SearchPage extends FluentPage {
    
    /**
     * The URL of this {@link SearchPage}.
     */
    private String url;
    
    /**
     * Creates a new {@link SearchPage}.
     * @param driver The {@link WebDriver} used by the SearchPage.
     * @param port The port of localhost.
     */
    public SearchPage(WebDriver driver, int port) {
        super(driver);
        this.url = "http://localhost:" + port + "/books";
    }
    
    /**
     * Gets this {@link SearchPage}'s URL.
     */
    @Override
    public String getUrl() {
        return this.url;
    }
    
    /**
     * Determines whether this page is actually at the search page.
     */
    @Override
    public void isAt() {
        assert(this.title().equals("Browse Books"));
    }
    
    /**
     * Tests that the search function is able to retrieve the added books.  Must be called after
     * the {@link AddPage} tests.
     */
    public void testSearch() {
        click("#searchSubmit");
        assertTrue("Should have 2 Books.", this.pageSource().contains("222-222-2222") && this.pageSource().contains("111-111-1111"));
        
        fill("#title").with("My");
        click("#searchSubmit");
        assertTrue("Should have 1 Books.", !this.pageSource().contains("222-222-2222") && this.pageSource().contains("111-111-1111"));
        
        // TODO Add more tests
    }
}
