package vendingmachine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;


/**
 * Red(질문) -> Green(응답) -> Refactor(정제)
 */
class ChangesModuleTest {

    @Test
    void create_vendingMachine() {
        VendingMachine vendingMachine = new VendingMachine();
        assertThat(vendingMachine).isNotNull();
    }

    @ParameterizedTest(name = "insert {0}won then we can know {0}won in changes module ")
    @ValueSource(ints = {500, 1000})
    void insert_n_won_then_we_can_know_n_won_in_changesModule(int changes) {
        ChangesModule changesModule = new ChangesModule();
        changesModule.put(changes);
        assertThat(changesModule.getChanges()).isEqualTo(changes);
    }

    @ParameterizedTest(name = "자판기에 {0}원을 넣으면 {0}원이 들어있음을 알 수 있다")
    @ValueSource(ints = {500, 1000, 10000, 200, 111, 223})
    void 자판기에_n원을_넣으면_n원이_들어있음을_알수있다(int changes) {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.put(changes);
        assertThat(vendingMachine.getChanges()).isEqualTo(changes);
    }

    @Test
    void _500원이_들어있는_자판기에_500원을_뺄시_0원이_들어있음을_알수있다() {
        VendingMachine vendingMachine = new VendingMachine(500);
        vendingMachine.withdraw(500);
        assertThat(vendingMachine.getChanges()).isEqualTo(0);
    }

    @Test
    void _500원이_들어있는_자판기에서_1000원을_뺄수_없다() {
        ChangesModule changesModule = new ChangesModule(0);
        assertThatIllegalStateException()
                .isThrownBy(() -> changesModule.withdraw(500));
    }

    @Test
    void vendingMachine_that_has_1000won_and_insert_500won_then_we_can_know_vending_machine_has_500won() {
        ChangesModule changesModule = new ChangesModule(1000);
        changesModule.put(500);
        assertThat(changesModule.getChanges()).isEqualTo(1500);
    }

    @Test
    void withdraw_500won_from_vendingMachine_has_500won_then_vendingMachine_has_no_money() {
        ChangesModule changesModule = new ChangesModule(500);
        changesModule.withdraw(500);
        assertThat(changesModule.getChanges()).isEqualTo(0);
    }

    @Test
    void withdraw_500won_from_vendingMachine_has_0won_then_cannot_withdraw() {
        ChangesModule changesModule = new ChangesModule(0);
        assertThatIllegalStateException()
                .isThrownBy(() -> changesModule.withdraw(500));
    }

}