package commons;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObject.hrm.DashboardPO;
import pageObject.hrm.LoginPO;
import pageObjects.user.nopCommerce.CustomerInfoPageObject;
import pageObjects.user.nopCommerce.OrderPageObject;
import pageObjects.user.nopCommerce.PageGeneratorManager;
import pageObjects.user.nopCommerce.SearchPageObject;
import pageUIs.admin.nopCommerce.AdminPageUI;

import pageUIs.user.nopCommerce.BasePageUI;
import pageUIs.hrm.HRMBasePageUI;
import pageUIs.hrm.MyInfoPageUI;



/**
 * @author Admin
 *
 */
public class BasePage {
	
	public static BasePage getBasePage() {
		return new BasePage();
	}
	
	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageURL(WebDriver driver) {
		return driver.getCurrentUrl();

	}

	public String getPageSouce(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public void clearTextbox (WebDriver driver, String locator) {
		getElement(driver, locator).clear();
	}

	public Alert waitAlertPresence(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(WebDriver driver) {
		alert = waitAlertPresence(driver);
		alert.accept();
		sleepInsecond(2);
	}

	public void cancelAlert(WebDriver driver) {
		alert = waitAlertPresence(driver);
		alert.dismiss();
	}

	public String getTextAlert(WebDriver driver) {
		alert = waitAlertPresence(driver);
		return alert.getText();
	}

	public void senkeyToAlert(WebDriver driver, String value) {
		alert = waitAlertPresence(driver);
		alert.sendKeys(value);
	}

	public void switchWindowByID(WebDriver driver, String windowID) {
		Set<String> allWindowIDs = driver.getWindowHandles();

		for (String id : allWindowIDs) {

			if (!id.equals(windowID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}

	public void switchToWindowByTitle(WebDriver driver, String pageTitle) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle().trim();
			if (actualTitle.equals(pageTitle)) {
				break;
			}
		}
	}

	public void closeAlltabWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}
		}
	}

