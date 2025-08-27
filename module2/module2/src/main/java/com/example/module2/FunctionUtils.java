package com.example.module2;

import com.example.module2.obj.Employee;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FunctionUtils {

    // 1
    public static List<String> getUniqueList(String[] strArr) {
        return Arrays.stream(strArr)
                .filter(x -> x != null && !x.isEmpty())
                .distinct().collect(Collectors.toList());
    }

    // 2
    public static long getUniqueChars(String[] strArr) {
        return String.join("", strArr).chars().distinct().count();
    }

    //3
    public static String getMaxString(String[] strArr) {
        return Arrays.stream(strArr)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }

    //4
    public static int getMax3Int(Integer[] intArr) {
        return Arrays.stream(intArr)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .skip(2)
                .findFirst()
                .orElse(0);
    }

    //5
    public static List<Employee> getOlderEngineer3(List<Employee> employees) {
        return employees.stream()
                .filter(x -> Objects.equals(x.getPost(), "Инженер"))
                .sorted(Comparator.comparing(Employee::getAge, Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toList());
    }

    //6
    public static double getAverageAgeEngineer(List<Employee> employees) {
        return employees.stream()
                .filter(x -> Objects.equals(x.getPost(), "Инженер"))
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0);
    }

    //7
    public static Map<Integer, List<String>> getMapStrings(String words) {
        Map<Integer, List<String>> resultMap = new HashMap<>();
        Arrays.stream(words.toLowerCase().replaceAll("[\\.,:;-]", " ").replaceAll("\\s+", " ").split(" "))
                //.peek(System.out::println)
                .distinct()
                .sorted(Comparator.comparing(String::length, Comparator.naturalOrder()))

                .forEach(w -> {

                    List<String> currList = resultMap.getOrDefault(w.length(), new ArrayList<>());
                    currList.add(w);
                    resultMap.put(w.length(), currList);
                });
        return resultMap;
    }

    //8
    public static List<String> getMaxWords(List<String> strings) {
        AtomicInteger currMaxL = new AtomicInteger();
        List<String> resultList = new ArrayList<>();
        strings.forEach(x -> {
            Map<Integer, List<String>> currMap = getMapStrings(x);
            int maxL = currMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0);

            if (maxL > currMaxL.get()) {
                //новая макс длина, почистим список слов и добавим новые
                resultList.clear();
                resultList.addAll(currMap.get(maxL));
                currMaxL.set(maxL);
            } else if (maxL == currMaxL.get()) {
                //при совпадении длины добавим слова в существующий список
                resultList.addAll(currMap.get(maxL));
            }
        }
        );
        return  resultList.stream().distinct().collect(Collectors.toList());
    }
}
