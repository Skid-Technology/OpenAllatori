package com.allatori.util.random;

import java.io.Serializable;
import java.util.Date;

public class RanmarRandom extends SeededRandomGenerator implements Serializable {

  public double carry;

  public double[] savedState;

  public int j97;

  public double[] state;

  public double carryDelta;

  public int i97;

  public static int DEFAULT_SEED = 54217137;

  public static int SEED_MODULUS = 899999963;

  public double carryModulus;

  public RanmarRandom() {
    this.initializeState(DEFAULT_SEED);
  }

  public void initializeState(int value) {
    this.state = new double[97];
    this.savedState = new double[97];
    int value2 = value / 30082;
    int value3 = value - 30082 * value2;
    int value4 = value2 / 177 % 177 + 2;
    int value5 = value2 % 177 + 2;
    int value6 = value3 / 169 % 178 + 1;
    int value7 = value3 % 169;
    int value8;
    for (int value9 = value8 = 0; value9 < 97; value9 = value8) {
      double value10 = 0.0;
      double value11 = 0.5;
      int value12;
      for (int value13 = value12 = 0; value13 < 24; value13 = value12) {
        int value14 = value4 * value5 % 179 * value6 % 179;
        value4 = value5;
        value5 = value6;
        value6 = value14;
        if ((value7 = (53 * value7 + 1) % 169) * value14 % 64 >= 32) {
          value10 += value11;
        }
        double value15 = value11;
        value12++;
        value11 = value15 * 0.5;
      }
      double value16 = value10;
      int value17 = value8;
      double[] values = this.state;
      value8++;
      values[value17] = value16;
    }
    this.carry = 0.021602869F;
    this.carryDelta = 0.45623308F;
    this.carryModulus = 0.9999998F;
    this.i97 = 96;
    this.j97 = 32;
  }

  @Override
  public final double nextDouble() {
    double value;
    if ((value = this.state[this.i97] - this.state[this.j97]) < 0.0) {
      value++;
    }
    this.state[this.i97] = value;
    if (--this.i97 < 0) {
      this.i97 = 96;
    }
    if (--this.j97 < 0) {
      this.j97 = 96;
    }
    this.carry = this.carry - this.carryDelta;
    if (this.carry < 0.0) {
      this.carry = this.carry + this.carryModulus;
    }
    double value2;
    if ((value2 = value - this.carry) < 0.0) {
      value2++;
    }
    return value2;
  }

  public RanmarRandom(int value) {
    super();
    this.initializeState(Math.abs(value % SEED_MODULUS));
  }

  @Override
  public final void fill(double[] values, int value) {
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      double value4;
      if ((value4 = this.state[this.i97] - this.state[this.j97]) < 0.0) {
        value4++;
      }
      this.state[this.i97] = value4;
      if (--this.i97 < 0) {
        this.i97 = 96;
      }
      if (--this.j97 < 0) {
        this.j97 = 96;
      }
      this.carry = this.carry - this.carryDelta;
      if (this.carry < 0.0) {
        this.carry = this.carry + this.carryModulus;
      }
      double value5;
      if ((value5 = value4 - this.carry) < 0.0) {
        value5++;
      }
      double value6 = value5;
      int value7 = value2;
      value2++;
      values[value7] = value6;
    }
  }

  public RanmarRandom(long value) {
    this.initializeState((int) Math.abs(value % SEED_MODULUS));
  }

  public RanmarRandom(Date date) {
    super();
    this.initializeState((int) calculateSeed(date) % SEED_MODULUS);
  }
}
