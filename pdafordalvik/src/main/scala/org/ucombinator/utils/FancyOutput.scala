package org.ucombinator.utils

trait FancyOutput {

  val graphsDirName: String = "graphs"

  val statisticsDirName: String = "statistics"

  type ControlState

  def isVerbose: Boolean

  def progressPrefix: String

  def shouldGC: Boolean

  def doLRA: Boolean

  def simplify: Boolean

  def printGCDebug: Boolean

  def interrupt: Boolean

  def dumpDSG: Boolean

  def interruptAfter: Int

  def timeInterrupt: Boolean

  def interruptAfterTime: Long

  def prettyPrintState(state: ControlState, map: Map[ControlState, Int]): String

  def prettyPrintState2(state: ControlState, map: Map[ControlState, Int]): String

  def genPrettyStateToHtml(st: ControlState, map: Map[ControlState, Int]): String

  def writeStateToHtmlfile(state: ControlState, map: Map[ControlState, Int], graphParentFolder: String)

  /* */
  /**
   * Get a fancy name dump files
   * This will be modified to generate svg graph
   */
  /*
    def getGraphDumpFileName(opts: AIOptions): String = {
      val cfa = opts.analysisType match {
        case AnalysisType.KCFA => "-cfa"
        case AnalysisType.PDCFA => "-pdcfa"
      }
      val prefix = "graph-"
      val arity = if (opts.dummy) "dummy" else opts.k.toString
      val gc = if (opts.gc) "-gc" else ""
      val lrv = if(opts.doLRA) "-lra" else ""
      prefix + arity + cfa + gc + lrv + ".dot"
    }



    def getStatisticsDumpFileName(opts: AIOptions): String = {
      val cfa = opts.analysisType match {
        case AnalysisType.KCFA => "-cfa"
        case AnalysisType.PDCFA => "-pdcfa"
      }
      val prefix = "stat-"
      val arity = if (opts.dummy) "dummy" else opts.k.toString
      val gc = if (opts.gc) "-gc" else ""
      val lrv = if(opts.doLRA) "-lra" else ""
      prefix + arity + cfa + gc + lrv + ".txt"
    }

  */

}
