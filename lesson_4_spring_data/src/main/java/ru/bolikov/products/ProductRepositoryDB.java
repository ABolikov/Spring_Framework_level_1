package ru.bolikov.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryDB {

    private final Connection conn;

    @Autowired
    public ProductRepositoryDB(DataSource dataSource) throws SQLException {
        this.conn = dataSource.getConnection();
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into product(title, cost) values (?, ?);")) {
            stmt.setString(1, product.getTitle());
            stmt.setInt(2, product.getCost());
            stmt.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "update product set title = ?, cost = ? where id_product = ?;")) {
            stmt.setString(1, product.getTitle());
            stmt.setInt(2, product.getCost());
            stmt.setInt(3, product.getId());
            stmt.execute();
        }
    }

    public void delete(Integer id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from product where id_product = ?;")) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public Product findByProduct(Integer id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from product where id_product = ?;")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getInt(3));
            }
        }
        return new Product(-1, "", 0);
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from product");
            while (rs.next()) {
                res.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        }
        return res;
    }
}
