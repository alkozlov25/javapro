package com.example.module2;

import com.example.module2.obj.Employee;

import java.util.List;

public class Module2Application {

    public static void main(String[] args) {
        System.out.println(FunctionUtils.getUniqueList(new String[]{null, "", "ABC", "ABC", "QQ"}));

        System.out.println(FunctionUtils.getUniqueChars(new String[]{"ABC", "CDE", "EE"}));

        System.out.println(FunctionUtils.getMaxString(new String[]{"ABC","CDEF", "EE"}));

        System.out.println(FunctionUtils.getMax3Int(new Integer[]{5, 2, 10, 9, 4, 3, 10, 1, 13}));

        System.out.println(FunctionUtils.getOlderEngineer3(List.of(new Employee("Liza", 34, "Инженер"),
                new Employee("Mark", 28, "Инженер"),
                new Employee("Tom", 58, "Директор"),
                new Employee("Eva", 25, "Инженер"),
                new Employee("Rick", 40, "Инженер"))));

        System.out.println(FunctionUtils.getAverageAgeEngineer(List.of(new Employee("Liza", 34, "Инженер"),
                new Employee("Mark", 28, "Инженер"),
                new Employee("Tom", 58, "Директор"),
                new Employee("Eva", 25, "Инженер"),
                new Employee("Rick", 40, "Инженер"))));

        System.out.println(FunctionUtils.getMapStrings("Мама. мыла Окно, окно -было довольно"));

        System.out.println(FunctionUtils.getMaxWords(List.of("Мама мыла Окно, окно было довольно", "кровать", "Директор довольно.")));
    }

}