	public void sleepInsecond(long shortTimeoutInsecond) {
		try {
			Thread.sleep(shortTimeoutInsecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public By getByXpath(String locator) {
		return By.xpath(locator);
	}

	public WebElement getElement(WebDriver driver, String locator) {
		return driver.findElement(getByXpath(locator));
	}
	
	public WebElement getElement(WebDriver driver, String locator, String... params) {
		return driver.findElement(getByXpath(getDynamicLocator(locator, params)));
	}

	public List<WebElement> getElements(WebDriver driver, String locator) {
		return driver.findElements(By.xpath(locator));
	}
	
	public String getDynamicLocator(String locator, String... params) {
		return String.format(locator, (Object[]) params);
	}

	public void clickToElement(WebDriver driver, String locator) {
		if(driver.toString().contains("internet explorer")) {
			clickToElementByJS(driver, locator);
			sleepInsecond(2);
		} else {
		getElement(driver, locator).click();
		}
	}
	
	public void clickToElement(WebDriver driver, String locator, String... params) {
		if(driver.toString().contains("internet explorer")) {
			clickToElementByJS(driver, getDynamicLocator(locator, params));
			sleepInsecond(2);
		} else {
		getElement(driver, getDynamicLocator(locator, params)).click();
		}		
	}

	public void senkeyToElement(WebDriver driver, String locator, String value) {
		getElement(driver, locator).clear();
		getElement(driver, locator).sendKeys(value);
	}
	
	public void senkeyToElement(WebDriver driver, String locator, String value, String...params) {
		locator = getDynamicLocator(locator, params);
		getElement(driver, locator).clear();
		getElement(driver, locator).sendKeys(value);
	}

	public int getSizeElements(WebDriver driver, String locator) {
		return getElements(driver, locator).size();
	}
	
	public int getSizeElements(WebDriver driver, String locator, String...params) {
		return getElements(driver, getDynamicLocator(locator, params)).size();
	}
	
	public Set<Cookie> getAllCookies(WebDriver driver) {
		return driver.manage().getCookies();	
	}
	
	public void setAllCookies(WebDriver driver, Set<Cookie> allCookies) {
		for (Cookie cookie : allCookies) {
			driver.manage().addCookie(cookie);
		}
	}
	
	public void selectDropdownByText(WebDriver driver, String locator, String itemText) {
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}
	
	public void selectDropdownByText(WebDriver driver, String locator, String itemText, String... params) {
		locator = getDynamicLocator(locator, params);
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}
	
	public String getSelectedItemDropdown(WebDriver driver, String locator){
		select = new Select(getElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}
	
	public String getSelectedItemDropdown(WebDriver driver, String locator, String... params){
		locator = getDynamicLocator(locator, params);
		select = new Select(getElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}
	
	public boolean isDropdownMultiple(WebDriver driver, String locator) {
		select = new Select(getElement(driver, locator));
		return select.isMultiple();
	}
	
	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator, String expectedItem) {
		getElement(driver, parentLocator).click();
		sleepInsecond(1);

		explicitWait = new WebDriverWait(driver, shortTimeout);
		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInsecond(1);

				item.click();
				sleepInsecond(1);
				break;
			}
		}
	}
	
	public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
		return getElement(driver, locator).getAttribute(attributeName);
	}
	
	public String getElementAttribute(WebDriver driver, String locator, String attributeName, String... params) {
		locator = getDynamicLocator(locator, params);
		return getElement(driver, locator).getAttribute(attributeName);
	}
	public String getElementText(WebDriver driver, String locator) {
		return getElement(driver, locator).getText().trim();
	}
	
	public String getElementText(WebDriver driver, String locator, String...params) {
		return getElement(driver, getDynamicLocator(locator, params)).getText().trim();
	}
	
	public String getElementCss(WebDriver driver, String locator, String cssValue) {
		return getElement(driver, locator).getCssValue(cssValue);
	}
	
	public String getElementCss(WebDriver driver, String locator, String cssValue, String... params) {
		locator = getDynamicLocator(locator, params);
		return getElement(driver, locator).getCssValue(cssValue);
	}
	
	public String convertRgbaToHex(WebDriver driver, String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}
	
	public void checkTheCheckboxOrRadio(WebDriver driver, String locator) {
		if (!isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}
	
	public void checkTheCheckboxOrRadio(WebDriver driver, String locator, String... params) {
		locator = getDynamicLocator(locator, params);
		if (!isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}
	
	public void uncheckTheCheckbox(WebDriver driver, String locator) {
		if (isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}
	
	public boolean isElementDisplayed(WebDriver driver, String locator) {
		try {
			//Displayed: Visible on UI + In DOM
			//Undisplay: Invisible on UI + In DOM
			return getElement(driver, locator).isDisplayed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Undisplayed + not in DOM + Invisible on UI
			return false;
		}
	}
	
	public boolean isElementUnDisplayed(WebDriver driver, String locator) {
		System.out.println("Start time = " + new Date().toString());
		overrideGlobalTimeout(driver, shortTimeout);
		List<WebElement> elements = getElements(driver, locator);
		overrideGlobalTimeout(driver, longTimeout);
		
		if (elements.size() == 0) {
			System.out.println("Element not in DOM and not visible on UI");
			System.out.println("End time " + new Date().toString());
			return true;		
		}else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			System.out.println("Element in DOM but not visible/dislpayed on UI");
			System.out.println("End time " + new Date().toString());
			return true;
		}else {
			System.out.println("Element in DOM and visible on UI");
			return false;
		}
		
	}
	
	public void overrideGlobalTimeout (WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		
	}
	
	
	public boolean isElementDisplayed(WebDriver driver, String locator, String...params) {
		return getElement(driver, getDynamicLocator(locator, params)).isDisplayed();
	}
	
	public boolean isElementEnabled(WebDriver driver, String locator) {
		return getElement(driver, locator).isEnabled();
	}
	
	public boolean isElementEnabled(WebDriver driver, String locator, String...params) {
		return getElement(driver, getDynamicLocator(locator, params)).isEnabled();
	}
	
	public boolean isElementSelected(WebDriver driver, String locator) {
		return getElement(driver, locator).isSelected();
	}
	
	public boolean isElementSelected(WebDriver driver, String locator, String...params) {
		return getElement(driver, getDynamicLocator(locator, params)).isSelected();
	}
	
	public WebDriver switchToIframeByElement(WebDriver driver, String locator) {
		return driver.switchTo().frame(getElement(driver, locator));
	}
	
	public WebDriver switchToDefaultContent(WebDriver driver) {
		return driver.switchTo().defaultContent();
	}
	
	public void hoverToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.moveToElement(getElement(driver, locator)).perform();
	}
	
	public void hoverToElement(WebDriver driver, String locator, String... params) {
		action = new Actions(driver);
		action.moveToElement(getElement(driver, getDynamicLocator(locator, params))).perform();
	}
	
	public void doubleClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.doubleClick(getElement(driver, locator)).perform();
	}
	
	public void rightClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.contextClick(getElement(driver, locator)).perform();
	}
	
