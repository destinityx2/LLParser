package parser;

import lexis.LexicalAnalyzer;
import lexis.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 19.02.17.
 */

public class SimpleLLParser implements Parser<String> {
  private Grammar mGrammar;

  // mTable[nonTerminal][inputToken] = number_of_applied_rule
  private Map<String, Map<Token.Type, Integer>> mTable;

  private LexicalAnalyzer mLexicalAnalyzer;

  private List<Symbol> mStack;

  public SimpleLLParser(Grammar grammar, LexicalAnalyzer lexicalAnalyzer) {
    mGrammar = grammar;
    mLexicalAnalyzer = lexicalAnalyzer;
    mStack = new ArrayList<Symbol>();

    mTable = new HashMap<String, Map<Token.Type, Integer>>();
  }

  public void set(String nonTerminal, Token.Type inputTokenType, Integer ruleNumber) {
    if (mTable.containsKey(nonTerminal)) {
      Map<Token.Type, Integer> map = mTable.get(nonTerminal);
      map.put(inputTokenType, ruleNumber);
    } else {
      Map<Token.Type, Integer> map = new HashMap<Token.Type, Integer>();
      map.put(inputTokenType, ruleNumber);
      mTable.put(nonTerminal, map);
    }
  }

  private Integer get(String nonTerminal, Token.Type inputTokenType) {
    if (mTable.containsKey(nonTerminal)) {
      return mTable.get(nonTerminal).getOrDefault(inputTokenType, null);
    }

    return null;
  }

  /**
   * Parse input string
   * @return sequence of indices of applied rules in form "i1 i2 i3 i4 i5 ..."
   */
  public String parse() {
    String result;
    List<Integer> ruleSequence = new ArrayList<Integer>();

    mStack.add(new Symbol(Token.Type.END));
    mStack.add(new Symbol(mGrammar.getStartNonTerminal()));

    Token curToken = mLexicalAnalyzer.nextToken();

    while (true) {
      if (curToken.isUnexpected()) {
        error("Unexpected token in input");
        break;
      }

      if (last().isTerminal() && last().getTerminal() == Token.Type.END) {
        break;
      }

      if (last().isTerminal()) {
        if (last().getTerminal() == curToken.getType()) {
          pop();
          curToken = mLexicalAnalyzer.nextToken();
        } else {
          error("Incorrect input sequence");
          break;
        }
      } else {
        Integer ruleNumber = get(last().getNonTerminal(), curToken.getType());

        if (ruleNumber != null) {
          Rule rule = mGrammar.getRule(ruleNumber);
          pop();
          List<Symbol> product = rule.getProduct();

          for (int i = product.size() - 1; i >= 0; i--) {
            Symbol symbol = product.get(i);

            // Skip empty symbols
            if (symbol.isTerminal() && symbol.getTerminal() == Token.Type.EMPTY)
              continue;

            mStack.add(symbol);
          }

          ruleSequence.add(ruleNumber);

        } else {
          error("No such transition in table");
          break;
        }
      }
    }

    result = ruleSequence.toString();
    return result;
  }

  private void error(String msg) {
    System.out.println(msg);
  }

  private Symbol last() {
    return mStack.get(mStack.size() - 1);
  }

  private void pop() {
    mStack.remove(mStack.size() - 1);
  }
}
