public class ByteConverter {


  public static Byte[] convertFrom(byte[] primitiveByteArray) {
    Byte[] result = new Byte[primitiveByteArray.length];

    for (int i = 0; i < primitiveByteArray.length; i++) {
      result[i] = primitiveByteArray[i];
    }

    return result;
  }

  public static byte[] convertFrom(Byte[] objectArray) {
    byte[] result =  new byte[objectArray.length];

    for (int i = 0; i < objectArray.length; i++) {
      result[i] = objectArray[i];
    }

    return result;
  }

}