	public void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
		action = new Actions(driver);
		action.dragAndDrop(getElement(driver, sourceLocator),getElement(driver, targetLocator)).perform();
	}
	
	public void pressKeyToElement(WebDriver driver, String locator, Keys key) {
		action = new Actions(driver);
		action.sendKeys(getElement(driver, locator), key).perform();	
	}
	public void pressKeyToElement(WebDriver driver, String locator, Keys key, String...params) {
		action = new Actions(driver);
		locator = getDynamicLocator(locator, params);
		action.sendKeys(getElement(driver, locator), key).perform();	
	}
	
	public Object executeForBrowser(WebDriver driver, String javaScript) {
		jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(WebDriver driver, String url) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInsecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getElement(driver, locator));
	}

	public void scrollToElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator));
	}

	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver, locator));
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, locator));
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
	
	public boolean isJQueryAjaxLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};
		return explicitWait.until(jQueryLoad);
	}

	public String getElementValidationMessage(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(driver, locator));
	}

	public boolean isImageLoaded(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getElement(driver, locator));
		if (status) {
			return true;
		} else {
			return false;
		}
	}
	
	public void waitForElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));	
	}
	
	public void waitForElementVisible(WebDriver driver, String locator, String...params) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));	
	}
	
	public void waitForAllElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));	
	}
	
	public void waitForElementInvisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));	
	}
	
	public void waitForElementInvisible(WebDriver driver, String locator, String...params) {
		explicitWait = new WebDriverWait(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));	
	}
	
	public void waitForElementClickable(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));	
	}
	
	public void waitForElementClickable(WebDriver driver, String locator, String...params) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, params))));	
	}
	
	public SearchPageObject openSearchPage(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.SEARCH_PAGE_FOOTER);
		clickToElement(driver, BasePageUI.SEARCH_PAGE_FOOTER);
		return PageGeneratorManager.getSearchPage(driver);
	}
	
	public CustomerInfoPageObject openCustomerInfoPage(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.CUSTOMER_INFO_PAGE_FOOTER);
		clickToElement(driver, BasePageUI.CUSTOMER_INFO_PAGE_FOOTER);
		return PageGeneratorManager.getCustomInfoPage(driver);
	}
	
	public OrderPageObject openOrderPage(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.ORDER_PAGE_FOOTER);
		clickToElement(driver, BasePageUI.ORDER_PAGE_FOOTER);
		return PageGeneratorManager.getOrderPage(driver);
	}
	//User - Nopcommerce
	//1 hàm cho 20 page
	//Case 1 - Page<10
	public BasePage getFooterPageByName(WebDriver driver, String pageName) {
		waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGE_FOOTER, pageName);
		clickToElement(driver, BasePageUI.DYNAMIC_PAGE_FOOTER, pageName);
		
		if(pageName.equals("Search")){
			return PageGeneratorManager.getSearchPage(driver);
		}else if(pageName.equals("My account")) {
			return PageGeneratorManager.getCustomInfoPage(driver);
		}else {
			return PageGeneratorManager.getOrderPage(driver);
		}
	}
	
	//Case 2 - Multiple page
	//Admin - Nopcommerce
	public void openSubMenuPageByName(WebDriver driver, String menuPageName, String subMenuPageName) {
		waitForElementClickable(driver, AdminPageUI.MENU_LINK_BY_NAME, menuPageName);
		clickToElement(driver, AdminPageUI.MENU_LINK_BY_NAME, menuPageName);
		
		waitForElementClickable(driver, AdminPageUI.SUB_MENU_LINK_BY_NAME, subMenuPageName);
		clickToElement(driver, AdminPageUI.SUB_MENU_LINK_BY_NAME, subMenuPageName);
	}	
	
	
	public void uploadMultipleFilesAtCardName(WebDriver driver, String cardName, String... fileNames) {
		String filePath = GlobalConstants.UPLOAD_FOLDER_PATH;
		String fullFileName = "";
		for (String file : fileNames) {
			fullFileName = fullFileName + filePath + file + "\n";
		}
		fullFileName = fullFileName.trim();
		getElement(driver, AdminPageUI.UPLOAD_FILE_BY_CARD_NAME, cardName).sendKeys(fullFileName);
	}
	
	public boolean isMessageDisplayedInEmptyTable(WebDriver driver, String tableName) {
		waitForElementVisible(driver, AdminPageUI.NO_DATA_MESSAGE_TABLE_NAME, tableName);
		return isElementDisplayed(driver, AdminPageUI.NO_DATA_MESSAGE_TABLE_NAME, tableName);
	}
	
