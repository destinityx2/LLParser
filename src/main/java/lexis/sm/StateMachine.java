package lexis.sm; /**
 * Created by ivan on 19.02.17.
 */

/**
 * Interface for the state machine.
 *
 * State 0 - failed state
 * All valid states have to start from one
 */
public interface StateMachine {
  /**
   * Restart state machine by changing currentState to initialState
   */
  void restart();

  /**
   * Change current state to the next state w.r.t. character {@code c}
   *
   * @param c next character in the stream
   * @return next state
   */
  int nextState(char c);

  /**
   * This method is the same as {@link #nextState(char)}, but one difference is that
   * this method doesn't change currentState, just returns the possible
   * next state
   */
  int checkNextState(char c);

  /**
   * Set transition from ({@code fromState}, {@code c}) to {@code toState}
   */
  void setTransition(int fromState, char c, int toState);

  int getCurrentState();

  boolean isFailed();

  boolean isFailedInNextState(char c);

  boolean isSucceed();
}
