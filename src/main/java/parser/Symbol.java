package parser;

import lexis.Token;

/**
 * Created by ivan on 19.02.17.
 */
public class Symbol {
  public enum Type {
    TERMINAL, NON_TERMINAL
  }

  private Type mType;
  private String mNonTerminal;
  private Token.Type mTerminal;

  private Symbol(Type t, String nonTerminal, Token.Type terminal) {
    mType = t;
    mNonTerminal = nonTerminal;
    mTerminal = terminal;
  }

  public Symbol(Token.Type terminal) {
    this(Type.TERMINAL, null, terminal);
  }

  public Symbol(String nonTerminal) {
    this(Type.NON_TERMINAL, nonTerminal, null);
  }

  public Type getType() {
    return mType;
  }

  public Token.Type getTerminal() {
    return mTerminal;
  }

  public String getNonTerminal() {
    return mNonTerminal;
  }

  public boolean isTerminal() {
    return mType == Type.TERMINAL;
  }
}
