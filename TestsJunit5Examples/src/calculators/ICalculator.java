package calculators;
//https://examples.javacodegeeks.com/enterprise-java/maven/junit-maven-example/
public interface ICalculator {  //calculator for integers
	int sum(int a, int b);

	int subtraction(int a, int b);

	int multiplication(int a, int b);

	int division(int a, int b) throws Exception;

	boolean equalIntegers(int a, int b);
	
	int fibonacci(int n);
}
