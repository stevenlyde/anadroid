package org.ucombinator.dalvik.cfa.widening

import org.ucombinator.dalvik.cfa.cesk.StateSpace

trait DalvikWideningConfiguration extends StateSpace with WideningConfiguration {

  def widening(st: ControlState): ControlState = {
    // widening starts
    if (st.getCurFreq > wideningFreq) {

      val (curWStore, curWPs) = st.getCurWidenedStore
      val widenedStore = curWStore.join(st.getCurStore) //mergeTwoStores(curWStore, st.getCurStore)
      val widenedPS = curWPs.join(st.getCurPropertyStore) //mergeTwoStores(curWPs, st.getCurPropertyStore)
      wideningState(st, widenedStore, widenedPS)
    } else
      st
  }

}
