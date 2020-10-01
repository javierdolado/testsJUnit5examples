package testingcalcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import calculators.CalculatorBasicII;

@DisplayName("MAIN CLASS for testing the Special Calculator of Integer and Reals")
public class CalculatorBasicIITests {
	private static CalculatorBasicII calculator;
	private static int i = 0; // for indexing parameters
	@BeforeAll
	@DisplayName("Creation of the Calculator for Test")
	public static void initCalculator() {
		calculator = new CalculatorBasicII();
	}

	@AfterEach
	public void done() {
		System.out.println("done!");
	}

	@AfterAll
	@DisplayName("Delete the Calculator for Test")
	public static void clearCalculator() {
		calculator = null;
	}

	@Nested
	@DisplayName("INTEGERS. Nested class for testing the integers part")
	class CalculatorIntTest {
		@Test
		@Tag("4integers")
		@Order(1)
		@DisplayName("Sum of integers")
		public void testSum() {
			int result = calculator.sum(3, 4);
			assertEquals(7, result);
		}

		@Test
		@Tag("4integers")
		@Order(5)
		@DisplayName("Division of integers")
		public void testDivison() {
			try {
				int result = calculator.division(10, 2);
				assertEquals(5, result);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}

		@Test
		@Tag("4integers")
		@Order(6)
		@DisplayName("Exception division by 0 of integers")
		public void testDivisionException() throws Exception {
			try {
				calculator.division(10, 0);
			} catch (Exception e) {
				assertEquals("Argument 'divisor' is 0", e.getMessage());
			}
		}

		@Test
		@Tag("4integers")
		@Order(9)
		@DisplayName("Exception division by 0 of integers")
		public void testDivisionExceptionAssertThrows() {
			Exception exception = assertThrows(Exception.class,
					() -> calculator.division(5, 0));
			assertEquals("Argument 'divisor' is 0", exception.getMessage());
		}

		@Disabled
		@Test
		@Tag("4integers")
		@Order(4)
		@DisplayName("Equal integers")
		public void testEqual() {
			boolean result = calculator.equalIntegers(20, 20);
			assertFalse(result);
		}

		@Disabled
		@Test
		@Order(2)
		@Tag("4integers")
		@DisplayName("Subtraction of integers")
		public void testSubstraction() {
			int result = calculator.subtraction(10, 1);
			assertTrue(result == 9);
		}

		@Disabled
		@Test
		@Order(12)
		@Tag("4integers")
		@DisplayName("Fibonacci exception for negative integers")
		public void testFibonacci4negatives() {
			boolean caught = false;
			try {
				calculator.fibonacci(-1);
			} catch (Exception e) {
				caught = true;
			}
			assertTrue(caught);
		}

		@Disabled
		@Test
		@Order(13)
		@Tag("4integers")
		@DisplayName("Fibonacci. Recursive Fibonacci exception for negative integers")
		public void testTailRecurFibon4negatives() {
			boolean caught = false;
			try {
				calculator.fibonacciTailRecursive(-1);
			} catch (Exception e) {
				caught = true;
			}
			assertTrue(caught);
		}

		@ParameterizedTest(name = "run #{index} with [{arguments}]")
		@Order(14)
		@Tag("4integers")
		@DisplayName("Fibonacci. Recursive Fibonacci Parameterized with Values ")
		@ValueSource(ints = {1, 4})
		public void testTailRecurFibonParameterized1(int n) {
			final int[] expectedresults = {1, 3};
			assertEquals(expectedresults[i], calculator.fibonacciTailRecursive(n)
					);
			i++;

		}

		@Disabled
		@Test
		@Order(14)
		@Tag("4integers")
		@DisplayName("Fibonacci. Test Recursive Fibonacci Parameterized with CsvSource ")
		@CsvSource({"1,1", "4,3"})
		public void testTailRecurFibonParameterized2(int input, int expected) {
			assertEquals(expected, calculator.fibonacciTailRecursive(input));
		}

		@Disabled
		@TestFactory
		@Order(18)
		@Tag("4integers")
		@DisplayName("Fibonacci. Test Recursive Fibonacci Dynamic Test ")
		public void testTailRecurFibonDynamicTest() {

		}

		@Disabled
		@Test
		@Order(14)
		@Tag("4integers")
		@DisplayName("Fibonacci. Test Recursive Fibonacci for TimeOut ")
		public void testTailRecurFibonTimeout1() throws InterruptedException {
			assertTimeout(Duration.ofMillis(1),
					() -> calculator.fibonacciTailRecursive(30));
			// Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () ->
			// calculator.fibonacciTailRecursive(100));
		}

		@ParameterizedTest(name = "run #{index} with {0} should return {1} ")
		@Order(17)
		@Tag("4integers")
		@DisplayName("Fibonacci. Parameterized Test with FILE: fibonacci1.csv")
		@CsvFileSource(resources = "fibonacci1.csv", numLinesToSkip = 1, delimiter = ',')
		void test(int n, int expectedfib) {
			assertEquals(expectedfib, calculator.fibonacciTailRecursive(n));
		}

		@TestFactory
		@Order(21)
		@Tag("4integers")
		@DisplayName("Fibonacci. DYNAMIC Test with FILE: fibonacci3.csv")
		public Stream<org.junit.jupiter.api.DynamicTest> testFactory() {
			Stream<DynamicTest> result = null;
			String inputFilePath = "src/testingcalcs/fibonacci2.csv";
			try {
				Stream<String> inputStream = Files
						.lines(Paths.get(inputFilePath)).skip(1);
				result = inputStream.map(mapCsvLineToDynamicTest());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return result;
		}
		private Function<String, org.junit.jupiter.api.DynamicTest> mapCsvLineToDynamicTest() {
			return s -> {
				final String[] values = s.split(",");
				return DynamicTest.dynamicTest(values[0],
						() -> assertFibonacci(values));
			};
		}

		private void assertFibonacci(String[] values) {
			assertEquals(
					calculator.fibonacciTailRecursive(
							Integer.parseInt(values[1])),
					Integer.parseInt(values[2]));
		}
	}

	@Nested
	@DisplayName("DOUBLE. Nested class for the Double part")
	class CalculatorDoubTest {
		@Test
		@Tag("4Double")
		@Order(1)
		@DisplayName("Add Double method")
		public void testAdd() {
			double number1 = 0.0;
			double number2 = 0.0;

			double expResult = 0.0;
			double result = calculator.add(number1, number2);
			assertEquals(expResult, result, 0.01);
		}

		@Test
		@Tag("4Double")
		@Order(2)
		@DisplayName("Subtract Double method")
		public void testSubtract() {
			double number1 = 0.0;
			double number2 = 0.0;
			double expResult = 0.0;
			double result = calculator.subtract(number1, number2);
			assertEquals(expResult, result, 0.01);
		}

		@Test
		@Tag("4Double")
		@Order(3)
		@DisplayName("Multiply Double method")
		public void testMultiply() {
			double number1 = 0.0;
			double number2 = 0.0;
			double expResult = 0.0;
			double result = calculator.multiply(number1, number2);
			assertEquals(expResult, result, 0.01);
		}

		@Test
		@Tag("4Double")
		@Order(5)
		@DisplayName("Divide Double method")
		public void testDivide() {
			double number1 = 2.0;
			double number2 = 1.0;
			double expResult = 2.0;
			double result = 0;
			try {
				result = calculator.divide(number1, number2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals(expResult, result, 0.0001);
		}

		@Test
		@Order(6)
		// @Disabled
		@Tag("4Double")
		@DisplayName("Exception division by 0 of Double")
		public void testDivideException() throws Exception {
			// fail("Not yet implemented");
			try {
				calculator.divide(10, 0);
			} catch (Exception e) {
				assertEquals(e.getMessage(), "Argument 'divisor' is 0");
			}
		}

		@Test
		@Order(7)
		@Disabled
		@Tag("4Double")
		@DisplayName("Example of FAILURE because of mismatch expected and actual results ")
		public void testFailureExample() {
			double result = 0;
			try {
				result = calculator.divide(10, 5);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals(22, result);
		}

		@Test
		@Order(8)
		@Disabled
		@Tag("4Double")
		@DisplayName("Example of ERROR, but does not abort test because of block try-catch  ")
		public void testErrorExample() {
			double result = 0;
			try {
				result = calculator.divide(10, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// assertThrows(ArithmeticException.class, () ->
			// calculator.divide(10, 0));
			assertThrows(Exception.class, () -> calculator.divide(10, 0));
		}
	}
}
