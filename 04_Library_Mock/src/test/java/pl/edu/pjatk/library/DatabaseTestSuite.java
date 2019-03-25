package pl.edu.pjatk.library;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pl.edu.pjatk.library.dao.BookDaoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(BookDaoTest.class)
public class DatabaseTestSuite {
}