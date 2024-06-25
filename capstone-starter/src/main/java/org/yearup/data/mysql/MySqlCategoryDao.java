package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{

    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }


    @Override
    public List<Category> getAllCategories()
    {
        List<Category> categories = new ArrayList<>();
        try(Connection connection = getConnection())
        {
            String sql = """
                    SELECT category_id
                    	   , name
                           , description
                    FROM categories;
                    """;
            Statement statement = connection.createStatement();
            ResultSet row = statement.executeQuery(sql);

            while(row.next())
            {
                int categoryId = row.getInt("category_id");
                String name = row.getString("name");
                String description = row.getString("description");

                Category category = new Category(categoryId, name, description);

                categories.add(category);

            }

        }catch (SQLException e)
        {

        }
        // get all categories
        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        try(Connection connection = getConnection())
        {
            String sql =
                    """        
                    SELECT category_id
                    , name
                    , description
            FROM categories
            WHERE category_id = ?
            """;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);

            ResultSet row = statement.executeQuery();

            if (row.next());
            {
                return mapRow(row);

            }


        }catch(Exception e)
        {

        }
        // get category by id
        return null;
    }

    @Override
    public Category create(Category category)
    {
        int newId = 0;
        try(Connection connection = getConnection())
        {
            String sql = """
                    INSERT INTO categories (name, description)
                    VALUES(?,?);
                    """;
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            newId = generatedKeys.getInt(1);

        }
        catch(SQLException e)
        {

        }
        // create a new category
        return getById(newId);
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        try(Connection connection = getConnection())
        {
            String sql = """
                    UPDATE categories
                    SET name = ?
                        ,description = ?
                    WHERE category_id = ?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, categoryId);

            statement.executeUpdate();


        }
        catch(SQLException e)
        {

        }
    }

    @Override
    public boolean delete(int categoryId)
    {
        // delete category
        try(Connection connection = getConnection())
        {
            String sql = """
                    DELETE FROM categories WHERE category_id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,categoryId);

            statement.executeUpdate();

        }catch(SQLException e)
        {

        }
        return false;
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
