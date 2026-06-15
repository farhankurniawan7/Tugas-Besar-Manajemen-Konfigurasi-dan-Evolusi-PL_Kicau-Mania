package com.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(-1, calculator.add(-2, 1));
        assertEquals(0, calculator.add(0, 0));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
        assertEquals(-3, calculator.subtract(-2, 1));
        assertEquals(0, calculator.subtract(5, 5));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
        assertEquals(-6, calculator.multiply(-2, 3));
        assertEquals(0, calculator.multiply(5, 0));
    }

    @Test
    public void testDivide() {
        assertEquals(2, calculator.divide(6, 3));
        assertEquals(-2, calculator.divide(-6, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        calculator.divide(5, 0);
    }

    @Test
    public void testModulo() {
        assertEquals(1, calculator.modulo(7, 3));
        assertEquals(0, calculator.modulo(8, 4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testModuloByZero() {
        calculator.modulo(5, 0);
    }

    @Test
    public void testPower() {
        assertEquals(8L, calculator.power(2, 3));
        assertEquals(1L, calculator.power(5, 0));
        assertEquals(81L, calculator.power(3, 4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPowerNegativeExponent() {
        calculator.power(2, -1);
    }
}
