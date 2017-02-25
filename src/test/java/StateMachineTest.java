import lexis.sm.StateMachine;
import lexis.sm.StateMachinesConstructor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by ivan on 19.02.17.
 */
public class StateMachineTest {

  public void runMachine(StateMachine fsm, String s) {
    fsm.restart();

    for (int i = 0; i < s.length(); ++i) {
      fsm.nextState(s.charAt(i));
    }
  }

  @Test
  public void testVariableStateMachine() {
    String acceptableString1 = "abc2";
    String acceptableString2 = "AdZ341a";
    String acceptableString3 = "_Z_X_Y";
    String nonAcceptableString1 = "2abc";
    String nonAcceptableString2 = "abc ";
    String nonAcceptableString3 = "abc 2";
    String nonAcceptableString4 = "hello ";

    StateMachine fsm = StateMachinesConstructor.variable();

    runMachine(fsm, acceptableString1);
    assertTrue(fsm.isSucceed());

    runMachine(fsm, acceptableString2);
    assertTrue(fsm.isSucceed());

    runMachine(fsm, acceptableString3);
    assertTrue(fsm.isSucceed());

    runMachine(fsm, nonAcceptableString1);
    assertTrue(fsm.isFailed());

    runMachine(fsm, nonAcceptableString2);
    assertTrue(fsm.isFailed());

    runMachine(fsm, nonAcceptableString3);
    assertTrue(fsm.isFailed());

    runMachine(fsm, nonAcceptableString4);
    assertTrue(fsm.isFailed());
  }
}
