package calculators;

/*A calculator for both integer and double*/
public class CalculatorBasicII implements ICalculator {

	@Override
	public int sum(int a, int b) {
		return a + b;
	}

	@Override
	public int subtraction(int a, int b) {
		return a - b;
	}

	@Override
	public int multiplication(int a, int b) {
		return a * b;
	}

	@Override
	public int division(int a, int b) throws Exception {
		if (b == 0) {
			throw new IllegalArgumentException("Argument 'divisor' is 0");
		}
		return a / b;
	}

	@Override
	public boolean equalIntegers(int a, int b) {
		boolean result = false;
		if (a == b) {
			result = true;
		}
		return result;
	}
	@Override
	public int fibonacci(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Argument is negative");
		}
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		int total = fibonacci(n - 1) + fibonacci(n - 2);
		return total;
	}

	public int fibonacciTailRecursive(int n) {
		return fibonWorker(n, 0, 1);
	}
	private int fibonWorker(int n, int a, int b) {
		if (n == 0)
			return a;
		if (n == 1)
			return b;
		return fibonWorker(n - 1, b, a + b);
	}
	
	public int fibonacciIterative(int n) {
		// not the most standard way to code this
		int prev = 0;
		int next = 1;

		for (int j = 0; j < n; j++) {
			int tmp = next;
			next = next + prev;
			prev = tmp;
		}
		return prev;
	}

	public double add(double number1, double number2) {
		return number1 + number2;
	}
	public double subtract(double number1, double number2) {
		return number1 - number2;
	}
	public double multiply(double number1, double number2) {
		return number1 * number2;
	}
	public double divide(double number1, double number2) throws Exception {
		if (number2 == 0) {
			throw new IllegalArgumentException("Argument 'divisor' is 0");
		}
		return number1 / number2;
	}
}
