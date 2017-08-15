package spring.boot.hive;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HiveDataSource.class)
public class MainTest {

    @Autowired
    @Qualifier("hiveJdbcTemplate")
    JdbcTemplate hiveJdbcTemplate;

    @Test
    public void DataSourceTest() {

        StringBuffer sql = new StringBuffer("select * from book limit 5");

        // create table
//        StringBuffer sql = new StringBuffer("create table IF NOT EXISTS ");
//        sql.append("HIVE_TEST1 ");
//        sql.append("(KEY INT, VALUE STRING) ");
//        sql.append("PARTITIONED BY (S_TIME DATE)"); // 分区存储
//        sql.append("ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' "); // 定义分隔符
//        sql.append("STORED AS TEXTFILE"); // 作为文本存储

        // drop table
//        StringBuffer sql = new StringBuffer("DROP TABLE IF EXISTS ");
//        sql.append("HIVE_TEST1");


//        hiveJdbcTemplate.execute(sql.toString());
        hiveJdbcTemplate.execute(sql.toString(), new ArticleListPreparedStatement());
    }

    class ArticleListPreparedStatement implements PreparedStatementCallback<List<Book>> {
        public List<Book> doInPreparedStatement(PreparedStatement ps)
                throws SQLException, DataAccessException {
            return QueryUtils.extractArticleListFromRs(ps.executeQuery());
        }
    }

    static class QueryUtils {
        public static Book extractArticleFromRs(ResultSet rs) throws SQLException {
            Book article = new Book();
            article.setId(rs.getInt("id"));
            article.setName(rs.getString("name"));
            article.setAge(rs.getInt("age"));
            article.setPubdate(rs.getString("pubdate"));
            System.out.println(article.toString());
            return article;
        }

        public static List<Book> extractArticleListFromRs(ResultSet rs) throws SQLException {
            List<Book> articleList = new ArrayList<Book>();
            while (rs.next()) {
                articleList.add(extractArticleFromRs(rs));
            }
            return articleList;
        }
    }

    static class Book {
        private int id;
        private String name;
        private int age;
        private String pubdate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", pubdate='" + pubdate + '\'' +
                    '}';
        }
    }

}
