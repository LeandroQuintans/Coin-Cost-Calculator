package coincost;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coincost.exceptions.NegativeCoinAmountException;


public class AllPaymentCalc implements IPaymentCalc {
    private CoinCost cc;
    private Set<Wallet> payments;

    public AllPaymentCalc(CoinCost cc) {
        this.cc = cc;
        payments = new HashSet<>();
    }

    public int fillPayment(Wallet payment, Iterator<BigDecimal> vals) {
        Wallet unusedCoinsWallet = cc.getWallet().subtract(payment);
        while (vals.hasNext()) {
            BigDecimal walletVal = vals.next();
            int amount = cc.getCost().subtract(payment.getFullTotal()).divideToIntegralValue(walletVal).intValue();
            try {
                unusedCoinsWallet.subAmount(walletVal, amount);
            } catch (NegativeCoinAmountException e) {
                amount = unusedCoinsWallet.get(walletVal);
                unusedCoinsWallet.subAmount(walletVal, amount);
            }
            payment.addAmount(walletVal, amount);

            if (payment.getFullTotal().equals(cc.getCost()))
                break;
        }
        return payment.getFullTotal().compareTo(cc.getCost());
    }

    public void paymentDecomposition(Wallet payment, BigDecimal val) {
        while (val != null) {
            Set<BigDecimal> vals = cc.getWallet().headWallet(val).descendingKeySet();
            
            Wallet nextPayment = new Wallet(payment);
            try {
                nextPayment.subAmount(val, 1);
                if (fillPayment(nextPayment, vals.iterator()) >= 0) {
                    payments.add(nextPayment);
                    paymentDecomposition(new Wallet(nextPayment), val);
                }
            }
            catch (NegativeCoinAmountException e) {}

            val = payment.higherKey(val);
        }
    }

    @Override
    public Set<Wallet> payments() {
        if (cc.getWallet().getFullTotal().compareTo(cc.getCost()) >= 0) {
            Wallet firstPayment = new Wallet();
            fillPayment(firstPayment, cc.getWallet().descendingKeySet().iterator());

            payments.add(firstPayment);

            Wallet nextPayment = new Wallet(firstPayment);
            paymentDecomposition(nextPayment, nextPayment.firstKey());
        }

        return payments;
    }
    
}
