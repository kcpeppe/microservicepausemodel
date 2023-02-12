package com.kodewerk.microservices.util;

public class Combinations {

    final private int n;

    public Combinations(int n) {
        if ( n > 66) throw new IllegalArgumentException("Sorry but > 66 is too big, limit of this demo");
        this.n = n;
    }

    public long combinations(int r) {
        if (( n == r) || (r == 0)) return 1;
        if (( r == 1) || ( r == n - 1)) return n;
        if ( r > n / 2) return combinations(n-r);
        double coefficient = 1.0d;
        int i = n - r + 1;
        for( int j = 1; j <= r; j++, i++)
            coefficient = coefficient * i / j;
        return (long)coefficient;
    }
}

/*
1 ways of seeing 0 pauses
39 ways of seeing 1 pauses
741 ways of seeing 2 pauses
9139 ways of seeing 3 pauses
82251 ways of seeing 4 pauses
575757 ways of seeing 5 pauses
3262623 ways of seeing 6 pauses
15380937 ways of seeing 7 pauses
61523748 ways of seeing 8 pauses
211915132 ways of seeing 9 pauses
635745396 ways of seeing 10 pauses
1676056044 ways of seeing 11 pauses
3910797436 ways of seeing 12 pauses
8122425444 ways of seeing 13 pauses
15084504396 ways of seeing 14 pauses
25140840660 ways of seeing 15 pauses
37711260990 ways of seeing 16 pauses
51021117810 ways of seeing 17 pauses
62359143990 ways of seeing 18 pauses
68923264410 ways of seeing 19 pauses
68923264410 ways of seeing 20 pauses
62359143990 ways of seeing 21 pauses
51021117810 ways of seeing 22 pauses
37711260990 ways of seeing 23 pauses
25140840660 ways of seeing 24 pauses
15084504396 ways of seeing 25 pauses
8122425444 ways of seeing 26 pauses
3910797436 ways of seeing 27 pauses
1676056044 ways of seeing 28 pauses
635745396 ways of seeing 29 pauses
211915132 ways of seeing 30 pauses
61523748 ways of seeing 31 pauses
15380937 ways of seeing 32 pauses
3262623 ways of seeing 33 pauses
575757 ways of seeing 34 pauses
82251 ways of seeing 35 pauses
9139 ways of seeing 36 pauses
741 ways of seeing 37 pauses
39 ways of seeing 38 pauses
1 ways of seeing 39 pauses
 */