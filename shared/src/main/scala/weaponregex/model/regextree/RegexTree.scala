package weaponregex.model.regextree

import weaponregex.model.Location

trait RegexTree {
  val children: Seq[RegexTree]

  val prefix: String = ""
  val postfix: String = ""

  val location: Location

  lazy val pattern: String = build

  def build: String

  def buildWith(child: RegexTree, childString: String): String
}
