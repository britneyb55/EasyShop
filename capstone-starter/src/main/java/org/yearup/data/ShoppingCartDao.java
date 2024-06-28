package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ShoppingCart addProductToCart(int product, int userId);
    // add additional method signatures here'
    void updateProductQuantityInsideCart(int product, int userid, int item);
    void deleteCartProducts(int userid);
}
