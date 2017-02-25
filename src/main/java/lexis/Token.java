package lexis;

/**
 * Created by ivan on 19.02.17.
 */
public class Token {
  /**
   * Types of token
   * Note: extend this list if necessary
   */
  public enum Type {
    VARIABLE, PLUS, MINUS, MULTIPLE, DIVIDE, LEFT_PAR, RIGHT_PAR, EMPTY, END, UNEXPECTED
  }

  private Type mType;
  private String mLexeme;

  Token(Type type, String lexeme) {
    mType = type;
    mLexeme = lexeme;
  }

  public Type getType() {
    return mType;
  }

  public String getLexeme() {
    return mLexeme;
  }

  public boolean isFinished() {
    return mType == Type.END;
  }

  public boolean isUnexpected() {
    return mType == Type.UNEXPECTED;
  }
}
