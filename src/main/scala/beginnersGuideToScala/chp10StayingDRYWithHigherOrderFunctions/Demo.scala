package beginnersGuideToScala.chp10StayingDRYWithHigherOrderFunctions

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:29 19-10-31
  * @Description:
  */
object Demo extends App {

  //函数生成
  case class Email(
                    subject: String,
                    text: String,
                    sender: String,
                    recipient: String
                  )

  type EmailFilter = Email => Boolean

  def newMailsForUser(mails: Seq[Email], f: EmailFilter) = mails.filter(f)

  val sentByOneOf: Set[String] => EmailFilter =
    senders =>
      email => senders.contains(email.sender)

  val notSentByAnyOf: Set[String] => EmailFilter =
    senders =>
      email => !senders.contains(email.sender)
  val minimumSize: Int => EmailFilter =
    n => email => email.text.size >= n
  val maximumSize: Int => EmailFilter =
    n => email => email.text.size <= n

  val emailFilter: EmailFilter = notSentByAnyOf(Set("johndoe@example.com"))
  val mails = Email(
    subject = "It's me again, your stalker friend!",
    text = "Hello my friend! How are you?",
    sender = "ohndoe@example.com",
    recipient = "me@example.com") :: Nil
  val r1 = newMailsForUser(mails, emailFilter)
  println(r1)

  //重用已有函数
  type SizeChecker = Int => Boolean
  val sizeConstraint: SizeChecker => EmailFilter =
    f =>
      email => f(email.text.size)

  val minimumSize2: Int => EmailFilter =
    n => sizeConstraint(_ >= n)
  val maximumSize2: Int => EmailFilter =
    n => sizeConstraint(_ <= n)

  //函数组合
  def complement[A](predicate: A => Boolean) = (a: A) => !predicate(a)

  val notSentByAnyOf2 = sentByOneOf andThen (complement(_))

  //谓词组合
  def any[A](predicates: (A => Boolean)*): A => Boolean =
    a => predicates.exists(pred => pred(a))

  def none[A](predicates: (A => Boolean)*) = complement(any(predicates: _*))

  def every[A](predicates: (A => Boolean)*) = none(predicates.view.map(complement(_)): _*)

  val filter: EmailFilter = every(
    notSentByAnyOf(Set("johndoe@example.com")),
    minimumSize(100),
    maximumSize(10000)
  )
  println(filter)

  //流水线组合
  val addMissingSubject = (email: Email) =>
    if (email.subject.isEmpty) email.copy(subject = "No subject")
    else email
  val checkSpelling = (email: Email) =>
    email.copy(text = email.text.replaceAll("your", "you're"))
  val removeInappropriateLanguage = (email: Email) =>
    email.copy(text = email.text.replaceAll("dynamic typing", "**CENSORED**"))
  val addAdvertismentToFooter = (email: Email) =>
    email.copy(text = email.text + "\nThis mail sent via Super Awesome Free Mail")

  val pipeline = Function.chain(Seq(
    addMissingSubject,
    checkSpelling,
    removeInappropriateLanguage,
    addAdvertismentToFooter))

  //高阶函数与偏函数
  //链接偏函数
  //  val handler = fooHandler orElse barHandler orElse bazHandler
  //  println(handler)

}
