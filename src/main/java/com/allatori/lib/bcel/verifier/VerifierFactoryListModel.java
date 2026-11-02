package com.allatori.lib.bcel.verifier;

import java.util.ArrayList;
import java.util.TreeSet;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class VerifierFactoryListModel implements VerifierFactoryObserver, ListModel {

  public TreeSet cache;

  public ArrayList listeners = new ArrayList();

  @Override
  public synchronized void addListDataListener(ListDataListener listDataListener) {
    this.listeners.add(listDataListener);
  }

  public VerifierFactoryListModel() {
    this.cache = new TreeSet();
    VerifierFactory.attach(this);
    this.update(null);
  }

  @Override
  public synchronized Object getElementAt(int value) {
    return this.cache.toArray()[value];
  }

  @Override
  public synchronized void removeListDataListener(ListDataListener listDataListener) {
    this.listeners.remove(listDataListener);
  }

  @Override
  public synchronized void update(String text) {
    int size2 = this.listeners.size();
    Verifier[] verifiers;
    int value = (verifiers = VerifierFactory.getVerifiers()).length;
    int value2 = 0;
    this.cache.clear();
    for (int index = 0; value2 < value; value2 = index) {
      int value3 = index;
      Verifier[] verifiers2 = verifiers;
      TreeSet treeSet = this.cache;
      index++;
      treeSet.add(verifiers2[value3].getClassName());
    }
    int value4;
    for (int value5 = value4 = 0; value5 < size2; value5 = value4) {
      ListDataEvent listDataEvent = new ListDataEvent(this, 0, 0, value - 1);
      ListDataListener listDataListener = (ListDataListener) this.listeners.get(value4);
      value4++;
      listDataListener.contentsChanged(listDataEvent);
    }
  }

  @Override
  public synchronized int getSize() {
    return this.cache.size();
  }
}
