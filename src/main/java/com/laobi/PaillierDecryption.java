package com.laobi;

import java.math.BigInteger;

public class PaillierDecryption {

	/**
	 * p and q are two large primes. lambda = lcm(p-1, q-1) =
	 * (p-1)*(q-1)/gcd(p-1, q-1).
	 */
	public BigInteger lambda;
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
	public BigInteger g;


	public PaillierDecryption(BigInteger n, BigInteger nsquare, BigInteger g, BigInteger lambda) {
		this.n = n;
		this.nsquare = nsquare;
		this.g = g;
		this.lambda = lambda;
	}

	/**
	 * Decrypts ciphertext c. plaintext m = L(c^lambda mod n^2) * u mod n, where
	 * u = (L(g^lambda mod n^2))^(-1) mod n.
	 *
	 * @param c
	 *            ciphertext as a BigInteger
	 * @return plaintext as a BigInteger
	 */
	public BigInteger decryption(BigInteger c) {
		BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
		return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
	}

}