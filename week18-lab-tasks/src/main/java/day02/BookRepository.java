package day02;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

public class BookRepository {

    private JdbcTemplate jdbcTemplate;

    public BookRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void insertBook(String writer, String title, int price, int pieces) {
        //language=sql
        jdbcTemplate.update
                ("insert into books(writer,title,price,pieces) values (?,?,?,?)", writer, title, price, pieces);
    }

    public List<Book> findBooksByWriter(String prefix) {
        //language=sql
        return jdbcTemplate.query("select * from books where writer like ?",
                (rs, rowNum) -> new Book(rs.getLong("id"), rs.getString("writer"),
                        rs.getString("title"), rs.getInt("price"),
                        rs.getInt("pieces"))
                , prefix + "%");
    }

    public void updatePieces(Long id, int pieces) {
        //language=sql
        jdbcTemplate.update("update books set pieces = pieces+? where id = ?", pieces, id);
    }

}
