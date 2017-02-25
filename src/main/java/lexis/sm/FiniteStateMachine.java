package lexis.sm;

/**
 * Created by ivan on 19.02.17.
 */
public class FiniteStateMachine implements StateMachine {
  private static int N_ASCII_CHARACTERS = 256;

  // States from 1 to nStates
  private int mNStates;

  // State is valid if it's from 1..nStates
  // State is invalid if it's zero
  private int mCurrentState;

  private final int mInitialState;
  private final int mFinalState;

  // First dimension - state
  // Second dimension - character
  private int[][] mTransitions;

  public FiniteStateMachine(int nStates, int initialState, int finalState) {
    mInitialState = initialState;
    mFinalState = finalState;
    mNStates = nStates;

    // We are adding +1 to nStates, because we want to take "invalid" state into account
    mTransitions = new int[mNStates + 1][N_ASCII_CHARACTERS];

    mCurrentState = mInitialState;
  }

  public FiniteStateMachine(int nStates) {
    this(nStates, 1, nStates);
  }

  // fromState, toState have to be in range [1, nStates]
  public void setTransition(int fromState, char c, int toState) {
    mTransitions[fromState][c] = toState;
  }

  public void restart() {
    mCurrentState = mInitialState;
  }

  public int nextState(char c) {
    mCurrentState = mTransitions[mCurrentState][c];
    return mCurrentState;
  }

  public int checkNextState(char c) {
    return mTransitions[mCurrentState][c];
  }

  public int getCurrentState() {
    return mCurrentState;
  }

  public boolean isFailed() {
    return mCurrentState == 0;
  }

  public boolean isFailedInNextState(char c) {
    return checkNextState(c) == 0;
  }

  public boolean isSucceed() {
    return mCurrentState == mFinalState;
  }
}
