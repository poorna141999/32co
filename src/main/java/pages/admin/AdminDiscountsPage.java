package pages.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;

public class AdminDiscountsPage extends Page {


	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(id = "discount-list_refresh-button")
	private WebElement btnReload;

	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	@FindBy(xpath = "//span[text()='Preview']")
	private WebElement btnPreview;
	
	@FindBy(id = "discount-list_add-discount-button")
	private WebElement btnDiscounts;
	
	@FindBy(id = "discount-filter_archive-discount-checkbox")
	private WebElement rbArchivedDiscounts;
	
	@FindBy(id = "discount-filter_search-discount-input")
	private WebElement txtSearch;
	
	@FindBy(xpath = "//button/span[@aria-label='search']")
	private WebElement btnSearch;
	
	
	
	public AdminDiscountsPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}
}
