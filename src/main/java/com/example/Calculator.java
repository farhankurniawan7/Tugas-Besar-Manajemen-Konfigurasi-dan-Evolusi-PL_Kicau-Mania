package com.example;

/**
 * Simple Calculator that performs basic arithmetic operations.
 * Used as the sample application for the CI/CD pipeline demonstration.
 */
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    public int modulo(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot modulo by zero");
        }
        return a % b;
    }

    public long power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must not be negative");
        }

        long result = 1;

        for (int i = 0; i < exponent; i++) {
            result *= base;
        }

        return result;
    }
}