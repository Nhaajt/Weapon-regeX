package weaponregex.model.mutation

import weaponregex.model.Location

import scala.scalajs.js.annotation._

/** Data structure for metadata of a mutation made by a mutator.
  * @param name Name of the mutation
  * @param location [[weaponregex.model.Location]] in the original string where the mutation occurred
  * @param levels The mutation levels of the mutator
  * @param description Description on the mutation
  */
@JSExportTopLevel("MutationData")
@JSExportAll
case class MutationData(name: String, location: Location, levels: Seq[Int], description: String)
