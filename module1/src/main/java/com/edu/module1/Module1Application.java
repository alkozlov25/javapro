package com.edu.module1;

import com.edu.module1.annotation.*;
import com.edu.module1.enums.TestResult;
import com.edu.module1.exceptions.BadTestClassError;
import com.edu.module1.exceptions.TestAssertionError;
import com.edu.module1.tests.TestSuccess;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.IntStream;

public class Module1Application {

	public static void main(String[] args) {
		Map<TestResult, List<TestInfo>> resultListMap = TestRunner.runTest(TestSuccess.class);
	}

	public static class TestRunner{

		private static void checkStatic(List<Method> methodList, Class<? extends Annotation> annotationClass) {
			methodList.forEach(m -> {
				if (Modifier.isStatic(m.getModifiers()) &&
						m.isAnnotationPresent(annotationClass)) {
					throw new BadTestClassError(String.format("Статический метод %s аннотирован запрещенной аннотацией %s", m.getName(), annotationClass.getName()));
				}
			});
		}

		private static void checkNonStatic(List<Method> methodList, Class<? extends Annotation> annotationClass) {
			methodList.forEach(m -> {
				if (!Modifier.isStatic(m.getModifiers()) &&
						m.isAnnotationPresent(annotationClass)) {
					throw new BadTestClassError(String.format("Объектный метод %s аннотирован запрещенной аннотацией %s", m.getName(), annotationClass.getName()));
				}
			});
		}

		private static String getTestName(Method method) {
			return StringUtils.isEmpty(method.getDeclaredAnnotation(Test.class).testName()) ? method.getName() : method.getDeclaredAnnotation(Test.class).testName();
		}

		public static Map<TestResult, List<TestInfo>> runTest(Class c) {
			Method[] methods = c.getDeclaredMethods();

			List<Method> beforeSuiteList = new ArrayList<>();
			List<Method> afterSuiteList = new ArrayList<>();
			List<Method> beforeEachList = new ArrayList<>();
			List<Method> afterEachList = new ArrayList<>();
			//список тестов сгруппированных по Order
			Map<Integer, List<Method>> testList = new HashMap<>();

			System.out.println(String.format("Подготовка тестов для класса %s", c.getName()));

			//проходим по всем методам класса, у которых есть аннотации и собираем соответствующие списки
			Arrays.stream(methods).forEach(m -> {
						Annotation[] annotationsM = m.getDeclaredAnnotations();
						if (annotationsM.length > 0) {
							if (Arrays.stream(annotationsM).anyMatch(x -> x.annotationType() == BeforeSuite.class)) {
								beforeSuiteList.add(m);
							}
							if (Arrays.stream(annotationsM).anyMatch(x -> x.annotationType() == AfterSuite.class)) {
								afterSuiteList.add(m);
							}
							if (Arrays.stream(annotationsM).anyMatch(x -> x.annotationType() == BeforeEach.class)) {
								beforeEachList.add(m);
							}
							if (Arrays.stream(annotationsM).anyMatch(x -> x.annotationType() == AfterEach.class)) {
								afterEachList.add(m);
							}

							if (Arrays.stream(annotationsM).anyMatch(x -> x.annotationType() == Test.class)) {
								Integer order = m.isAnnotationPresent(Order.class) ? m.getDeclaredAnnotation(Order.class).value() : 5;
								List<Method> currList = testList.getOrDefault(order, new ArrayList<>());
								currList.add(m);
								testList.put(order, currList);
							}
						} else { System.out.println(String.format("Метод %s не имеет аннотаций и будет пропущен", m.getName())); }
					});

			System.out.println(String.format("Подготовка тестов для класса %s завершена", c.getName()));

			//проверка аннотаций @Test, @BeforeEach, @AfterEach на статических методах
			checkStatic(beforeEachList, BeforeEach.class);
			checkStatic(afterEachList, AfterEach.class);
			testList.values().forEach(lm -> checkStatic(lm, Test.class));

			//проверка аннотаций @BeforeSuite, @AfterSuite висят на объектных методах
			checkNonStatic(beforeSuiteList, BeforeSuite.class);
			checkNonStatic(afterSuiteList, AfterSuite.class);

			System.out.println(String.format("Проверки тестов для класса %s завершены", c.getName()));

			System.out.println(String.format("Запуск тестов для класса %s...", c.getName()));

			Map<TestResult, List<TestInfo>> resultListMap = new HashMap<>();

			beforeSuiteList.forEach(b -> {
				try {
					b.invoke(c);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
            });

			//выполнение тестов в порядке Order, с сортировкой по наименованию
			IntStream.range(1, 10).forEach(i -> {
				if (testList.containsKey(i)) {
					testList.get(i).stream()
							.sorted(Comparator.comparing(TestRunner::getTestName, Comparator.naturalOrder()))
							.forEach(m -> {
								Object currObj;
								try {
									Class<?> clz = Class.forName(c.getName());
									Constructor<?> constructor = clz.getDeclaredConstructor();
									currObj = constructor.newInstance();
								} catch (Exception e) {
									throw new BadTestClassError(String.format("Ошибка создания объекта класса %s: %s", c.getName(), e.getMessage()));
                                }

                                if (m.isAnnotationPresent(Disabled.class)) {
									//отключенные тесты
									List<TestInfo> currExecuteList = resultListMap.getOrDefault(TestResult.Skipped, new ArrayList<>());
									currExecuteList.add(new TestInfo(TestResult.Skipped, m.getName()));
									resultListMap.put(TestResult.Skipped, currExecuteList);
								} else {
                                    beforeEachList.forEach(b -> {
										try {
											b.invoke(currObj);
										} catch (IllegalAccessException | InvocationTargetException e) {
											throw new RuntimeException(e);
										}
                                    });

									try {
										m.invoke(currObj);
										List<TestInfo> currExecuteList = resultListMap.getOrDefault(TestResult.Success, new ArrayList<>());
										currExecuteList.add(new TestInfo(TestResult.Success, m.getName()));
										resultListMap.put(TestResult.Success, currExecuteList);
									} catch (TestAssertionError e) {
										List<TestInfo> currExecuteList = resultListMap.getOrDefault(TestResult.Failed, new ArrayList<>());
										currExecuteList.add(new TestInfo(TestResult.Failed, m.getName(), e));
										resultListMap.put(TestResult.Failed, currExecuteList);
									} catch (Exception e) {
										List<TestInfo> currExecuteList = resultListMap.getOrDefault(TestResult.Error, new ArrayList<>());
										currExecuteList.add(new TestInfo(TestResult.Error, m.getName(), e));
										resultListMap.put(TestResult.Error, currExecuteList);
									}

									afterEachList.forEach(a -> {
										try {
											a.invoke(currObj);
										} catch (IllegalAccessException | InvocationTargetException e) {
											throw new RuntimeException(e);
										}
                                    });
								}
							});
				}
			});

			afterSuiteList.forEach(a -> {
				try {
					a.invoke(c);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
            });

			resultListMap.keySet().forEach(k -> {
				System.out.print(String.format("%s = [",k.name()));
				resultListMap.get(k).forEach(t -> {
					System.out.print(String.format("(%s; %s; %s), ",t.getTestName(), t.getTestResult().name(), t.getException()));
				});
				System.out.println("]");
			});

			return resultListMap;
		}
	}
}
