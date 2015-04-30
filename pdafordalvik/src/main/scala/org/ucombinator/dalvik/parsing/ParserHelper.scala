package org.ucombinator.dalvik.parsing
import org.ucombinator.utils.AIOptions
import org.ucombinator.dalvik.syntax.SExp
import java.io.File
import scala.util.matching.Regex
import org.ucombinator.utils.CommonUtils

trait ParserHelper {

    private def simplefunc(pf: File, cnt: Int, opts:AIOptions) {
      val fp = pf.getPath // path.getAbsolutePath();
      val sexp = SExp.parseAllIn(fp)
      S2DParser(sexp);
  }  
    
   def parseDalvikSExprs(opts: AIOptions) {
    println("parseDalvikSExprs()");
    val dirName = opts.sexprDir

    val sexDir = new File(dirName)
    val allFileList = CommonUtils.deepFiles(sexDir)
     
    val sexpFiles = allFileList.filter((f) => {
      f.getName().endsWith(".sxddx")
    })

    var cnt = 0
    sexpFiles.foreach((sf) => {
      cnt += 1
      
      if (opts.verbose) {
        System.out.println(cnt + " Parsing file " + sf)
      }
      simplefunc(sf, cnt, opts)
    })

    if (opts.verbose) {
      System.out.println(" Done parsing all the s-exp class files!")
    }
  }
}