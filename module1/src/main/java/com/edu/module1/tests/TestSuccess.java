package com.edu.module1.tests;

import com.edu.module1.annotation.*;
import com.edu.module1.exceptions.TestAssertionError;

public class TestSuccess {
    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("run beforeSuite");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("run afterSuite");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("run beforeEach");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("run afterEach");
    }

    @Test(testName = "Test1", priority = 1)
    @Order(value = 1)
    public void testA() {
        System.out.println("run test1");
    }
    @Test(testName = "TestB", priority = 1)
    @Order(value = 1)
    public void testB() {
        System.out.println("run testB");
        throw new TestAssertionError("Ошибка");
    }

    @Test(testName = "testDisabled", priority = 1)
    @Disabled
    public void testDisabled() {
        System.out.println("run testDisabled");
    }

    @Test(testName = "testDisabled", priority = 1)
    @Order(value = 2)
    public void test2() {
        System.out.println("run test2");
    }

}
