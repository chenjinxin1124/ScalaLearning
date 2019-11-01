package beginnersGuideToScala.chp11CurryingAndPartiallyAppliedFunctions

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:38 19-10-31
  * @Description:
  */
object Demo extends App {

  case class Email(
                    subject: String,
                    text: String,
                    sender: String,
                    recipient: String)

  type EmailFilter = Email => Boolean

  type IntPairPred = (Int, Int) => Boolean

  def sizeConstraint(pred: IntPairPred, n: Int, email: Email) =
    pred(email.text.size, n)

  val gt: IntPairPred = _ > _
  val ge: IntPairPred = _ >= _
  val lt: IntPairPred = _ < _
  val le: IntPairPred = _ <= _
  val eq: IntPairPred = _ == _

  val minimumSize: (Int, Email) => Boolean = sizeConstraint(ge, _: Int, _: Email)
  val maximumSize: (Int, Email) => Boolean = sizeConstraint(le, _: Int, _: Email)

  val constr20: (IntPairPred, Email) => Boolean =
    sizeConstraint(_: IntPairPred, 20, _: Email)

  val constr30: (IntPairPred, Email) => Boolean =
    sizeConstraint(_: IntPairPred, 30, _: Email)

  //更有趣的函数
  def sizeConstraint2(pred: IntPairPred)(n: Int)(email: Email): Boolean =
    pred(email.text.size, n)

  //柯里化函数
  val sizeConstraintFn: IntPairPred => Int => Email => Boolean = sizeConstraint2 _
  val minSize: Int => Email => Boolean = sizeConstraint2(ge)
  val maxSize: Int => Email => Boolean = sizeConstraint2(le)
  val min20: Email => Boolean = minSize(20)
  val max20: Email => Boolean = maxSize(20)
  val min20_2: Email => Boolean = sizeConstraintFn(ge)(20)
  val max20_2: Email => Boolean = sizeConstraintFn(le)(20)

  //函数柯里化
  val sum: (Int, Int) => Int = _ + _
  val sumCurried: Int => Int => Int = sum.curried

  //函数化的依赖注入
  case class User(name: String)
  trait EmailRepository {
    def getMails(user: User, unread: Boolean): Seq[Email]
  }
  trait FilterRepository {
    def getEmailFilter(user: User): EmailFilter
  }
  trait MailboxService {
    def getNewMails(emailRepo: EmailRepository)(filterRepo: FilterRepository)(user: User) =
      emailRepo.getMails(user, true).filter(filterRepo.getEmailFilter(user))
    val newMails: User => Seq[Email]
  }

  object MockEmailRepository extends EmailRepository {
    def getMails(user: User, unread: Boolean): Seq[Email] = Nil
  }
  object MockFilterRepository extends FilterRepository {
    def getEmailFilter(user: User): EmailFilter = _ => true
  }
  object MailboxServiceWithMockDeps extends MailboxService {
    val newMails: (User) => Seq[Email] =
      getNewMails(MockEmailRepository)(MockFilterRepository) _
  }

  val res = MailboxServiceWithMockDeps.newMails(User("daniel"))
  println(res)

}
