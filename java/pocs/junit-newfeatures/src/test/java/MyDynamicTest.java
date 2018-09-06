import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class MyDynamicTest {
	private static List<String> urls;

	@BeforeAll
	public static void setup()  {
		urls  = Arrays.asList("www.google.com", "www.github.com");
	}

	@TestFactory
	Stream<DynamicTest> myTest() {
		return urls.stream().map(url ->
			dynamicTest("Testing against " + url, () -> {
				System.out.println("Testing using url: " + url);
				assertTrue(url != null);

			})
		);

	}

}
