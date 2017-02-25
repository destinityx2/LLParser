package lexis;

import utils.Pair;
import lexis.sm.StateMachine;

import java.util.List;

/**
 * Created by ivan on 19.02.17.
 */
public class SimpleLexicalAnalyzer implements LexicalAnalyzer {
  // State machines ordered with priorities
  // mStateMachines[i] has higher priority than mStateMachines[j] if i < j
  private List<Pair<StateMachine, Token.Type>> mStateMachines;
  private String mS;

  private boolean mIsSkipWhiteSpace;

  // We extract lexeme [mBeginInd, mEndInd)
  private int mBeginInd;
  private int mEndInd;

  public SimpleLexicalAnalyzer(List<Pair<StateMachine, Token.Type>> stateMachines,
                               String s, boolean isSkipWhiteSpace) {
    mStateMachines = stateMachines;
    mS = s;
    mIsSkipWhiteSpace = isSkipWhiteSpace;

    mBeginInd = 0;
    mEndInd = 0;

    restartMachines();
  }

  private void restartMachines() {
    for (Pair<StateMachine, Token.Type> pair : mStateMachines) {
      pair.first.restart();
    }
  }

  public SimpleLexicalAnalyzer(List<Pair<StateMachine, Token.Type>> stateMachines, String s) {
    this(stateMachines, s, true);
  }

  public Token nextToken() {
    skipWhiteSpaces();

    if (mEndInd >= mS.length())
      return new Token(Token.Type.END, "");

    Token t;
    boolean areAllFailed = false;
    while (mEndInd < mS.length() && !areAllFailed) {
      char c = mS.charAt(mEndInd);
      areAllFailed = true;

      for (Pair<StateMachine, Token.Type> pair : mStateMachines) {
        areAllFailed = areAllFailed && pair.first.isFailedInNextState(c);
      }

      if (!areAllFailed) {
        for (Pair<StateMachine, Token.Type> pair : mStateMachines) {
          pair.first.nextState(c);
        }
      }
      mEndInd++;
    }

    if (areAllFailed) {
      mEndInd--;
    }

    t = new Token(getSucceedMachineType(), mS.substring(mBeginInd, mEndInd));

    skipWhiteSpaces();

    mBeginInd = mEndInd;
    restartMachines();

    return t;
  }

  private void skipWhiteSpaces() {
    if (mIsSkipWhiteSpace) {
      while (mEndInd < mS.length() && isWhiteSpace(mS.charAt(mEndInd)))
        mEndInd++;
    }
  }

  private boolean isWhiteSpace(char c) {
    return c == ' ' || c == '\t' || c == '\n';
  }

  private Token.Type getSucceedMachineType() {
    Token.Type type = Token.Type.UNEXPECTED;
    for (Pair<StateMachine, Token.Type> pair : mStateMachines) {
      if (pair.first.isSucceed()) {
        type = pair.second;
        break;
      }
    }

    return type;
  }
}
