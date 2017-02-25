import lexis.LexicalAnalyzer;
import lexis.SimpleLexicalAnalyzer;
import lexis.Token;
import lexis.sm.StateMachine;
import lexis.sm.StateMachinesConstructor;
import org.junit.Test;
import utils.Pair;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 19.02.17.
 */
public class SimpleLexicalAnalyzerTest {
  private List<Pair<StateMachine, Token.Type>> l = constructStateMachines();

  @Test
  public void testLexicalAnalyzer() {

    String s1 = "heello +";
    String s1Ans = "var[heello] plus[+] end[] ";
    performTest(s1, s1Ans);

    String s2 = "vanya nope";
    String s2Ans = "var[vanya] var[nope] end[] ";
    performTest(s2, s2Ans);

    String s3 = "X+Y/Z";
    String s3Ans = "var[X] plus[+] var[Y] divide[/] var[Z] end[] ";
    performTest(s3, s3Ans);

    String s4 = "X +  Y /       Z";
    String s4Ans = "var[X] plus[+] var[Y] divide[/] var[Z] end[] ";
    performTest(s4, s4Ans);

    String s5 = "X'";
    String s5Ans = "var[X] unexpected[] ";
    performTest(s5, s5Ans);

    String s6 = "(   ( X) * Y";
    String s6Ans = "left_par[(] left_par[(] var[X] right_par[)] multiple[*] var[Y] end[] ";
    performTest(s6, s6Ans);

    String s7 = "";
    String s7Ans = "end[] ";
    performTest(s7, s7Ans);
  }

  private void performTest(String s, String expected) {
    LexicalAnalyzer la = new SimpleLexicalAnalyzer(l, s);
    String res = constructString(la);
    assertEquals(expected, res);
  }

  private String constructString(LexicalAnalyzer la) {
    StringBuilder sb = new StringBuilder();
    Token t;
    while (true) {
      t = la.nextToken();
      sb.append(typeToString(t.getType()));
      sb.append("[");
      sb.append(t.getLexeme());
      sb.append("] ");

      if (t.isFinished() || t.isUnexpected()) {
        break;
      }
    }

    return sb.toString();
  }

  private String typeToString(Token.Type t) {
    switch (t) {
      case VARIABLE:
        return "var";
      case PLUS:
        return "plus";
      case MINUS:
        return "minus";
      case MULTIPLE:
        return "multiple";
      case DIVIDE:
        return "divide";
      case LEFT_PAR:
        return "left_par";
      case RIGHT_PAR:
        return "right_par";
      case END:
        return "end";
      case UNEXPECTED:
        return "unexpected";
      default:
        return "undefined_type";
    }
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
}
