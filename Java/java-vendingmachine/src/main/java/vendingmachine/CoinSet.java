package vendingmachine;

public enum CoinSet {
    _500COIN(500),
    _100COIN(100),
    _50COIN(50),
    _10COIN(10),
    ;

    int value;

    CoinSet(int value) {
        this.value = value;
    }

}
