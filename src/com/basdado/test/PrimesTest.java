package com.basdado.test;

import com.basdado.primes.PrimeSieve;

public class PrimesTest {
	
	public static void main(String[] args) {
		
		int until = 1000000;
		
		long startTime = System.nanoTime();		
		PrimeSieve primeSieve = new PrimeSieve(until);
		long endTime = System.nanoTime();
		
		System.out.println("Sieve build in " + ((endTime - startTime) / 1000000) + " ms");
		
		for (int n = 995; n <= 1000; n++)
			System.out.println("" + n + " is prime: " + primeSieve.isPrime(n));
		
		int count = 0;
		for (int n : primeSieve) {
//			System.out.println(n);
			count++;
		}
		System.out.println("Primes below " + until + ": " + count);
	}
}
