package com.allatori.util.random;

public abstract class RandomGenerator implements Cloneable {

  public Double cachedGaussian;

  public int nextInt(int value) {
    return this.nextInt(1, value);
  }

  public double nextGaussian() {
    if (this.cachedGaussian != null) {
      double value = this.cachedGaussian;
      this.cachedGaussian = null;
      return value;
    }
    double value2;
    double value3;
    double value4;
    do {
      value2 = this.nextDouble(-1.0, 1.0);
      value4 = this.nextDouble(-1.0, 1.0);
    } while ((value3 = value2 * value2 + value4 * value4) >= 1.0);
    double value5 = Math.sqrt(-2.0 * Math.log(value3) / value3);
    this.cachedGaussian = new Double(value2 * value5);
    return value4 * value5;
  }

  public boolean nextBoolean() {
    return this.nextDouble() <= 0.5;
  }

  public double nextDouble(double value, double value2) {
    return value + (value2 - value) * this.nextDouble();
  }

  public double nextGaussian(double value) {
    return this.nextGaussian() * value;
  }

  public double nextPowerLaw(double value, double value2) {
    return value2 * Math.pow(this.nextDouble(), 1.0 / (value + 1.0));
  }

  public void fill(double[] values) {
    this.fill(values, values.length);
  }

  public int nextInt(int value, int value2) {
    int value3;
    if ((value3 = value + (int) ((value2 - value) * this.nextDouble())) > value2) {
      value3 = value2;
    }
    return value3;
  }

  public void fill(double[] values, int value) {
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      double value4 = this.nextDouble();
      int value5 = value2;
      value2++;
      values[value5] = value4;
    }
  }

  public boolean nextBoolean(double value) {
    return this.nextDouble() <= value;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public abstract double nextDouble();
}
