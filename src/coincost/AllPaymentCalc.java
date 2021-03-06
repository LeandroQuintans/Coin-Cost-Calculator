package coincost;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coincost.exceptions.NegativeCoinAmountException;

/**
 * <p>An implementation of {@link IPaymentCalc}</p>
 * <p>This implementation calculates all possible payments, but might not be the most efficient
 * since it does find repeats during the algorithm execution.</p>
 * @author Leandro Quintans
 */
public class AllPaymentCalc implements IPaymentCalc {
    private CoinCost cc;
    private BigDecimal usedCost;
    private Set<Wallet> payments;

    /**
     * <p>Default constructor, takes a CoinCost argument.</p>
     * @param cc
     */
    public AllPaymentCalc(CoinCost cc) {
        this.cc = cc;
        this.usedCost = cc.getCost();
        payments = new HashSet<>();
    }

    /**
     * <p>Fills a Wallet entity with as many coins and bills possible until it's equal to the cost.</p>
     * @param payment Payment to be filled
     * @param vals Values of coins/bills to be used for filling 
     * @return 0 or 1 if payment is equal or greater to the cost, -1 if otherwise
     */
    // TODO explain algorithm
    private int fillPayment(Wallet payment, Iterator<BigDecimal> vals) {
        Wallet unusedCoinsWallet = cc.getWallet().subtract(payment);
        while (vals.hasNext()) {
            BigDecimal walletVal = vals.next();
            int amount = usedCost.subtract(payment.getFullTotal()).divideToIntegralValue(walletVal).intValue();
            try {
                unusedCoinsWallet.subAmount(walletVal, amount);
            } catch (NegativeCoinAmountException e) {
                amount = unusedCoinsWallet.get(walletVal);
                unusedCoinsWallet.subAmount(walletVal, amount);
            }
            payment.addAmount(walletVal, amount);

            if (payment.getFullTotal().equals(usedCost)) // TODO put condition in while
                break;
        }

        return payment.getFullTotal().compareTo(usedCost);
    }

    /**
     * <p>Recursive method that fills the Set<Wallet> payments attribute.</p>
     * @param payment Payment to be decomposed
     * @param val Value of coin/bill
     */
    // TODO explain algorithm
    public void paymentDecomposition(Wallet payment, BigDecimal val) {
        while (val != null) {
            paymentDecomposition(payment, payment.lowerKey(val));
            
            Set<BigDecimal> vals = cc.getWallet().headWallet(val).descendingKeySet();

            Wallet nextPayment = new Wallet(payment);
            nextPayment.subAmount(val, 1);
            if (fillPayment(nextPayment, vals.iterator()) >= 0) {
                payments.add(nextPayment);
            }
            payment = nextPayment;

            if (payment.get(val) == 0)
                val = cc.getWallet().lowerKey(val);

        }
    }

    // TODO explain algorithm
    @Override
    public Set<Wallet> payments() {
        // if the wallet has greater or equal money to the cost
        if (cc.getWallet().getFullTotal().compareTo(cc.getCost()) >= 0) {
            Wallet firstPayment = new Wallet();
            int walletCostCompare = fillPayment(firstPayment, cc.getWallet().descendingKeySet().iterator());


            // When you don't have an exact amount to pay the cost
            if (walletCostCompare < 0 && cc.getWallet().getFullTotal().compareTo(cc.getCost()) >= 0) {
                Wallet unusedCoinsWallet = cc.getWallet().subtract(firstPayment);
                BigDecimal firstKey = unusedCoinsWallet.firstKey();

                // Move this block to a new method?
                // Block start
                firstPayment.addAmount(firstKey, 1);
                unusedCoinsWallet.subAmount(firstKey, 1);

                BigDecimal key = firstPayment.firstKey();
                while (key.compareTo(firstKey) < 0) {
                    firstPayment.emptyPile(key);
                    key = firstPayment.higherKey(key);
                }
                // Block end

                usedCost = firstPayment.getFullTotal();

                firstPayment = new Wallet();
                fillPayment(firstPayment, cc.getWallet().descendingKeySet().iterator());
            }

            payments.add(firstPayment);

            Wallet nextPayment = new Wallet(firstPayment);
            paymentDecomposition(nextPayment, nextPayment.lastKey());
        }

        return payments;
    }
    
}
