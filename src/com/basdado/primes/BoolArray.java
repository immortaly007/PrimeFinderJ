package com.basdado.primes;
public class BoolArray {
	
	int[] data;
	int size = 0;
	
	public BoolArray(int size, boolean initial) {
		this.data = new int[getWordIndex(size - 1) + 1];
		
		// Default int value is 0, so we only set it if the initial value is true
		if (initial) 
			for (int i = 0; i < data.length; i++)
				data[i] = ~0;
		this.size = size;
	}
	
	private int getWordIndex(int i) {
		return i >> 5;
	}
	
	private int getBitIndex(int i) {
		return (int)i & 0x1F;
	}
	
	/**
	 * @param i
	 * @return The bit mask that masks the bit corresponding to index i
	 */
	private int getWordMask(int i) {
		return 1 << getBitIndex(i);
	}
	
	/**
	 * @param i The index of the wanted boolean
	 * @return The value of the boolean stored at index i
	 */
	public boolean get(int i) {
		return (data[getWordIndex(i)] & getWordMask(i)) != 0;
	}
	
	/**
	 * Sets the boolean at index i to the value given in value.
	 * @param i
	 * @param value
	 */
	public void set(int i, boolean value) {
		if (value) data[getWordIndex(i)] |= getWordMask(i);
		else data[getWordIndex(i)] &= ~getWordMask(i);
	}

	public int size() {
		return this.size;
	}
	
}
