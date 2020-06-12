package com.laobi;

import java.math.BigInteger;
import java.util.Random;

public class PaillierMain {

	/**
	 * p and q are two large primes. lambda = lcm(p-1, q-1) =
	 * (p-1)*(q-1)/gcd(p-1, q-1).
	 */
	private static BigInteger p, q, lambda;
	/**
	 * n = p*q, where p and q are two large primes.
	 */
	public static BigInteger n;
	/**
	 * nsquare = n*n
	 */
	public static BigInteger nsquare;
	/**
	 * a random integer in Z*_{n^2} where gcd (L(g^lambda mod n^2), n) = 1.
	 */
	private static BigInteger g;
	/**
	 * number of bits of modulus
	 */
	private static int bitLength;


	/**
	 * Sets up the public key and private key.
	 *
	 * @param bitLengthVal
	 *            number of bits of modulus.
	 * @param certainty
	 *            The probability that the new BigInteger represents a prime
	 *            number will exceed (1 - 2^(-certainty)). The execution time of
	 *            this constructor is proportional to the value of this
	 *            parameter.
	 */
	public static PaillierEntity KeyGeneration(int bitLengthVal, int certainty) {
		bitLength = bitLengthVal;
		/*
		 * Constructs two randomly generated positive BigIntegers that are
		 * probably prime, with the specified bitLength and certainty.
		 */
		p = new BigInteger(bitLength / 2, certainty, new Random());
		q = new BigInteger(bitLength / 2, certainty, new Random());

		n = p.multiply(q);
		nsquare = n.multiply(n);

		g = new BigInteger("2");
		lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))
				.divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
		/* check whether g is good. */
		if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
			System.out.println("g is not good. Choose g again.");
			System.exit(1);
		}

		PaillierEntity pe = new PaillierEntity();
		pe.g = g;
		pe.lambda = lambda;
		pe.n = n;
		pe.nsquare = nsquare;
		pe.p = p;
		pe.q = q;
		pe.bitLength = bitLength;

		return pe;
	}

	/**
	 * main function
	 *
	 * @param str
	 *            intput string
	 */
	public static void main(String[] str) {

		PaillierEntity paillierEntity = KeyGeneration(512, 64);

//		System.out.println(paillierEntity);

		PaillierEncryption encoder = new PaillierEncryption(paillierEntity.n,
				paillierEntity.nsquare,
				paillierEntity.g,
				paillierEntity.bitLength);

		BigInteger t1 = new BigInteger("100");
		BigInteger t2 = new BigInteger("100");

		BigInteger et1 = encoder.encryption(t1);
		BigInteger et2 = encoder.encryption(t2);

		BigInteger sum = new BigInteger("1");
//		sum = encoder.cipherAdd(sum, et1);
//		sum = encoder.cipherAdd(sum, et2);

		long l = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			sum = encoder.cipherAdd(sum, et2);
		}

		System.out.println("耗时: " + (System.currentTimeMillis() - l));

		System.out.println("sum: "+sum.toString());

		PaillierDecryption decoder = new PaillierDecryption(paillierEntity.n,
				paillierEntity.nsquare,
				paillierEntity.g,
				paillierEntity.lambda);

		System.out.println("decrypted sum: "+decoder.decryption(sum).toString());

		//穷举破解太慢
//		for (int i = 0; i < 1000000000; i++) {
//
//			PaillierDecryption decoder = new PaillierDecryption(paillierEntity.n,
//					paillierEntity.nsquare,
//					paillierEntity.g,
//					paillierEntity.lambda.add(new BigInteger("-" + i)));
//
//			String dstr = decoder.decryption(sum).toString();
//
//			if(dstr.length() < 5) {
//				System.out.println("decrypted sum: "+ dstr);
//				System.exit(1);
//			}
//
//		}

		System.out.println("--------------------------------");

		

	}
}