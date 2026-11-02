package com.allatori.lib.bcel;

public interface ExceptionConstants {

  Class TYPE10 = Throwable.class;

  Class TYPE20 = RuntimeException.class;

  Class TYPE13 = LinkageError.class;

  Class TYPE16 = ClassCircularityError.class;

  Class TYPE17 = ClassFormatError.class;

  Class TYPE4 = ExceptionInInitializerError.class;

  Class TYPE9 = IncompatibleClassChangeError.class;

  Class TYPE14 = AbstractMethodError.class;

  Class TYPE7 = IllegalAccessError.class;

  Class TYPE2 = InstantiationError.class;

  Class TYPE8 = NoSuchFieldError.class;

  Class TYPE5 = NoSuchMethodError.class;

  Class TYPE21 = NoClassDefFoundError.class;

  Class TYPE6 = UnsatisfiedLinkError.class;

  Class TYPE12 = VerifyError.class;

  Class TYPE3 = NullPointerException.class;

  Class TYPE15 = ArrayIndexOutOfBoundsException.class;

  Class TYPE18 = ArithmeticException.class;

  Class TYPE11 = NegativeArraySizeException.class;

  Class TYPE = ClassCastException.class;

  Class TYPE19 = IllegalMonitorStateException.class;

  Class[] TYPES5 = new Class[] {TYPE21, TYPE17, TYPE12, TYPE14, TYPE4, TYPE7};

  Class[] TYPES2 = new Class[] {ExceptionConstants.TYPE8, ExceptionConstants.TYPE7, TYPE5};

  Class[] TYPES4 = new Class[0];

  Class[] TYPES = new Class[0];

  Class[] TYPES3 = new Class[] {TYPE3, ExceptionConstants.TYPE15};
}
