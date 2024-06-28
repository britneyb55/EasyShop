package org.yearup.data;

import org.yearup.models.Order;

public interface OrderDao
{
    Order createOrder(Order order);

    Order getOrderByUserId(int userId);

}
