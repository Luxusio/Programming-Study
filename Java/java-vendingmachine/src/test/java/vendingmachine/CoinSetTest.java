package vendingmachine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CoinSetTest {

    @Test
    void create_coin() {
        CoinSet coin = CoinSet._100COIN;
        assertThat(coin.value).isEqualTo(100);
    }

    @ParameterizedTest(name = "there are {1}won coin")
    @MethodSource("coinSet")
    void there_are_500won_100won_50won_10won_coins(int value, CoinSet coin) {
        assertThat(coin.value).isEqualTo(value);
    }

    static List<Arguments> coinSet() {
        return Arrays.asList(
                Arguments.of(500, CoinSet._500COIN),
                Arguments.of(100, CoinSet._100COIN),
                Arguments.of(50, CoinSet._50COIN),
                Arguments.of(10, CoinSet._10COIN)
        );
    }

}