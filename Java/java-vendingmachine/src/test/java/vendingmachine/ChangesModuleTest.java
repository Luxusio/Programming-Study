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
    void create_changesModule() {
        ChangesModule changesModule = new ChangesModule();
        assertThat(changesModule).isNotNull();
    }

    @ParameterizedTest(name = "insert {0}won then we can know {0}won in changes module ")
    @ValueSource(ints = {500, 1000})
    void insert_n_won_then_we_can_know_n_won_in_changesModule(int changes) {
        ChangesModule changesModule = new ChangesModule();
        changesModule.put(changes);
        assertThat(changesModule.getChanges()).isEqualTo(changes);
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