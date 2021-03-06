package tethys.commons

import tethys.commons.Token._

sealed trait TokenNode {
  def token: Token
}

object TokenNode {

  case object NullValueNode extends TokenNode {
    override val token: Token = NullValueToken
  }
  case object ArrayStartNode extends TokenNode {
    override val token: Token = ArrayStartToken
  }
  case object ArrayEndNode extends TokenNode {
    override val token: Token = ArrayEndToken
  }
  case object ObjectStartNode extends TokenNode {
    override val token: Token = ObjectStartToken
  }
  case object ObjectEndNode extends TokenNode {
    override val token: Token = ObjectEndToken
  }

  case class FieldNameNode(value: String) extends TokenNode {
    override val token: Token = FieldNameToken
  }
  case class StringValueNode(value: String) extends TokenNode {
    override val token: Token = StringValueToken
  }
  case class BooleanValueNode(value: Boolean) extends TokenNode {
    override val token: Token = BooleanValueToken
  }
  case class NumberValueNode(value: Number) extends TokenNode {
    override val token: Token = NumberValueToken
  }

  case class ShortValueNode(value: Short) extends TokenNode {
    override val token: Token = NumberValueToken
  }
  case class IntValueNode(value: Int) extends TokenNode {
    override val token: Token = NumberValueToken
  }
  case class LongValueNode(value: Long) extends TokenNode {
    override val token: Token = NumberValueToken
  }
  case class FloatValueNode(value: Float) extends TokenNode {
    override val token: Token = NumberValueToken
  }
  case class DoubleValueNode(value: Double) extends TokenNode {
    override val token: Token = NumberValueToken
  }


  def obj(fields: (String, Any)*): List[TokenNode] = {
    val tokens = fields.toList.flatMap {
      case (name, a) => FieldNameNode(name) :: anyToTokens(a)
    }

    ObjectStartNode :: tokens ::: ObjectEndNode :: Nil
  }

  def arr(elems: Any*): List[TokenNode] = {
    ArrayStartNode :: elems.toList.flatMap(anyToTokens) ::: ArrayEndNode :: Nil
  }

  def value(v: String): List[TokenNode] = StringValueNode(v) :: Nil
  def value(v: Boolean): List[TokenNode] = BooleanValueNode(v) :: Nil
  def value(v: Short): List[TokenNode] = ShortValueNode(v) :: Nil
  def value(v: Int): List[TokenNode] = IntValueNode(v) :: Nil
  def value(v: Long): List[TokenNode] = LongValueNode(v) :: Nil
  def value(v: Float): List[TokenNode] = FloatValueNode(v) :: Nil
  def value(v: Double): List[TokenNode] = DoubleValueNode(v) :: Nil
  def value(v: BigInt): List[TokenNode] = NumberValueNode(v) :: Nil
  def value(v: BigDecimal): List[TokenNode] = NumberValueNode(v) :: Nil

  private def anyToTokens(any: Any): List[TokenNode] = any match {
    case v: TokenNode => v :: Nil
    case nodes: List[_] => nodes.flatMap(anyToTokens)
    case v: String => value(v)
    case v: Short => value(v)
    case v: Int => value(v)
    case v: Long => value(v)
    case v: java.math.BigInteger => value(v)
    case v: BigInt => value(v)
    case v: Double => value(v)
    case v: Float => value(v)
    case v: java.math.BigDecimal => value(v)
    case v: BigDecimal => value(v)
    case v: Boolean => value(v)
    case null | None => NullValueNode :: Nil
    case v => throw new Exception(s"Can't auto wrap '$v'")
  }
}
