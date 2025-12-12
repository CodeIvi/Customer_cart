package org.iesvdm.shoppingcart.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.model.OrderItem;
import org.iesvdm.shoppingcart.model.Product;
import org.iesvdm.shoppingcart.repository.CustomerOrderRepository;
import org.iesvdm.shoppingcart.service.CustomerOrderService;
import org.iesvdm.shoppingcart.service.OrderService;
import org.iesvdm.shoppingcart.service.ProductService;
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


    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerOrderService customerOrderService;

    public ShoppingController(OrderService orderService, ProductService productService, CustomerOrderService customerOrderService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerOrderService = customerOrderService;
    }

    @ModelAttribute("cart")
    public CustomerOrder cart() {
        return new CustomerOrder();
    }


    @GetMapping("/login")
    public String loginGet(Model model) {
        List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrders();
        model.addAttribute("customerOrders",customerOrders);

        return "login";
    }


    @PostMapping("/yourcart")
    public String cartGet(Model model, @RequestParam(value = "idCart", required = false) Long idCart,
                          @RequestParam(value = "idProduct", required = false) Long idProduct,
                          @RequestParam(value = "action", required = false) String action,
                          @RequestParam(value = "idAddProduct", required = false) Long idAddProduct,
                          @RequestParam(value = "quantity", required = false) BigDecimal quantity,
                          @ModelAttribute Product product) {
        model.addAttribute("product", new Product());
        model.addAttribute("idCart", idCart);
        model.addAttribute("listaProductos", productService.getALL());

        CustomerOrder customerOrder = orderService.findById(idCart);
        List<OrderItem> orderList = orderService.orderlist(idCart);

        if (customerOrder != null && customerOrder.getStatus().equalsIgnoreCase("new")) {
            model.addAttribute("cart", customerOrder);
            model.addAttribute("orderlist", orderList);


            if (action != null && action.equals("increment")) {
                OrderItem item = orderService.findOrderById(idProduct);
                item.setQuantity(item.getQuantity().add(BigDecimal.valueOf(1)));
                orderService.update(item, idProduct);
                orderList = orderService.orderlist(idCart);
                model.addAttribute("orderlist", orderList);
                model.addAttribute("listaProductos", productService.getALL());
            } else if (action != null && action.equals("subtract")) {
                OrderItem item = orderService.findOrderById(idProduct);
                if(item.getQuantity() == BigDecimal.valueOf(0)){

                }else {
                    item.setQuantity(item.getQuantity().subtract(BigDecimal.valueOf(1)));
                    orderService.update(item, idProduct);
                    orderList = orderService.orderlist(idCart);
                    model.addAttribute("orderlist", orderList);
                    model.addAttribute("listaProductos", productService.getALL());
                }
            } else if (action != null && action.equals("delete")) {
                orderService.delete(idProduct);
                orderList = orderService.orderlist(idCart);
                model.addAttribute("orderlist", orderList);
                model.addAttribute("listaProductos", productService.getALL());

            }else if(action != null && action.equals("add")) {
                Product getProduct = productService.findById(idAddProduct);

                OrderItem newOrderitem = OrderItem.builder()
                        .orderId(idCart)
                        .productId(idAddProduct)
                        .productName(getProduct.getName())
                        .unitPrice(getProduct.getPrice())
                        .quantity(quantity)
                        .lineTotal(getProduct.getPrice().multiply(quantity))
                        .build();
                orderService.createOrderItem(newOrderitem);
                orderService.update(newOrderitem, idAddProduct);
                orderList = orderService.orderlist(idCart);
                model.addAttribute("orderlist", orderList);
                model.addAttribute("listaProductos", productService.getALL());

            }
            log.info("lISTA {}", orderList);
            return "cart";


        } else {
            List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrders();
            model.addAttribute("customerOrders",customerOrders);
            String mensaje = "The cart with " + idCart + " is in process";
            model.addAttribute("mensaje", mensaje);

            return "login";

        }
    }
}
