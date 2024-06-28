package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.*;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController()
@RequestMapping("/orders")
@CrossOrigin
public class OrdersController
{
    private ProductDao productDao;
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private OrderDao orderDao;
    private ProfileDao profileDao;

    @Autowired
    public OrdersController(OrderDao orderDao, ProductDao productDao, ShoppingCartDao shoppingCart, UserDao userDao, ProfileDao profile)
    {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.shoppingCartDao = shoppingCart;
        this.userDao = userDao;
        this.profileDao = profile;
    }

    @PostMapping("")
    @PreAuthorize("permitAll()")
    @ResponseStatus(HttpStatus.CREATED)
    public Order submitOrder(Principal principal)
    {

        Order newOrder = new Order();
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            ShoppingCart cart = shoppingCartDao.getByUserId(userId);
            Profile userProfile = profileDao.getByUserId(userId);

            LocalDate date = LocalDate.now();

            newOrder.setUser_id(userId);
            newOrder.setDate(date);
            newOrder.setAddress(userProfile.getAddress());
            newOrder.setCity(userProfile.getCity());
            newOrder.setState(userProfile.getState());
            newOrder.setZip(userProfile.getZip());
            newOrder.setShipping_amount(100.00);
            int orderUserId =  orderDao.createOrder(newOrder).getUser_id();


            System.out.println(orderUserId);



            return orderDao.getOrderByUserId(orderUserId);

        }
        catch(Exception e)
        {

            System.out.println(e.getMessage());
        }

        return null;
        // you would be calling the method(s) from your MySqlOrderDao
    }

}
