package org.iesvdm.shoppingcart.controller;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.model.OrderItem;
import org.iesvdm.shoppingcart.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class ShoppingController {


    private final CustomerOrderService customerOrderService;

    public ShoppingController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @ModelAttribute("cart")
    public OrderItem initorderItem(){
        return new OrderItem();
    }
    @GetMapping("/cart")
    public String cartGet(@RequestAttribute(value= "cartId",required = false) Long id){
        CustomerOrder customerOrder = customerOrderService.findById(id);
        if(customerOrder.getStatus().equals("new")){
            return "chekout";
        }
        return "cart";
    }
}
