package com.lambdaschool.shoppingcart.controllers;

import com.lambdaschool.shoppingcart.exceptions.ResourceNotFoundException;
import com.lambdaschool.shoppingcart.models.Cart;
import com.lambdaschool.shoppingcart.models.Product;
import com.lambdaschool.shoppingcart.models.User;
import com.lambdaschool.shoppingcart.models.UserRoles;
import com.lambdaschool.shoppingcart.repositories.UserRepository;
import com.lambdaschool.shoppingcart.services.CartService;
import com.lambdaschool.shoppingcart.services.UserAuditing;
import com.lambdaschool.shoppingcart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/carts")
public class CartController
{
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuditing userAuditing;

    @GetMapping(value = "/user/{userid}", produces = {"application/json"})
    public ResponseEntity<?> listAllCarts(@PathVariable long userid)
    {
        List<Cart> myCarts = cartService.findAllByUserId(userid);
        return new ResponseEntity<>(myCarts, HttpStatus.OK);
    }

    @GetMapping(value = "/cart/{cartId}",
            produces = {"application/json"})
    public ResponseEntity<?> getCartById(
            @PathVariable
                    Long cartId)
    {
        Cart p = cartService.findCartById(cartId);
        return new ResponseEntity<>(p,
                                    HttpStatus.OK);
    }

    @GetMapping(value = "/user", produces = {"application/json"})
    public ResponseEntity<?> getCartById()
    {
        User foundUser = userService.findByName(userAuditing.getCurrentAuditor()
            .orElseThrow(() -> new ResourceNotFoundException("Current Auditor not found!")));
        List<Cart> cartList = foundUser.getCarts();
        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    @PostMapping(value = "/create/user/{userid}/product/{productid}")
    public ResponseEntity<?> addNewCart(@PathVariable long userid,
                                        @PathVariable long productid)
    {
        User dataUser = new User();
        dataUser.setUserid(userid);

        Product dataProduct = new Product();
        dataProduct.setProductid(productid);

        cartService.save(dataUser, dataProduct);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/create/product/{productid}")
    public ResponseEntity<?> addNewCart(@PathVariable long productid)
    {
        User dataUser = userService.findByName(userAuditing.getCurrentAuditor()
            .orElseThrow(() -> new ResourceNotFoundException("Current Auditor not found!")));

        Product dataProduct = new Product();
        dataProduct.setProductid(productid);

        cartService.save(dataUser, dataProduct);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/cart/{cartid}/product/{productid}")
    public ResponseEntity<?> updateCart(@PathVariable long cartid,
                                        @PathVariable long productid)
    {
        User user = userService.findByName(userAuditing.getCurrentAuditor()
            .orElseThrow(() -> new ResourceNotFoundException("Role id current Auditor not found!")));
        Set<UserRoles> userRolesSet = user.getRoles();
        List<Cart> cartList = user.getCarts();

        Cart dataCart = new Cart();
        dataCart.setCartid(cartid);

        Product dataProduct = new Product();
        dataProduct.setProductid(productid);

        Boolean userHasAccessToCart = false;
        for(Cart c : cartList) {
            if (c.getUser()
                .getUserid() == dataCart.getUser()
                .getUserid()) {

                userHasAccessToCart = true;
            }
        }
        for(UserRoles ur: userRolesSet){
            if (ur.getRole().getName() == "ADMIN"){
                userHasAccessToCart = true;
            }
        }

        if(userHasAccessToCart){
            cartService.save(dataCart, dataProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/delete/cart/{cartid}/product/{productid}")
    public ResponseEntity<?> deleteFromCart(@PathVariable long cartid,
                                            @PathVariable long productid)
    {
        User user = userService.findByName(userAuditing.getCurrentAuditor()
            .orElseThrow(() -> new ResourceNotFoundException("Role id current Auditor not found!")));
        Set<UserRoles> userRolesSet = user.getRoles();
        List<Cart> cartList = user.getCarts();

        Cart dataCart = new Cart();
        dataCart.setCartid(cartid);

        Product dataProduct = new Product();
        dataProduct.setProductid(productid);

        Boolean userHasAccessToCart = false;
        for(Cart c : cartList) {
            if (c.getUser()
                .getUserid() == dataCart.getUser()
                .getUserid()) {

                userHasAccessToCart = true;
            }
        }
        for(UserRoles ur: userRolesSet){
            if (ur.getRole().getName() == "ADMIN"){
                userHasAccessToCart = true;
            }
        }

        if(userHasAccessToCart){
            cartService.delete(dataCart, dataProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
