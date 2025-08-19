package com.edu.module1.enums;

public enum TestResult {
    Success("тест выполнен успешно"),
    Failed("условие теста провалено"),
    Error("тест упал с произвольным исключением"),
    Skipped("тест не исполнялся");

    TestResult(String name) {
    }
}
