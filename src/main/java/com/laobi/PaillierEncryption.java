package com.laobi;

import java.math.BigInteger;
import java.util.Random;

public class PaillierEncryption {

	/**
	 * n = p*q, where p and q are two large primes.
	 */
	public BigInteger n;
	/**
	 * nsquare = n*n
	 */
	public BigInteger nsquare;
	/**
	 * a random integer in Z*_{n^2} where gcd (L(g^lambda mod n^2), n) = 1.
	 */
	private BigInteger g;
	/**
	 * number of bits of modulus
	 */
	private int bitLength;

	public PaillierEncryption(BigInteger n, BigInteger nsquare, BigInteger g, int bitLength) {
		this.n = n;
		this.nsquare = nsquare;
		this.g = g;
		this.bitLength = bitLength;
	}

	/**
	 * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function
	 * explicitly requires random input r to help with encryption.
	 *
	 * @param m
	 *            plaintext as a BigInteger
	 * @param r
	 *            random plaintext to help with encryption
	 * @return ciphertext as a BigInteger
	 */
	public BigInteger encryption(BigInteger m, BigInteger r) {
		return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
	}

	/**
	 * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function
	 * automatically generates random input r (to help with encryption).
	 *
	 * @param m
	 *            plaintext as a BigInteger
	 * @return ciphertext as a BigInteger
	 */
	public BigInteger encryption(BigInteger m) {
		BigInteger r = new BigInteger(bitLength, new Random());
		return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
	}

	/**
	 * sum of (cipher) em1 and em2
	 *
	 * @param em1
	 * @param em2
	 * @return
	 */
	public BigInteger cipherAdd(BigInteger em1, BigInteger em2) {
		return em1.multiply(em2).mod(nsquare);
	}

}