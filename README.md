# Coin Cost Calculator
This is a small library written in Java with the objective to calculate the payments you can make with any
combination of coins and/or bills, of any non-negative cost.

Strategy design pattern is used to ensure that other payment calculation algorithms can be implemented
later. The only implementation of the algorithm in this library calculates all possible payments in a way that
might not be the most efficient, due to the occurrence of repeats.

## License
[MIT](https://choosealicense.com/licenses/mit/)
