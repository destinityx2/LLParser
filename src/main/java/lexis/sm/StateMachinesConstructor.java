package lexis.sm;

/**
 * Created by ivan on 19.02.17.
 */
public class StateMachinesConstructor {

  /**
   * [a-zA-Z_][a-zA-Z0-9_]*
   */
  public static StateMachine variable() {
    int nStates = 2;
    StateMachine fsm = new FiniteStateMachine(nStates);

    for (char c = 'a'; c <= 'z'; ++c) {
      fsm.setTransition(1, c, 2);
      fsm.setTransition(2, c, 2);
    }

    for (char c = 'A'; c <= 'Z'; ++c) {
      fsm.setTransition(1, c, 2);
      fsm.setTransition(2, c, 2);
    }

    for (char c = '0'; c <= '9'; ++c) {
      fsm.setTransition(2, c, 2);
    }

    fsm.setTransition(2, '_', 2);
    fsm.setTransition(1, '_', 2);

    return fsm;
  }

  /**
   * [+-/*]
   */
//  public static StateMachine sign() {
//    int nStates = 2;
//    StateMachine fsm = new FiniteStateMachine(nStates);
//
//    fsm.setTransition(1, '+', 2);
//    fsm.setTransition(1, '-', 2);
//    fsm.setTransition(1, '*', 2);
//    fsm.setTransition(1, '/', 2);
//
//    return fsm;
//  }

  /**
   * [+]
   */
  public static StateMachine plus() {
    int nStates = 2;
    StateMachine fsm = new FiniteStateMachine(nStates);

    fsm.setTransition(1, '+', 2);

    return fsm;
  }

  /**
   * [-]
   */
  public static StateMachine minus() {
    int nStates = 2;
    StateMachine fsm = new FiniteStateMachine(nStates);

    fsm.setTransition(1, '-', 2);

    return fsm;
  }

  /**
   * [*]
   */
  public static StateMachine multiple() {
    int nStates = 2;
    StateMachine fsm = new FiniteStateMachine(nStates);

    fsm.setTransition(1, '*', 2);

    return fsm;
  }

  /**
   * [/]
   */
  public static StateMachine divide() {
    int nStates = 2;
    StateMachine fsm = new FiniteStateMachine(nStates);

    fsm.setTransition(1, '/', 2);

    return fsm;
  }

  /**
   * [(]
   */
  public static StateMachine leftParenthesis() {
    int nStates = 2;
    StateMachine fsm = new FiniteStateMachine(nStates);

    fsm.setTransition(1, '(', 2);

    return fsm;
  }

  /**
   * [)]
   */
  public static StateMachine rightParenthesis() {
    int nStates = 2;
    StateMachine fsm = new FiniteStateMachine(nStates);

    fsm.setTransition(1, ')', 2);

    return fsm;
  }
}
