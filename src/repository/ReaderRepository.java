package repository;

import configuration.DBConnection;
import entity.Reader;

import java.sql.Connection;
import java.sql.ResultSet;

public class ReaderRepository {

        String sqlGetAll = "SELECT * FROM readers";
        String sqlGetById = "SELECT * FROM readers WHERE id = ?";
        String sqlSave = """
            INSERT INTO readers(name, total_books)
            VALUES(?, ?)
            """;

        String sqlUpdate = """
            UPDATE readers
            SET title = ?
            WHERE id = ?
            """;

        String sqlDelete = """
            DELETE FROM readers
            WHERE id = ?
            """;

        public void getAll() {

            try (Connection conn = DBConnection.connect();
                 var ps = conn.prepareStatement(sqlGetAll)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getLong("id") + "---" + rs.getString("name"));
                }

            } catch (Exception e) {

            }
        }

        public Reader getById(long id) {
            try (Connection conn = DBConnection.connect();
                 var ps = conn.prepareStatement(sqlGetById)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println(rs.getLong("id") + "---" + rs.getString("name"));

                    return new Reader(rs.getLong("id"),
                            rs.getString("name"),
                            rs.getLong("total_books"));
                }

            } catch (Exception e) {

            }
            return null;
        }

        public void save(String name, int total_books) {
            try (Connection conn = DBConnection.connect();
                 var ps = conn.prepareStatement(sqlSave)) {

                ps.setString(1, name);
                ps.setLong(2, total_books);
                ps.executeUpdate();

            } catch (Exception e) {

            }
        }

        public void update(String name, Long id) {
            try (Connection conn = DBConnection.connect();
                 var ps = conn.prepareStatement(sqlUpdate)) {

                ps.setString(1, name);
                ps.setLong(2, id);
                ps.executeUpdate();

            } catch (Exception e) {

            }
        }

        public void remove (Long id){
            try (Connection conn = DBConnection.connect();
                 var ps = conn.prepareStatement(sqlDelete)) {


                ps.setLong(1, id);
                ps.executeUpdate();

            } catch (Exception e) {

            }
        }
    }





