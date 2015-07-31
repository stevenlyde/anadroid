package org.ucombinator.playhelpers

import scala.sys.process._

class ExtractIRHelperThread(cmd: String) extends Thread {
  override def run() {
    cmd !
  }
}
