import com.lambdaworks.redis.RedisAsyncConnection;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.codec.ByteArrayCodec;
import com.lambdaworks.redis.codec.RedisCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.lambdaworks.redis.ScriptOutputType.VALUE;

public class Main {

  public static String decodeStr(ByteBuffer bytes) {
    return StandardCharsets.UTF_8.decode(bytes).toString();
  }

  public static byte[] encodeStr(String key) {
    return key.getBytes(StandardCharsets.UTF_8);
  }

  public static Byte[] encodeLong(long l) {
    byte[] bytes =  ByteBuffer.allocate(Long.BYTES).putLong(l).array();
    Byte[] byteObjects = new Byte[bytes.length];
    int i = 0;
    for(byte b: bytes)
      byteObjects[i++] = b;  // Autoboxing.

    return byteObjects;
  }

  public static long decodeLong(byte[] data) {
    return ByteBuffer.wrap(data).getLong();
  }

  private static byte[] from(List<Byte[]> values) throws IOException {
    byte[] result = new byte[values.size() * Long.BYTES];
    int j=0;
    for(int i = 0; i < values.size(); i++) {
      Byte[] byteObjects = values.get(i);

      for(Byte b: byteObjects)
        result[j++] = b.byteValue();

    }

    return result;
  }


  private static List<Long> from(byte[] serverReturnedValues) {
    List<Long> result  = new ArrayList<>();
    Byte[] value = new Byte[Long.BYTES];
    int bitControl = 0;
    for (int i = 0; i < serverReturnedValues.length; i++) {
      value[bitControl] = serverReturnedValues[i];
      bitControl++;
      if (bitControl == Long.BYTES) {
        result.add(decodeLong(ByteConverter.convertFrom(value)));
        value = new Byte[Long.BYTES];
        bitControl = 0;
      }
    }

    return result;
  }

  public static void main(String[] args) throws Throwable {

    RedisCodec<byte[], byte[]> codec = new ByteArrayCodec();

    List<Byte[]> values = new ArrayList<>();
    values.add(encodeLong(33L));
    values.add(encodeLong(2L));

    RedisClient client = RedisClient.create("redis://localhost:6379");
    RedisAsyncConnection<byte[], byte[]> connection = client.connectAsync(codec);
    String hash = loadLuaScript(connection);

    RedisFuture<byte[]> result = connection.evalsha(hash, VALUE, encodeStr("myhash1"), encodeStr("age"), encodeStr("8"));
    System.out.println("hstrrange result: " + from(result.get()));

    RedisFuture<Boolean> changedResult = connection.hset(encodeStr("myhash1"), encodeStr("age"), from(values));
    System.out.println("Age rollback: " + changedResult.get());

  }

  private static String loadLuaScript(RedisAsyncConnection<byte[], byte[]> connection) throws Exception {
    Path script  = Paths.get("hello.lua");
    RedisFuture<String> result = connection.scriptLoad(Files.readAllBytes(script));
    String hash = result.get();
    System.out.println("Hash: " +  hash);
    return hash;
  }

}
