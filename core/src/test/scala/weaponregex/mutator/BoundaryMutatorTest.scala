package weaponregex.mutator

import weaponregex.parser.Parser
import TreeMutator._

class BoundaryMutatorTest extends munit.FunSuite {
  test("Removes BOL") {
    val pattern = "^abc^def^"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[String] = parsedTree.mutate(Seq(BOLRemoval)) map (_.pattern)

    val expected: Seq[String] = Seq(
      "abc^def^",
      "^abcdef^",
      "^abc^def"
    )

    assertEquals(clue(mutants).length, expected.length)
    mutants foreach (m => assert(clue(expected) contains clue(m)))
  }

  test("Does not remove escaped BOL") {
    val pattern = "\\^abc"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[String] = parsedTree.mutate(Seq(BOLRemoval)) map (_.pattern)
    assertEquals(clue(mutants), Nil)
  }

  test("Removes EOL") {
    val pattern = "$abc$def$"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[String] = parsedTree.mutate(Seq(EOLRemoval)) map (_.pattern)

    val expected: Seq[String] = Seq(
      "abc$def$",
      "$abcdef$",
      "$abc$def"
    )

    assertEquals(clue(mutants).length, expected.length)
    mutants foreach (m => assert(clue(expected) contains clue(m)))
  }

  test("Does not remove escaped EOL") {
    val pattern = "abc\\$"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[String] = parsedTree.mutate(Seq(EOLRemoval)) map (_.pattern)
    assertEquals(clue(mutants), Nil)
  }

  test("Changes BOL to BOI") {
    val pattern = "^abc^def^ghi\\^"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[String] = parsedTree.mutate(Seq(BOL2BOI)) map (_.pattern)

    val expected: Seq[String] = Seq(
      """\Aabc^def^ghi\^""",
      """^abc\Adef^ghi\^""",
      """^abc^def\Aghi\^"""
    )

    assertEquals(clue(mutants).length, expected.length)
    mutants foreach (m => assert(clue(expected) contains clue(m)))
  }

  test("Does not change escaped BOL") {
    val pattern = "\\^abc"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[String] = parsedTree.mutate(Seq(BOL2BOI)) map (_.pattern)
    assertEquals(clue(mutants), Nil)
  }

  test("Changes EOL to EOI") {
    val pattern = "$abc$def$ghi\\$"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[String] = parsedTree.mutate(Seq(EOL2EOI)) map (_.pattern)

    val expected: Seq[String] = Seq(
      """\zabc$def$ghi\$""",
      """$abc\zdef$ghi\$""",
      """$abc$def\zghi\$"""
    )

    assertEquals(clue(mutants).length, expected.length)
    mutants foreach (m => assert(clue(expected) contains clue(m)))
  }

  test("Does not change escaped EOL") {
    val pattern = "abc\\$"
    val parsedTree = Parser.parseOrError(pattern)

    val mutants: Seq[String] = parsedTree.mutate(Seq(EOL2EOI)) map (_.pattern)
    assertEquals(clue(mutants), Nil)
  }
}
