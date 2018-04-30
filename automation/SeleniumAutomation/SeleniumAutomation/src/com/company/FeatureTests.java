package com.company;

import java.util.List;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;t
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeatureTests {

    private static WebDriver driver;
    private static String base_uri = "http://localhost/dotdash/dotdash-project-alpha/";
    private String new_to_add_category_name= "selenium Tasks";

    @BeforeClass
    public static void setup()
    {
        System.out.print("Please Enter the path to chrome driver : ");
        Scanner input = new Scanner(System.in);
        String path = input.nextLine();
        System.setProperty("webdriver.chrome.driver", path);

        driver= new ChromeDriver();
        driver.get(base_uri);

    }

    @Test
    public void test_A_add_non_existing_Category(){
        System.out.print("\nRunning Test Adding a new Category ... \n");
        WebElement new_category_input_field = driver.findElement(By.xpath("//*[@id=\"extra\"]/input[1]"));
        new_category_input_field.sendKeys(new_to_add_category_name);

        Select color_category_dropdow = new Select(driver.findElement(By.xpath("//*[@id=\"extra\"]/select[5]")));
        color_category_dropdow.selectByVisibleText("Green");

        WebElement add_categroy_button = driver.findElement(By.xpath("//*[@id=\"extra\"]/input[2]"));
        add_categroy_button.click();

        WebElement newly_added_cat = driver.findElement(By.linkText(new_to_add_category_name));

        try{
            assertNotNull(newly_added_cat);
            System.out.println("\t [+]Test Passed ");
        }catch (AssertionError e){
            System.out.println("\t [-]Test Failed ");
            System.out.println(e.getCause().toString());
        }


    }

    @Test
    public void test_B_add_non_existing_Category_with_existing_color(){
        System.out.print("\nRunning Test Adding a new Category with color used by existing category ... \n");
        WebElement new_category_input_field = driver.findElement(By.xpath("//*[@id=\"extra\"]/input[1]"));
        new_category_input_field.sendKeys("New Category 2");

        Select select_category_color = new Select(driver.findElement(By.xpath("//*[@id=\"extra\"]/select[5]")));
        select_category_color.selectByVisibleText("Green");

        WebElement add_categroy_button = driver.findElement(By.xpath("//*[@id=\"extra\"]/input[2]"));
        add_categroy_button.click();

        try{
            assertTrue( driver.findElement(By.tagName("body")).getText().contains("That colour is already being used by:") );
            System.out.println("\t [+]Test Passed ");
        }catch (AssertionError e){
            System.out.println("\t [-]Test Failed ");
            System.out.println(e.getCause().toString());
        }

        driver.findElement(By.linkText("Nevermind")).click();


    }

    @Test
    public void test_C_add_existing_Category(){
        System.out.print("\nRunning Test Adding an Existing Category \n");
        WebElement new_category_input_field = driver.findElement(By.xpath("//*[@id=\"extra\"]/input[1]"));
        new_category_input_field.sendKeys(new_to_add_category_name);

        Select select_category_color = new Select(driver.findElement(By.xpath("//*[@id=\"extra\"]/select[5]")));
        select_category_color.selectByVisibleText("Green");

        WebElement add_categroy_button = driver.findElement(By.xpath("//*[@id=\"extra\"]/input[2]"));
        add_categroy_button.click();


        try{
            assertTrue( driver.findElement(By.tagName("body")).getText().contains("The category you want to add already exists") );
            System.out.println("\t [+]Test Passed ");
        }catch (AssertionError e){
            System.out.println("\t [-]Test Failed ");
            System.out.println(e.getCause().toString());
        }


        driver.get(base_uri);
    }

    @Test
    public void test_D_RemoveCategory(){
        System.out.print("\nRunning Test Removing a Category ... \n");
        WebElement newly_added_category = driver.findElement(By.linkText(new_to_add_category_name));
        newly_added_category.click();

        try{
            assertTrue( driver.findElement(By.tagName("body")).getText().contains("Are you sure you wish to remove") );
            System.out.println("\t [+]Test Passed ");
        }catch (AssertionError e){
            System.out.println("\t [-]Test Failed ");
            System.out.println(e.getCause().toString());
        }


        WebElement yes_button = driver.findElement(By.linkText("Yes"));
        yes_button.click();
    }

    @Test
    public void test_E_AddTask(){
        System.out.print("\nRunning Test Adding a new Task ... \n");
        WebElement todo_list_div = driver.findElement(By.id("todos-content"));
        List<WebElement> task_list_items = todo_list_div.findElements(By.tagName("li"));
        int number_of_tasks_before_adding_task = task_list_items.size();

        WebElement task_input_field = driver.findElement(By.xpath("/html/body/div[4]/input[1]"));
        task_input_field.sendKeys("new Task added");

        Select category_dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"extra\"]/select[1]")));
        category_dropdown.selectByVisibleText("College");

        Select select_due_date_day = new Select(driver.findElement(By.xpath("//*[@id=\"extra\"]/select[2]")));
        select_due_date_day.selectByVisibleText("10");

        Select select_due_date_month = new Select(driver.findElement(By.xpath("//*[@id=\"extra\"]/select[3]")));
        select_due_date_month.selectByVisibleText("Feb");

        Select select_due_date_year = new Select(driver.findElement(By.xpath("//*[@id=\"extra\"]/select[4]")));
        select_due_date_year.selectByVisibleText("2018");

        WebElement add_button = driver.findElement(By.xpath("/html/body/div[4]/input[2]"));
        add_button.click();

        WebElement todo_list_div_updated = driver.findElement(By.id("todos-content"));
        List<WebElement> task_list_items_updated = todo_list_div_updated.findElements(By.tagName("li"));
        int number_of_tasks_after_adding_task = task_list_items_updated.size();

        try{
            assertEquals(number_of_tasks_before_adding_task + 1, number_of_tasks_after_adding_task);
            System.out.println("\t [+]Test Passed ");
        }catch (AssertionError e){
            System.out.println("\t [-]Test Failed ");
            System.out.println(e.getCause().toString());
        }

    }

    @Test
    public void test_F_CompleteTask(){
        System.out.print("\nRunning Test Striking out a task( marking task as complete) ... \n");
        WebElement span_element = driver.findElement(By.xpath("//*[contains(text(), 'new Task added (10/02/18')]"));
        WebElement parent_of_span = span_element.findElement(By.xpath(".."));
        WebElement input_element = parent_of_span.findElement(By.tagName("input"));
        input_element.click();

        WebElement complete_button = driver.findElement(By.xpath("/html/body/div[3]/input[2]"));
        complete_button.click();

        WebElement span_element_after = driver.findElement(By.xpath("//*[contains(text(), '(10/02/18')]"));
        WebElement parent_of_span_after = span_element_after.findElement(By.xpath(".."));
        WebElement strike_element = parent_of_span_after.findElement(By.tagName("strike"));

        try{
            assertNotNull(strike_element);
            System.out.println("\t [+]Test Passed ");
        }catch (AssertionError e){
            System.out.println("\t [-]Test Failed ");
            System.out.println(e.getCause().toString());
        }



    }


    @Test
    public void test_G_RemoveTask(){
        System.out.print("\nRunning Test Remove a Task ... \n");
        WebElement todo_list_div = driver.findElement(By.id("todos-content"));
        List<WebElement> task_list_items = todo_list_div.findElements(By.tagName("li"));
        int number_of_tasks_before_removing_task = task_list_items.size();

        WebElement span_element = driver.findElement(By.xpath("//*[contains(text(), '(10/02/18')]"));
        WebElement parent_of_span = span_element.findElement(By.xpath(".."));
        WebElement input_element = parent_of_span.findElement(By.tagName("input"));
        input_element.click();

        WebElement remove_button = driver.findElement(By.xpath("/html/body/div[3]/input[1]"));
        remove_button.click();

        WebElement todo_list_div_updated = driver.findElement(By.id("todos-content"));
        List<WebElement> task_list_items_updated = todo_list_div_updated.findElements(By.tagName("li"));
        int number_of_tasks_after_removing_task = task_list_items_updated.size();

        try{
            assertEquals(number_of_tasks_before_removing_task - 1, number_of_tasks_after_removing_task);
            System.out.println("\t [+]Test Passed ");
        }catch (AssertionError e){
            System.out.println("\t [-]Test Failed ");
            System.out.println(e.getCause().toString());
        }

    }


}
