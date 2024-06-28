package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController
{
    private final ProfileDao profileDao;
    private final UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao,UserDao userDao)
    {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }


    public Profile register(Profile profile)
    {
        return profileDao.create(profile);
    }

    @GetMapping("")
    public Profile getProfile(Principal principal)
    {

        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            Profile profile = profileDao.getByUserId(userId);
            return profile;
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

    }

    @PutMapping("")
    public Profile updateProfile(Principal principal , @RequestBody Profile profile)
    {
        try{
            System.out.println(profile);


            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

           return profileDao.updateProfile(userId,profile);


        } catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

    }


}
