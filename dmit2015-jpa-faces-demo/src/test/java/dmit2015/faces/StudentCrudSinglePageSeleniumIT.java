package dmit2015.faces;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentCrudSinglePageSeleniumIT {

    private static WebDriver driver;

    private static JavascriptExecutor js;

    static List<String> sharedEditIds = new ArrayList<>();

    @BeforeAll
    static void beforeAllTests() {
        // You can download the latest version of Selenium Manager from https://github.com/titusfortner/selenium_manager_debug/releases
        // Use the command `./selenium-manager-linux --browser chrome` to download the webdriver for Chrome browser
        // Use the command `./selenium-manager-linux --browser firefox` to download the webdriver for Firefox browser
        // System.setProperty("webdriver.chrome.driver", "/home/user2015/.cache/selenium/chromedriver/linux64/129.0.6668.70/chromedriver");
        // System.setProperty("webdriver.gecko.driver", "/snap/bin/geckodriver");
        WebDriverManager
                .chromedriver()
                // .driverVersion("129.0.6668.70")
                .setup();
        var chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
//        driver = new FirefoxDriver();

        js = (JavascriptExecutor) driver;
    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
    }

    @BeforeEach
    void beforeEachTestMethod() {

    }

    @AfterEach
    void afterEachTestMethod() throws InterruptedException {
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Thread.sleep(1000);
    }

    private void setTextValue(String fieldId, String fieldValue) throws InterruptedException {
        WebElement element = driver.findElement(By.id(fieldId));
        element.clear();
        element.sendKeys(fieldValue);
        Thread.sleep(500);
    }

    private void setPrimeFacesDatePickerValue(String fieldId, String fieldValue) {
        // The text field for the p:datePicker component has a suffix of "_input" appended to the end of the field id.
        final String datePickerTextFieldId = String.format("%s_input", fieldId);
        WebElement element = driver.findElement(By.id(datePickerTextFieldId));

        element.sendKeys(Keys.chord(Keys.ESCAPE));

        element.sendKeys(fieldValue);
        element.sendKeys(Keys.chord(Keys.TAB));
    }

    private void setPrimeFacesSelectOneMenuValue(String fieldId, String fieldValue) {
        // The id of the element to click for p:selectOneMenu has a suffix of "_label" appended to the id of the p:selectOneMenu
        String selectOneMenuId = String.format("%s_label", fieldId);
        driver.findElement(By.id(selectOneMenuId)).click();
        // Wait until 3 seconds for select items to pop up
        var waitSelectOneMenu = new WebDriverWait(driver, Duration.ofSeconds(3));
        // The id of the items for p:selectOneMenu has a suffix of "_items" appended to the id of the p:selectOneMenu
        String selectOneMenuItemsId = String.format("%s_items", fieldId);
        var selectOneMenuItems = waitSelectOneMenu.until(ExpectedConditions.visibilityOfElementLocated(By.id(selectOneMenuItemsId)));
        // The value for each item is stored a attribute named "data-label"
        String selectItemXPath = String.format("*[@data-label=\"%s\"]", fieldValue);
        var selectItem = selectOneMenuItems.findElement(By.xpath(selectItemXPath));
        selectItem.click();
    }

    /**
     * Find and returns the table row element with the "data-rk" attribute value that matches idValue.
     *
     * @param idValue                     The unique identifier value for the row.
     * @param tableElementXpathExpression The xpath expression for the PrimeFaces dataTable.
     * @return The row element that contains the idValue valur or null if not found.
     */
    private WebElement findRowIndex(String idValue, String tableElementXpathExpression) {
        // Check each page in the dataTable for a row that contains the idValue until found or the last page is checked.
        // Find the total number of pages in the table paginator
        int totalPages = driver.findElements(By.className("ui-paginator-page")).size();
        // Set the current page to 1 of the paginator
        int currentPage = 1;
        // Check each page of the dataTable
        while (currentPage <= totalPages) {
            // find the table element
            var tableElement = driver.findElement(By.xpath(tableElementXpathExpression));
            // Get all the rows from the table
            var tableRows = tableElement.findElements(By.tagName("tr"));
            // Track which row index the idValue is located
            int currentRowIndex = 0;
            // Check each row in the dataTable
            for (WebElement currentRow : tableRows) {
                // The idValue is stored in the "data-rk" attribute of the <tr> element
                String rowIdValue = currentRow.getAttribute("data-rk");
                // If the row contains a id value and it matches idValue then return the current row
                if (rowIdValue != null && rowIdValue.equalsIgnoreCase(idValue)) {
                    return tableRows.get(currentRowIndex);
                }
                // Check the next row
                currentRowIndex += 1;
            }
            // Check the next page
            currentPage += 1;
            // Click on the next page link
            var pageNextLink = driver.findElement(By.className("ui-paginator-next"));
            if (pageNextLink != null && pageNextLink.isEnabled()) {
                pageNextLink.click();
            }
        }

        return null;
    }

    @Order(1)
    @ParameterizedTest
    @CsvSource(value = {
            "firstName,Bruce,lastName,Less,email,bruce.less@nait.ca",
            "firstName,Bruce,lastName,More,email,bruce.more@nait.ca",
            "firstName,No,lastName,Bruce,email,no.bruce@nait.ca",
    })
    void shouldCreate(
            String field1Id, String field1Value,
            String field2Id, String field2Value,
            String field3Id, String field3Value
    ) throws InterruptedException {

        driver.get("http://localhost:8080/students/crud.xhtml");
        // Maximize the browser window to see the data being inputted
        driver.manage().window().maximize();
        Thread.sleep(1000);

        assertThat(driver.getTitle())
                .isEqualToIgnoringCase("Student - CRUD");

        // Find the New button by id then click on it
        var newButtonElement = driver.findElement(By.id("newButton"));
        assertThat(newButtonElement).isNotNull();
        newButtonElement.click();

        // Set the value for each form field.
        // Add suffix `_input` for p:inputNumber
        setTextValue("dialogs:" + field1Id, field1Value);
        // setPrimeFacesSelectOneMenuValue(field2Id, field2Value);
        // setPrimeFacesDatePickerValue(field1Id, field1Value);
        setTextValue("dialogs:" + field2Id, field2Value);
        setTextValue("dialogs:" + field3Id, field3Value);

        Thread.sleep(1000);

        // Find the Save button then click on it
        var saveButtonElement = driver.findElement(By.id("dialogs:saveButton"));
        assertThat(saveButtonElement).isNotNull();
        saveButtonElement.click();

        // Wait for 3 seconds and verify navigate has been redirected to the listing page
        var wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        var growlMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-growl-message")));
        // Verify the feedback message is displayed in the page
        String feedbackMessage = growlMessage.getText();
        assertThat(feedbackMessage)
                .containsIgnoringCase("Create was successful");
        // The primary key of the entity is at the end of the feedback message after the period
        final int indexOfPrimaryKeyValue = feedbackMessage.indexOf(".") + 2;
        // Extract the primary key for re-use if we need to edit or delete the entity
        String idValue = feedbackMessage.substring(indexOfPrimaryKeyValue, feedbackMessage.indexOf("\n"));
        sharedEditIds.add(idValue);

        Thread.sleep(1000);
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({
            "0, Bruce, Less, bruce.less@nait.ca",
            "1, Bruce, More, bruce.more@nait.ca",
            "2, No, Bruce, no.bruce@nait.ca",
    })
    void shouldList(
            int idIndex,
            String expectedColumn1Value, String expectedColumn2Value, String expectedColumn3Value
    ) throws InterruptedException {
        String expectedIdValue = sharedEditIds.get(idIndex);
        // Open a browser and navigate to the index page
        driver.get("http://localhost:8080/students/crud.xhtml");
        // Maximize the browser window so we can see the data being inputted
        driver.manage().window().maximize();
        Thread.sleep(1000);

        assertThat(driver.getTitle())
                .isEqualToIgnoringCase("Student - CRUD");

        final String primeFacesDataTableXpath = "/html/body/div[2]/form[1]/div[2]/div[2]/table";
        WebElement rowElement = findRowIndex(expectedIdValue, primeFacesDataTableXpath);
        assertThat(rowElement).isNotNull();

        var rowColumns = rowElement.findElements(By.xpath("td"));
        final String column1Value = rowColumns.get(0).getText();
        final String column2Value = rowColumns.get(1).getText();
        final String column3Value = rowColumns.get(2).getText();

        assertThat(column1Value)
                .isEqualToIgnoringCase(expectedColumn1Value);
        assertThat(column2Value)
                .isEqualToIgnoringCase(expectedColumn2Value);
        assertThat(column3Value)
                .isEqualToIgnoringCase(expectedColumn3Value);

    }

}