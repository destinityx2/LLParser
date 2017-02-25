import lexis.LexicalAnalyzer;
import lexis.SimpleLexicalAnalyzer;
import lexis.Token;
import lexis.sm.StateMachine;
import lexis.sm.StateMachinesConstructor;
import org.junit.Test;
import parser.*;
import utils.Pair;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ivan on 19.02.17.
 */
public class SimpleLLParserTest {
  private Grammar grammar = constructGrammar();
  private List<Pair<StateMachine, Token.Type>> stateMachines = constructStateMachines();

  // Non-terminals
  static final String S = "S";
  static final String E = "E";
  static final String E2 = "E2";
  static final String T1 = "T1";
  static final String T = "T";
  static final String T2 = "T2";
  static final String F1 = "F1";
  static final String F = "F1";

  /**
   * Lexical rules:
   * PLUS: +
   * MINUS: -
   * MULTIPLE: *
   * DIVIDE: /
   * VARIABLE: [a-zA-Z_][a-zA-Z0-9_]*
   *
   * Grammar rules:
   * S -> E
   * E -> TE''
   * E'' -> T'E''
   * E'' -> epsilon
   * T' -> +T
   * T' -> -T
   * T -> FT''
   * T'' -> epsilon
   * T'' -> F'T''
   * F' -> *F
   * F' -> /F
   * F -> VARIABLE
   * F -> (E)
   *
   * */

  @Test
  public void testLLParser() {
    String s1 = " abc*  (def + aAs_23)";
    String s1Exp = "[0, 1, 6, 11, 8, 9, 12, 1, 6, 11, 7, 2, 4, 6, 11, 7, 3, 7, 3]";
    performTest(s1, s1Exp);

    String s2 = " eads_abc    *( de_1f + aA__Bs_23)";
    String s2Exp = "[0, 1, 6, 11, 8, 9, 12, 1, 6, 11, 7, 2, 4, 6, 11, 7, 3, 7, 3]";
    performTest(s2, s2Exp);

    String s3 = "((ivan))";
    String s3Exp = "[0, 1, 6, 12, 1, 6, 12, 1, 6, 11, 7, 3, 7, 3, 7, 3]";
    performTest(s3, s3Exp);

    String s4 = "(a)";
    String s4Exp = "[0, 1, 6, 12, 1, 6, 11, 7, 3, 7, 3]";
    performTest(s4, s4Exp);
  }

  private void performTest(String s, String expected) {
    Parser<String> parser = constructSimpleLLParser(s);
    String s1Res = parser.parse();

    assertEquals(expected, s1Res);
  }

  private Parser<String> constructSimpleLLParser(String s) {
    LexicalAnalyzer la = constructLexicalAnalyzer(s);

    SimpleLLParser parser = new SimpleLLParser(grammar, la);

    // Fulfill parser table
    parser.set(S, Token.Type.VARIABLE, 0);
    parser.set(S, Token.Type.LEFT_PAR, 0);

    parser.set(E, Token.Type.VARIABLE, 1);
    parser.set(E, Token.Type.LEFT_PAR, 1);

    parser.set(E2, Token.Type.PLUS, 2);
    parser.set(E2, Token.Type.MINUS, 2);
    parser.set(E2, Token.Type.RIGHT_PAR, 3);
    parser.set(E2, Token.Type.END, 3);

    parser.set(T1, Token.Type.PLUS, 4);
    parser.set(T1, Token.Type.MINUS, 5);

    parser.set(T, Token.Type.VARIABLE, 6);
    parser.set(T, Token.Type.LEFT_PAR, 6);

    parser.set(T2, Token.Type.PLUS, 7);
    parser.set(T2, Token.Type.MINUS, 7);
    parser.set(T2, Token.Type.MULTIPLE, 8);
    parser.set(T2, Token.Type.DIVIDE, 8);
    parser.set(T2, Token.Type.RIGHT_PAR, 7);
    parser.set(T2, Token.Type.END, 7);

    parser.set(F1, Token.Type.MULTIPLE, 9);
    parser.set(F1, Token.Type.DIVIDE, 10);

    parser.set(F, Token.Type.VARIABLE, 11);
    parser.set(F, Token.Type.LEFT_PAR, 12);

    return parser;
  }

  private LexicalAnalyzer constructLexicalAnalyzer(String s) {
    return new SimpleLexicalAnalyzer(stateMachines, s);
  }

  private List<Pair<StateMachine, Token.Type>> constructStateMachines() {
    List<Pair<StateMachine, Token.Type>> l = new ArrayList<Pair<StateMachine, Token.Type>>();

    l.add(new Pair<StateMachine, Token.Type>(StateMachinesConstructor.variable(), Token.Type.VARIABLE));
    l.add(new Pair<StateMachine, Token.Type>(StateMachinesConstructor.plus(), Token.Type.PLUS));
    l.add(new Pair<StateMachine, Token.Type>(StateMachinesConstructor.minus(), Token.Type.MINUS));
    l.add(new Pair<StateMachine, Token.Type>(StateMachinesConstructor.multiple(), Token.Type.MULTIPLE));
    l.add(new Pair<StateMachine, Token.Type>(StateMachinesConstructor.divide(), Token.Type.DIVIDE));
    l.add(new Pair<StateMachine, Token.Type>(StateMachinesConstructor.leftParenthesis(), Token.Type.LEFT_PAR));
    l.add(new Pair<StateMachine, Token.Type>(StateMachinesConstructor.rightParenthesis(), Token.Type.RIGHT_PAR));
    return l;
  }

  private Grammar constructGrammar() {
    Grammar g = new Grammar(S);
    g.addRule(new Rule(S, Arrays.asList(new Symbol(E))));

    g.addRule(new Rule(E, Arrays.asList(new Symbol(T), new Symbol(E2))));

    g.addRule(new Rule(E2, Arrays.asList(new Symbol(T1), new Symbol(E2))));
    g.addRule(new Rule(E2, Arrays.asList(new Symbol(Token.Type.EMPTY))));

    g.addRule(new Rule(T1, Arrays.asList(new Symbol(Token.Type.PLUS), new Symbol(T))));
    g.addRule(new Rule(T1, Arrays.asList(new Symbol(Token.Type.MINUS), new Symbol(T))));

    g.addRule(new Rule(T, Arrays.asList(new Symbol(F), new Symbol(T2))));

    g.addRule(new Rule(T2, Arrays.asList(new Symbol(Token.Type.EMPTY))));
    g.addRule(new Rule(T2, Arrays.asList(new Symbol(F1), new Symbol(T2))));

    g.addRule(new Rule(F1, Arrays.asList(new Symbol(Token.Type.MULTIPLE), new Symbol(F))));
    g.addRule(new Rule(F1, Arrays.asList(new Symbol(Token.Type.DIVIDE), new Symbol(F))));

    g.addRule(new Rule(F, Arrays.asList(new Symbol(Token.Type.VARIABLE))));
    g.addRule(new Rule(F, Arrays.asList(new Symbol(Token.Type.LEFT_PAR),
            new Symbol(E), new Symbol(Token.Type.RIGHT_PAR))));

    return g;
  }
}
