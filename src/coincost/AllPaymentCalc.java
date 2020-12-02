package coincost;

import java.util.HashSet;
import java.util.Set;

public class AllPaymentCalc implements IPaymentCalc {
    private CoinCost cc;
    private Set<CoinPile> payments;

    public AllPaymentCalc(CoinCost cc) {
        this.cc = cc;
        payments = new HashSet<>();
    }

    @Override
    public Set<CoinPile> payments() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
