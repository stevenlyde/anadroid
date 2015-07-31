package org.ucombinator.tests

import java.io.File

import scala.io.Source
import scala.util.matching.Regex

object TestString {


  def matchSS(input: String, pattern: Regex): Boolean = {
    //println(pattern)
    val resList = (pattern findAllIn input).toList
    //println(resList)
    if (resList.isEmpty) false
    else true
  }

  def test() {
    val str = "http://google.com"
    val p = "(http|ftp)://(.*)\\.([a-z]+)"
    println(p.r findAllIn str)
  }


  def main(args: Array[String]): Unit = {

    val strFilePath = "data" + File.separator + "str-pat.txt"

    val classLines = Source.fromFile(strFilePath).getLines().filter(_.trim() != "")
    val deduplicateClsLines = classLines.toSet.toList
    deduplicateClsLines.foreach(println)
    val map =
      deduplicateClsLines.foldLeft(Map[Regex, String]())((res, line) => {
        val splitted: List[String] = line.split("\\s+").toList
        val pattern = new Regex(splitted(0))
        println(matchSS("http://www.foobar.com/foo.gzip", pattern))
        val cate = splitted(1)
        res + (pattern -> cate)
      })

    val regexes = map.map(_._1).toList


  }
}
