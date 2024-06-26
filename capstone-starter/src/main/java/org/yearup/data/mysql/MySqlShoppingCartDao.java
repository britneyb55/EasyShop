package org.yearup.data.mysql;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao
{
    private ProductDao productDao;


    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao)
    {
        super(dataSource);
        this.productDao = productDao;

    }


    @Override
    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart shoppingCart = new ShoppingCart();
        try(Connection connection = getConnection())
        {
            String sql = """
                        SELECT user_id
                               , product_id
                               , quantity
                               FROM shopping_cart WHERE user_id = ?;
                        """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);

            ResultSet row = statement.executeQuery();
            while(row.next())
            {
                int productId = row.getInt("product_id");
                int quantity = row.getInt("quantity");

                Product product = productDao.getById(productId);

                ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                {{
                    shoppingCartItem.setQuantity(quantity);
                    shoppingCartItem.setProduct(product);
                }};
                shoppingCart.add(shoppingCartItem);


            }

        }
        catch(SQLException e)
        {
            System.out.println(e.getLocalizedMessage());
        }
        return  shoppingCart;
    }

    @Override
    public ShoppingCart addProductToCart(int productId, int userId)
    {
        try(Connection connection = getConnection())
        {
            String sql = """
                    INSERT INTO shopping_cart (user_id, product_id, quantity)
                    VALUES(?,?,1);
                    
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setInt(2,productId);

            statement.executeUpdate();

        }catch(SQLException e)
        {
            System.out.println(e.getLocalizedMessage());
        }
        return getByUserId(userId);
    }

    @Override
    public void updateProductQuantityInsideCart(int productId, int usedId, int quantity)
    {
        try (Connection connection = getConnection())
        {
            String sql = """
                    UPDATE shopping_cart
                    SET quantity = ?
                    WHERE product_id = ? AND user_id = ? ;
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,quantity);
            statement.setInt(2,productId);
            statement.setInt(3,usedId);

            statement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteCartProducts(int userid)
    {

        try (Connection connection = getConnection())
        {
            String sql = "DELETE FROM shopping_cart WHERE user_id = ? ";

           PreparedStatement statement= connection.prepareStatement(sql);
           statement.setInt(1,userid);
           statement.executeUpdate();

        } catch (SQLException e)
        {

        }
    }


}
