package com.basdado.primes;
import java.util.Iterator;

/**
 * Prime number generator based on the sieve of Eratosthenes.
 * 
 * @author Bas Dado
 */
public class PrimeSieve implements Iterable<Integer> {
	
	int until;
	BoolArray sieve;
	
	public PrimeSieve(int until) {
		this.until = until;
		this.sieve = generateSieve();
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new PrimeSieveIterator(sieve);
	}

	/**
	 * @param n
	 * @return True iff n is even
	 */
	boolean isEven(int n)
	{
		return n % 2 == 0;
	}

	/**
	 * @param n
	 * @return Checks if the number is in the sieve, since we skip even numbers and numbers smaller than 3.
	 */
	boolean hasIndex(int n)
	{
		return !isEven(n) && n >= 3;
	}
	
	/**
	 * @return True iff n is prime
	 */
	public boolean isPrime(int n) {
		if (n == 2) return true;
		else if (!hasIndex(n)) return false;
		else if (n <= until) return sieve.get(getIndex(n));
		else if (n <= until * until) {
			for (int prime : this)
				if (n % prime == 0) return false;
			return true;
		} else {
			throw new IndexOutOfBoundsException("With the current sieve size it's impossible to determine whether n is prime");
		}
	}
	
	/**
	 * @param n
	 * @return The index of n in the sieve. Gives a lower bound index if n is not in the sieve
	 */
	private int getIndex(int n)
	{
		return (n - 3) / 2;
	}

	/**
	 * @param i
	 * @return The number at index i in the Sieve
	 */
	private int getNumberAtIndex(int i)
	{
		return (i * 2) + 3;
	}

	/**
	 * @param until 
	 * @return Expected number of primes below "until", based on the "Prime Number Theorem",
	 */
	public int getExpectedPrimeCount(double until)
	{
		return (int) (until / (Math.log(until) - 1.0));
	}

	/**
	 * Marks of multiples of n in the sieve, starting at the square of n
	 * @param sieve The sieve in which the numbers should be marked of
	 * @param n
	 * @param i
	 */
	private void markMultiples(BoolArray sieve, int n, int i)
	{
		int cur = i + ((i + 1) * n); // Start at the square of the number. 
		while (cur < sieve.size())
		{
			// Since we're multiplying two odd numbers, the result is always odd, and thus in our sieve.
			sieve.set(cur, false);
			cur += n;
		} 
	}

	/**
	 * Builds a prime sieve up to the current "until" value.
	 * @return
	 */
	private BoolArray generateSieve()
	{
		// Initialize the sieve assuming all numbers are prime
		BoolArray sieve = new BoolArray(getIndex(until) + 1, true);

		int sqrtUntil = (int) (Math.sqrt(until) + 1);
		int iSqrtUntil = getIndex(sqrtUntil);
		for (int i = 0; i <= iSqrtUntil; i++)
		{
			// If the current number is prime, mark all multiples of it as being not prime:
			if (sieve.get(i)) {
				// n is the current number that is considered
				int n = getNumberAtIndex(i);
				//MarkMultiples(sieve, n, until);
				markMultiples(sieve, n, i);
			}
		}
		return sieve;
	}
	
	public class PrimeSieveIterator implements Iterator<Integer> {
		
		private BoolArray sieve;
		private int i;
		private int lastPrimeIndex;
		
		public PrimeSieveIterator(BoolArray sieve)
		{
			this.sieve = sieve;
			this.i = -1;
			this.lastPrimeIndex = getLastPrimeIndex();
		}
		
		private int getLastPrimeIndex() {
			for (int i = sieve.size() - 1; i > 0; i--) {
				if (sieve.get(i)) return i;
			}
			return 0;
		}

		@Override
		public boolean hasNext() {
			return i < lastPrimeIndex;
		}

		@Override
		public Integer next() {
			if (i == -1) { i++; return 2; }
			while (!sieve.get(i)) i++;
			return getNumberAtIndex(i++);
		}
	}


}
