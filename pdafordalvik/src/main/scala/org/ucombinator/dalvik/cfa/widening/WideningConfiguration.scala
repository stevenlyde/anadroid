package org.ucombinator.dalvik.cfa.widening

trait WideningConfiguration {

  //  type Addr
  //
  type ControlState
  //
  //  type Kont
  //
  //  type Store

  def wideningFreq: Int

  def perPointWidening: Boolean


  def aggresiveCutOff: Boolean

  // def getCurFreq(ct: ControlState) : Int
  // def getCurWidenedStore(ct: ControlState) : Store


  def widening(st: ControlState): ControlState


}