//	//Pattern object
//		public void openFooterPageByName(WebDriver driver, String pageName) {
//			waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGE_FOOTER, pageName);
//			clickToElement(driver, BasePageUI.DYNAMIC_PAGE_FOOTER, pageName);	
//		}
//		
//		public void clickToHeaderLinkByText(WebDriver driver, String headerText) {
//			waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGE_HEADER, headerText);
//			clickToElement(driver, BasePageUI.DYNAMIC_PAGE_HEADER, headerText);	
//		}
//		
//		public void clickToRadioButtonByID(WebDriver driver, String genderID) {
//			waitForElementClickable(driver, BasePageUI.DYNAMIC_GENDER_RADIO, genderID);
//			clickToElement(driver, BasePageUI.DYNAMIC_GENDER_RADIO, genderID);	
//		}
//		
//		public void enterToTextboxByID(WebDriver driver,String textboxID, String value) {
//			waitForElementVisible(driver, BasePageUI.DYNAMIC_TEXTBOX, textboxID);
//			senkeyToElement(driver, BasePageUI.DYNAMIC_TEXTBOX, value, textboxID);
//		}
//		
//		public void clickToButtonByText(WebDriver driver, String buttonID) {
//			waitForElementClickable(driver, BasePageUI.DYNAMIC_BUTTON, buttonID);
//			clickToElement(driver, BasePageUI.DYNAMIC_BUTTON, buttonID);
//			
//		}
	
	//Admin - HRM
	
	public void enterToTextboxByID(WebDriver driver, String value, String textboxID) {
		waitForElementVisible(driver, HRMBasePageUI.TEXTBOX_BY_ID, textboxID);
		senkeyToElement(driver, HRMBasePageUI.TEXTBOX_BY_ID, value, textboxID);
	}
	
	public void clickToButtonByID(WebDriver driver,String buttonID) {
		waitForElementClickable(driver, HRMBasePageUI.BUTTON_BY_ID, buttonID);
		clickToElement(driver, HRMBasePageUI.BUTTON_BY_ID, buttonID);
	}
	
	public void openMenuPage(WebDriver driver, String menuPageName) {
		waitForElementClickable(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, menuPageName);
		clickToElement(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, menuPageName);
		
		isJQueryAjaxLoadedSuccess(driver);
	}
	
	public void openSubMenuPage(WebDriver driver, String menuPageName, String subMenuPageName) {
		waitForElementClickable(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, menuPageName);
		clickToElement(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, menuPageName);
		
		waitForElementVisible(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, subMenuPageName);
		clickToElement(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, subMenuPageName);
		
		isJQueryAjaxLoadedSuccess(driver);
	}
	
	public void openChildSubMenuPage(WebDriver driver, String menuPageName, String subMenuPageName, String childSubMenuPageName) {
		waitForElementClickable(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, menuPageName);
		clickToElement(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, menuPageName);
		
		waitForElementVisible(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, subMenuPageName);
		hoverToElement(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, subMenuPageName);
		
		waitForElementClickable(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, childSubMenuPageName);
		clickToElement(driver, HRMBasePageUI.MENU_BY_PAGE_NAME, menuPageName);
		
		isJQueryAjaxLoadedSuccess(driver);
	}
	
	public String getValueInTextboxByID(WebDriver driver, String getValueID) {
		waitForElementVisible(driver, HRMBasePageUI.TEXTBOX_BY_ID, getValueID);
		return getElementAttribute(driver, HRMBasePageUI.TEXTBOX_BY_ID, "value", getValueID);
	}
	
	public void clickToCheckboxByLabel(WebDriver driver, String checkboxLabel) {
		waitForElementClickable(driver, HRMBasePageUI.CHECKBOX_BY_LABEL, checkboxLabel);
		checkTheCheckboxOrRadio(driver, HRMBasePageUI.CHECKBOX_BY_LABEL, checkboxLabel);
	}
	
	public void clickToRadioByLabel(WebDriver driver, String radioLabel) {
		waitForElementClickable(driver, HRMBasePageUI.RADIO_BUTTON_BY_LABEL, radioLabel);
		checkTheCheckboxOrRadio(driver, HRMBasePageUI.RADIO_BUTTON_BY_LABEL, radioLabel);
	}
	
	public void selectItemInDropdownByID(WebDriver driver, String value, String dropdownID) {
		waitForElementClickable(driver, HRMBasePageUI.DROPDOWN_BY_ID, dropdownID);
		selectDropdownByText(driver, HRMBasePageUI.DROPDOWN_BY_ID, value, dropdownID);
	}
	
	public String getSelectItemInDropdownByID(WebDriver driver, String dropdownID) {
		waitForElementVisible(driver, HRMBasePageUI.DROPDOWN_BY_ID, dropdownID);
		return getSelectedItemDropdown(driver, HRMBasePageUI.DROPDOWN_BY_ID, dropdownID);
	}
	
	public String getValueInTableIDAtColumnNameAndRowIndex(WebDriver driver, String tableID, String rowIndex, String headerName) {
		int columnIndex = getSizeElements(driver, HRMBasePageUI.TABLE_HEADER_BY_ID_AND_NAME, tableID,headerName) + 1;
		waitForElementVisible(driver, HRMBasePageUI.TABLE_ROW_BY_COLUMN_INDEX_AND_ROW_INDEX, tableID, rowIndex, String.valueOf(columnIndex));
		return getElementText(driver, HRMBasePageUI.TABLE_ROW_BY_COLUMN_INDEX_AND_ROW_INDEX, tableID, rowIndex, String.valueOf(columnIndex));
	}
	
	public boolean isSelectedItemInRadio(WebDriver driver, String radioLabel) {
		waitForElementVisible(driver, HRMBasePageUI.RADIO_BUTTON_BY_LABEL, radioLabel);
		return isElementSelected(driver, HRMBasePageUI.RADIO_BUTTON_BY_LABEL, radioLabel);
	}
	
	
	/**
	 * Get selected text item in dropdown
	 * @author dttam
	 * @param driver
	 * @param dropdownID
	 * @return selected text in dropdown
	 */
	
	public LoginPO clickToLogoutLink(WebDriver driver) {
		waitForElementClickable(driver, HRMBasePageUI.USER_ICON_LINK);
		clickToElement(driver, HRMBasePageUI.USER_ICON_LINK);
		
		waitForElementClickable(driver, HRMBasePageUI.LOGOUT_LINK);
		clickToElement(driver, HRMBasePageUI.LOGOUT_LINK);
		
		return new LoginPO(driver);
	}
	
	public DashboardPO loginToSystemHRM(WebDriver driver, String userName, String password) {
		waitForElementVisible(driver, HRMBasePageUI.USERNAME_TEXTBOX);
		senkeyToElement(driver, HRMBasePageUI.USERNAME_TEXTBOX, userName);
		senkeyToElement(driver, HRMBasePageUI.PASSWORD_TEXTBOX, password);
		clickToElement(driver, HRMBasePageUI.LOGIN_BUTTON);
		
		if (driver.toString().contains("internet explorer")) {
			sleepInsecond(3);
			
		}
		
		return new DashboardPO(driver);
	}
	
	public void uploadImage(WebDriver driver,String filePath) {
		getElement(driver, HRMBasePageUI.UPLOAD_FILE).sendKeys(filePath);
	}
	
	public boolean isMessageSuccessDisplayed(WebDriver driver,String messageSuccess) {
		waitForElementVisible(driver, HRMBasePageUI.SUCCESS_MESSAGE, messageSuccess);
		return isElementDisplayed(driver, HRMBasePageUI.SUCCESS_MESSAGE, messageSuccess);
	}
	
	public boolean isFieldEnableByName(WebDriver driver,String fieldID) {
		waitForElementVisible(driver, HRMBasePageUI.ANY_FIELD_BY_ID, fieldID);
		return isElementEnabled(driver, HRMBasePageUI.ANY_FIELD_BY_ID, fieldID);
	}
	
	
	private Alert alert;
	private WebDriverWait explicitWait;
	private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
	private long longTimeout = GlobalConstants.LONG_TIMEOUT;
	private Select select;
	private JavascriptExecutor jsExecutor;
	private Actions action;
	
	
}
