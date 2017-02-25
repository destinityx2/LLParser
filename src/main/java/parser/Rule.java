package parser;

import java.util.List;

/**
 * Created by ivan on 19.02.17.
 */
public class Rule {
  private String mNonTerminal;
  private List<Symbol> mProduct;

  public Rule(String nonTerminal, List<Symbol> product) {
    mNonTerminal = nonTerminal;
    mProduct = product;
  }

  public String getNonTerminal() {
    return mNonTerminal;
  }

  public List<Symbol> getProduct() {
    return mProduct;
  }
}
