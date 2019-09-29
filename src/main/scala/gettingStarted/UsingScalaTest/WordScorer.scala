package gettingStarted.UsingScalaTest

/**
  * @Author: gin
  * @Date: Created in 上午11:28 19-7-15
  * @Description:
  */
class WordScorer {
  private val VOWELS = List('a', 'e', 'i', 'o', 'u')

  def score(word: String): Int = {
    (0 /: word) { (total, letter) =>
      total + (if (VOWELS.contains(letter)) 1 else 2)
    }
  }
}
