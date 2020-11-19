package weaponregex.mutator

import weaponregex.Parser
import weaponregex.TreeMutator._
import weaponregex.model.mutation._

class MutatorTest extends munit.FunSuite {
  test("Remove BOL") {
    val pattern = "^abc^def^"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[Mutant] = parsedTree.mutate(Seq(RemoveBOL))
    assertEquals(mutants.length, 3)

    val expected: Seq[String] = Seq("abc^def^", "^abcdef^", "^abc^def").sorted
    assertEquals(mutants.map(_.pattern).sorted, expected)
  }

  test("Remove EOL") {
    val pattern = "$abc$def$"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[Mutant] = parsedTree.mutate(Seq(RemoveEOL))
    assertEquals(mutants.length, 3)

    val expected: Seq[String] = Seq("abc$def$", "$abcdef$", "$abc$def").sorted
    assertEquals(mutants.map(_.pattern).sorted, expected)
  }
}
