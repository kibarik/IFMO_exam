package app.kibarik.learn.manager;

import app.kibarik.learn.App;
import app.kibarik.learn.entity.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductEntityManager {
    public static List<ProductEntity> selectAll() throws SQLException{
        try (Connection c = App.getConnection()){
            String sql = "SELECT * FROM product";
            Statement st = c.createStatement();
            st.executeQuery(sql);

            ResultSet rs = st.getResultSet();
            List<ProductEntity> list = new ArrayList<>();
            while(rs.next()){
                rs.getInt(1);
                list.add(new ProductEntity(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getString("ProductType"),
                        rs.getString("ArticleNumber"),
                        rs.getString("Description"),
                        rs.getString("Image"),
                        rs.getInt("ProductionPersonCount"),
                        rs.getInt("ProductionWorkshopNumber"),
                        rs.getDouble("MinCostForAgent")
                ));
            }

            return list;
        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public static void insert(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()){
            String sql = "INSERT INTO product(Title, ProductType, ArticleNumber, Description, Image, ProductionPersonCount, ProductionWorkshopNumber, MinCostForAgent) VALUES(?,?,?,?,?,?,?,?) ";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getTitle() );
            ps.setString(2, product.getProductType() );
            ps.setString(3, product.getArticleNumber() );
            ps.setString(4, product.getDescription() );
            ps.setString(5, product.getImage() );
            ps.setInt(6, product.getProductionPersonCount() );
            ps.setInt(7, product.getProductionWorkshopNumber() );
            ps.setDouble(8, product.getMinCostForAgent() );
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                product.setId(rs.getInt(1));
            }
        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }


    public static void update(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()){
            String sql = "UPDATE product(Title=?, ProductType=?, ArticleNumber=?, Description=?, Image=?, ProductionPersonCount=?, ProductionWorkshopNumber=?, MinCostForAgent=?) WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, product.getTitle() );
            ps.setString(2, product.getProductType() );
            ps.setString(3, product.getArticleNumber() );
            ps.setString(4, product.getDescription() );
            ps.setString(5, product.getImage() );
            ps.setInt(6, product.getProductionPersonCount() );
            ps.setInt(7, product.getProductionWorkshopNumber() );
            ps.setDouble(8, product.getMinCostForAgent() );
            ps.setInt(9, product.getId() );
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }


    public static void delete(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()){
            String sql = "DELETE FROM product WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, product.getId() );
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
}
