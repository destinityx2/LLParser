package lexis;

/**
 * Created by ivan on 19.02.17.
 */
public interface LexicalAnalyzer {
  /**
   * @return next token from the stream
   */
  Token nextToken();
}
