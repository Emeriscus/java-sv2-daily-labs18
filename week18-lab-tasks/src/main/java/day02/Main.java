package day02;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        MariaDbDataSource dataSource = new MariaDbDataSource();

        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/bookstore?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot reach database", se);
        }

        Flyway flyway = Flyway.configure().locations("db/migration/bookstore").dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        BookRepository bookRepository = new BookRepository(dataSource);

        bookRepository.insertBook("Fekete István", "Vuk", 3400, 10);
        bookRepository.insertBook("Fekete István", "Téli berek", 3600, 8);
        bookRepository.insertBook("Fekete Péter", "Kártyatrükkök", 1200, 2);

        bookRepository.updatePieces(1L, 30);

        System.out.println(bookRepository.findBooksByWriter("Fek"));


    }
}
