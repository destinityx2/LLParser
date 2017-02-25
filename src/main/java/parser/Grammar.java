package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 19.02.17.
 */
public class Grammar {
  private List<Rule> mRules;
  private String mStartNonTerminal;

  public Grammar(String startNonTerminal) {
    mRules = new ArrayList<Rule>();
    mStartNonTerminal = startNonTerminal;
  }

  public Grammar(List<Rule> rules, String startNonTerminal) {
    mRules = rules;
    mStartNonTerminal = startNonTerminal;
  }

  public Grammar(List<Rule> rules) {
    this(rules, rules.get(0).getNonTerminal());
  }

  public void addRule(Rule rule) {
    mRules.add(rule);
  }

  public Rule getRule(int i) {
    return mRules.get(i);
  }

  public List<Rule> getRules() {
    return mRules;
  }

  public String getStartNonTerminal() {
    return mStartNonTerminal;
  }
}
