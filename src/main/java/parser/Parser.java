package parser;

/**
 * Created by ivan on 19.02.17.
 */
public interface Parser<T> {
  /**
   * Parse input string via LexicalAnalyzer
   * @return object of type T (Ex: String - sequence of indices of applied rules)
   */
  T parse();
}
