package ai.balto.iamdb;

import static ai.balto.iamdb.models.MovieData.findAll;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import ai.balto.iamdb.models.Movie;
import ai.balto.iamdb.models.MovieData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class IamdbApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test_JUnit() {
		System.out.println("This is the testcase in this class");
		String str1="This is the testcase in this class";
		assertTrue(str1.contains("This is the testcase in this class"));
	}

	@Test
	public void testIndexPage() {
		ArrayList<Movie> movies = MovieData.findAll();
		Movie testMovie = movies.get(0);
		assertTrue(testMovie != null);
	}
}
