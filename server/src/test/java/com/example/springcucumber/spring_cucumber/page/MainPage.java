package com.example.springcucumber.spring_cucumber.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;

@PageEntry(title = "Main")
public class MainPage extends Page {
    @ElementTitle(value = "Add user form")
    @FindBy(id = "navAddForm")
    WebElement addUserForm;
    @ElementTitle(value = "User table")
    //Need for caching WebElement and don't find them more times
    @CacheLookup
    @FindBys({
            @FindBy(id = "navLinkTable"),
            @FindBy(xpath = "//a[contains(text(),'s table')]")
    })
    WebElement userTable;
    @ElementTitle(value = "User add form first name")
    @FindBy(id = "addFirstName")
    WebElement addFirstName;
    @ElementTitle(value = "User add form last name")
    @FindBy(id = "addLastName")
    WebElement addLastName;
    @ElementTitle(value = "User add form phone number")
    @FindBy(id = "addPhoneNumber")
    WebElement addPhoneNumber;
    @ElementTitle(value = "User add form login")
    @FindBy(id = "addLogin")
    WebElement addLogin;
    @ElementTitle(value = "User add form password")
    @FindBy(id = "addPassword")
    WebElement addPassword;
    @ElementTitle(value = "Button add user")
    @FindBy(id = "butAddUser")
    WebElement buttonAddUser;
    @ElementTitle(value = "Error in add user")
    @FindBy(id = "errorInAddUser")
    WebElement errorInAddUser;
    @ElementTitle(value = "Edit menu opened")
    @FindBy(xpath = "(//div//b[text()='First name:']/following-sibling::input)[1]")
    WebElement editMenuOpened;
    @ElementTitle(value = "Edit user first name")
    @FindBy(xpath = "(//div//b[text()='First name:']/following-sibling::input)[1]")
    WebElement editFirstName;
    @ElementTitle(value = "Edit user last name")
    @FindBy(xpath = "(//div//b[text()='Last name:']/following-sibling::input)[1]")
    WebElement editLastName;
    @ElementTitle(value = "Edit user phone number")
    @FindBy(xpath = "(//div//b[text()='Phone number:']/following-sibling::input)[1]")
    WebElement editPhoneNumber;
    @ElementTitle(value = "Edit user login")
    @FindBy(xpath = "(//div//b[text()='Login:']/following-sibling::input)[1]")
    WebElement editLogin;
    @ElementTitle(value = "Edit user password")
    @FindBy(xpath = "(//div//b[text()='Password:']/following-sibling::input)[1]")
    WebElement editPassword;
    @ElementTitle(value = "Edit button confirm")
    @FindBy(xpath = "(//button[text()='Change'])[1]")
    WebElement conformEditButton;
    @ElementTitle(value = "Edit role on admin")
    @FindBy(xpath = "//select//option[@value='admin']")
    WebElement editRoleOnAdmin;
    @ElementTitle(value = "Edit role on user")
    @FindBy(xpath = "//select//option[@value='user']")
    WebElement editRoleOnUser;
    @ElementTitle("Add role by admin")
    @FindBy(css = "select > option:nth-child(1)")
    WebElement addAdminRole;
    @ElementTitle("Add role by user")
    @FindBy(css = "select > option:nth-child(1)")
    WebElement addUserRole;
    @ElementTitle("Confirm button to add user")
    @FindBy(css = "button[id='butAddUser']")
    WebElement confirmAddUser;


    public MainPage() {
        PageFactory.initElements(PageFactory.getWebDriver(),this);
    }

    public void editUser(String firstName, String lastName, String login, String password, String phoneNumber, String role) {
        WebElement roleInput;

        editFirstName.clear();
        editFirstName.sendKeys(firstName);
        editLastName.clear();
        editLastName.sendKeys(lastName);
        editLogin.clear();
        editLogin.sendKeys(login);
        editPassword.sendKeys(password);
        editPhoneNumber.clear();
        editPhoneNumber.sendKeys(phoneNumber);
        if (role.equalsIgnoreCase("admin")) {
            roleInput = editRoleOnAdmin;
        } else {
            roleInput = editRoleOnUser;
        }
        roleInput.click();
        conformEditButton.click();
    }

    public void addUser(String firstName, String lastName, String login, String password, String phoneNumber, String role) {
        WebElement inputRole;

        if (role.equalsIgnoreCase("admin")) {
            inputRole = addAdminRole;
        } else {
            inputRole = addUserRole;
        }

        addFirstName.sendKeys(firstName);
        addLastName.sendKeys(lastName);
        addLogin.sendKeys(login);
        addPassword.sendKeys(password);
        addPhoneNumber.sendKeys(phoneNumber);
        inputRole.click();

        confirmAddUser.click();

    }

        public String getUrl() {
        return ((PageEntry)this.getClass().getAnnotation(PageEntry.class)).url();
    }

    public WebElement getConfirmAddUser() {
        return confirmAddUser;
    }

    public WebElement getAddAdminRole() {
        return addAdminRole;
    }

    public WebElement getAddUserRole() {
        return addUserRole;
    }

    public WebElement getAddUserForm() {
        return addUserForm;
    }

    public WebElement getUserTable() {
        return userTable;
    }

    public WebElement getAddFirstName() {
        return addFirstName;
    }

    public WebElement getAddLastName() {
        return addLastName;
    }

    public WebElement getAddPhoneNumber() {
        return addPhoneNumber;
    }

    public WebElement getAddLogin() {
        return addLogin;
    }

    public WebElement getAddPassword() {
        return addPassword;
    }

    public WebElement getButtonAddUser() {
        return buttonAddUser;
    }

    public WebElement getErrorInAddUser() {
        return errorInAddUser;
    }

    public WebElement getEditMenuOpened() {
        return editMenuOpened;
    }

    public WebElement getEditFirstName() {
        return editFirstName;
    }

    public WebElement getEditLastName() {
        return editLastName;
    }

    public WebElement getEditPhoneNumber() {
        return editPhoneNumber;
    }

    public WebElement getEditLogin() {
        return editLogin;
    }

    public WebElement getEditPassword() {
        return editPassword;
    }

    public WebElement getConformEditButton() {
        return conformEditButton;
    }

    public WebElement getEditRoleOnAdmin() {
        return editRoleOnAdmin;
    }

    public WebElement getEditRoleOnUser() {
        return editRoleOnUser;
    }
}
