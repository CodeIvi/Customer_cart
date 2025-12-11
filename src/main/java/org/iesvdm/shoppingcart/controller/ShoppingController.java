package org.iesvdm.shoppingcart.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.model.OrderItem;
import org.iesvdm.shoppingcart.model.Product;
import org.iesvdm.shoppingcart.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class ShoppingController {


    private final CustomerOrderService customerOrderService;

    public ShoppingController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @ModelAttribute("cart")
    public CustomerOrder cart(){
        return new CustomerOrder();
    }



    @GetMapping("/login")
    public String loginGet() {

        return "login";
    }



    @PostMapping("/yourcart")
    public String cartGet(Model model, @RequestParam(value= "idCart",required = false) Long idCart,
                          @RequestParam(value = "idProduct",required = false) Long idProduct,
                          @RequestParam(value = "action",required = false) String action,
                          @ModelAttribute Product product){
        model.addAttribute("product",new Product());
        model.addAttribute("idCart",idCart);

        CustomerOrder customerOrder = customerOrderService.findById(idCart);
        List<OrderItem> orderList = customerOrderService.orderlist(idCart);

        if(customerOrder.getStatus().equalsIgnoreCase("new")){
            model.addAttribute("cart",customerOrder);
            model.addAttribute("orderlist",orderList);


            if(action!=null && action.equals("increment")){
                OrderItem item = customerOrderService.findOrderById(idProduct);
                item.setQuantity(item.getQuantity().add(BigDecimal.valueOf(1)));
                customerOrderService.update(item,idProduct);
                orderList = customerOrderService.orderlist(idCart);
                model.addAttribute("orderlist",orderList);
            }else if(action!=null && action.equals("subtract")){
                OrderItem item = customerOrderService.findOrderById(idProduct);
                item.setQuantity(item.getQuantity().subtract(BigDecimal.valueOf(1)));
                customerOrderService.update(item,idProduct);
                orderList = customerOrderService.orderlist(idCart);
                model.addAttribute("orderlist",orderList);
            }else if(action != null && action.equals("delete")){
                customerOrderService.delete(idProduct);
                orderList = customerOrderService.orderlist(idCart);
                model.addAttribute("orderlist",orderList);

            }
            log.info("lISTA {}",orderList);
            return "cart";
        }else{
            String mensaje = "The cart with "+idCart+ " doesn't exist";
            model.addAttribute("mensaje",mensaje);

            return "login";

        }

    }
}
