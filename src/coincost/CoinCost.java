package coincost;

import java.math.BigDecimal;
import java.util.Iterator;

public class CoinCost {
    private CoinPile wallet;
    private BigDecimal cost;

    public CoinCost(CoinPile wallet, BigDecimal cost) {
        this.wallet = new CoinPile(wallet);
        this.cost = new BigDecimal(cost.toString());
    }

    public CoinPile getWallet() {
        return wallet;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Iterator<CoinPile> payments() {
        return new TopDownPaymentCalcItr(wallet);
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(wallet.toString());

        sBuilder.append(cost);

        return sBuilder.toString();
    }

    private class TopDownPaymentCalcItr implements Iterator<CoinPile>, IPaymentCalculator {
        private CoinPile unusedCoinPile;
        private CoinPile currentCoinPile;

        public TopDownPaymentCalcItr(CoinPile coinPile) {
            unusedCoinPile = new CoinPile(coinPile);
            currentCoinPile = new CoinPile();
            start();
        }

        public void start() {

        }

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public CoinPile next() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Iterator<CoinPile> payments(CoinPile wallet) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private class BottomUpPaymentCalcItr implements Iterator<CoinPile>, IPaymentCalculator {

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public CoinPile next() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Iterator<CoinPile> payments(CoinPile wallet) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     String[] coinPileStrings = sc.nextLine().split(" ");
    //     BigDecimal cost = new BigDecimal(sc.next());
    //     sc.close();
        
    //     Pattern pattern = Pattern.compile("\\((\\d+.\\d+|\\d+),(\\d+)\\)");
        
    //     CoinPile coinPiles = new CoinPile[coinPileStrings.length];
    //     int i = 0;
    //     for (String coinPileString : coinPileStrings) {
    //         Matcher matcher = pattern.matcher(coinPileString);
    //         matcher.matches();
    //         String value = matcher.group(1);
    //         int amount = Integer.parseInt(matcher.group(2));

    //         coinPiles[i++] = new CoinPile(value, amount);
    //     }

    //     CoinCost coinCost = new CoinCost(coinPiles, cost);

    //     Iterator<CoinPile> payments = coinCost.payments();

    //     while (payments.hasNext()) {
    //         for (CoinPile coinPile : payments.next()) {
    //             System.out.print(coinPile.toString() + ' ');
    //         }
    //         System.out.println();
    //     }
    // }
}
