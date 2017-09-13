package tethys.derivation.impl

import scala.reflect.macros.blackbox

/**
  * Created by eld0727 on 22.04.17.
  */
trait MacroUtils extends BaseMacroDefinitions
    with CaseClassUtils
    with LoggingUtils {
  val c: blackbox.Context
  import c.universe._

  case class SelectChain(chain: Seq[String])

  implicit lazy val selectChainUnliftable: Unliftable[SelectChain] = Unliftable[SelectChain] {
    case Ident(name) => SelectChain(Seq(name.decodedName.toString))
    case select: Select =>
      def selectAllNames(s: Tree): Seq[String] = s match {
        case Select(rest, name) => selectAllNames(rest) :+ name.decodedName.toString
        case Ident(name) => Seq(name.decodedName.toString)
      }

      SelectChain(selectAllNames(select))
  }


  case class BuilderField(name: String, tpe: Type)

  implicit lazy val builderFieldUnliftable: Unliftable[BuilderField] = Unliftable[BuilderField] {
    case q"((${ValDef(_, name, t, _)}) => ${b: SelectChain})"
      if b.chain.size == 2 && name.decodedName.toString == b.chain.head =>
      BuilderField(b.chain(1), t.tpe)
  }

  object Untyped {
    def unapply(arg: Tree): Option[Tree] = arg match {
      case Typed(t, _) => Untyped.unapply(t)
      case _ => Some(arg)
    }
  }
}
