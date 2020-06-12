package com.laobi;

import java.math.BigInteger;

public class PaillierEntity {
    /**
     * p and q are two large primes. lambda = lcm(p-1, q-1) =
     * (p-1)*(q-1)/gcd(p-1, q-1).
     */
    public BigInteger p, q, lambda;
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
    /**
     * number of bits of modulus
     */
    public int bitLength;

    @Override
    public String toString() {
        return "PaillierEntity{" +
                "\np=" + p +
                ", \nq=" + q +
                ", \nlambda=" + lambda +
                ", \nn=" + n +
                ", \nnsquare=" + nsquare +
                ", \ng=" + g +
                ", \nbitLength=" + bitLength +
                '}';
    }
}
