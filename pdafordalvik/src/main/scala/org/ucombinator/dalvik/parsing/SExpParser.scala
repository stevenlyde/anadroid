package org.ucombinator.dalvik.parsing

import org.ucombinator.dalvik.syntax._

import scala.util.matching.Regex
import scala.util.parsing.combinator._


class SExpParser extends RegexParsers {

  /**

  BUG! try: "\n(a)\n"

    */

  override def skipWhitespace = true

  override protected val whiteSpace = new Regex("([\\r\\n\\t ]*([;][^\\r\\n]*[\\r\\n]?)?)+")

  private def lpar: Parser[String] =
    regex(new Regex("[(]")) ^^ { case "(" => "(" }

  private def rpar: Parser[String] =
    regex(new Regex("[)]")) ^^ { case ")" => ")" }

  // for invoke/ranges and arguments
  private def lrkt: Parser[String] =
    regex(new Regex("[{]")) ^^ {
      case "{" => "("
    }

  private def rrkt: Parser[String] =
    regex(new Regex("[}]")) ^^ {
      case "}" => ")"
    }


  private def integer: Parser[SExp] =
    regex(new Regex("-?[0-9]+")) ^^ {
      case s => SInt(BigInt(s))
    }

  private def strue: Parser[SExp] =
    "#t" ^^ {
      case "#t" => SBoolean(true)
    }

  // false
  private def sfalse: Parser[SExp] =
    "#f" ^^ {
      case "#f" => SBoolean(false)
    }

  // char
  private def schar: Parser[SExp] =
    ("#\\" ~ regex(new Regex("[^\\r\\n\\t ]"))) ^^ {
      case "#\\" ~ c => SChar(c.charAt(0))
    }

  private def symbol: Parser[SExp] =
  // regex(new Regex("([^.#; \\t\\r\n()',`\"][^; \\t\\r\\n()',`\"]*|[.][^; \\t\\r\\n()',`\"]+)")) ^^ {
  // regex(new Regex("([^.#; \\t\\r\n(){}\\[\\]',`\"][^; \\t\\r\\n(){}\\[\\]',`\"]*|[.][^; \\t\\r\\n(){}\\[\\]',`\"]+)")) ^^ {
    regex(new Regex("([^.; \\t\\r\n(){}\\[\\]',`\"][^; \\t\\r\\n(){}\\[\\]',`\"]*|[.][^; \\t\\r\\n(){}\\[\\]',`\"]+)")) ^^ {
      case s => SName.from(s)
    }


  private def formal: Parser[SExp] =
    "{" ~ sexplist ~ "}" ^^ { case "{" ~ sxl ~ "}" => sxl }

  private def emptyformal: Parser[SExp] =
    ("{" ~ "}") ^^ { case "{" ~ "}" => SNil() }

  private def objorarrtype: Parser[SExp] =
    "[" ~ sexplist ~ "]" ^^ { case "[" ~ sxl ~ "]" => sxl }

  private def keyword: Parser[SExp] =
    regex(new Regex("([#][:][^; \\t\\r\\n()',`\"]+)")) ^^ {
      case s => SKeyword.from(s.substring(2))
    }

  // string?
  private def text: Parser[SExp] =
    "\"\"" ^^ {
      case _ => SText("")
    } |
      regex(new Regex("\"([^\"\\\\]|\\\\.|\\\\\\\\|)*\"")) ^^ {
        case s => SText(s.substring(1, s.length() - 1))
      }

  //empty list
  private def nil: Parser[SExp] =
    (lpar ~ rpar) ^^ {
      case "(" ~ ")" => SNil()
    }

  private def sboolean: Parser[SExp] = strue | sfalse

  //sxlist
  private def sxlist: Parser[SExp] =
    (lpar ~ sexplist ~ rpar) ^^ {
      case "(" ~ l ~ ")" => l
    }

  //sxlist{}
  private def sxlistrkt: Parser[SExp] =
    (lrkt ~ sexplist ~ rrkt) ^^ {
      case "{" ~ l ~ "}" => l
    }

  // no need
  private def special: Parser[SExp] =
    (",@" ~ sexp) ^^ {
      case ",@" ~ sexp => SExp(List(CommonSSymbols.SUnquoteSplicing, sexp))
    } |
      ("'" ~ sexp) ^^ {
        case "'" ~ sexp => SExp(List(CommonSSymbols.SQuote, sexp))
      } |
      ("`" ~ sexp) ^^ {
        case "`" ~ sexp => SExp(List(CommonSSymbols.SQuasiquote, sexp))
      } |
      ("," ~ sexp) ^^ {
        case "," ~ sexp => SExp(List(CommonSSymbols.SUnquote, sexp))
      }


  private def sexp: Parser[SExp] =
    positioned(nil | sxlist | emptyformal | formal | objorarrtype
      | integer | sboolean | keyword | schar | text | symbol | special)

  private def sexplist: Parser[SExp] =
    rep(sexp) ~ (("." ~ sexp) ?) ^^ {
      case sexps ~ Some("." ~ sexp) => SExp(sexps, sexp)
      case sexps ~ None => SExp(sexps)
    }

  def parse(input: String): SExp = parse(sexp, input).get

  def parseAll(input: String): List[SExp] = {
    val result = parse(phrase(rep(sexp)), input)

    if (result.successful)
      result.get
    else {
      throw new Exception("Parsing failed at position " + result.next.pos + ";\n character: '" + result.next.first + "';\n at end: " + result.next.atEnd)
    }
  }

}
