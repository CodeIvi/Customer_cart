package org.iesvdm.shoppingcart.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.shoppingcart.model.Coupon;
import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.model.OrderItem;
import org.iesvdm.shoppingcart.model.Product;
import org.iesvdm.shoppingcart.repository.CouponRepository;
import org.iesvdm.shoppingcart.repository.CustomerOrderRepository;
import org.iesvdm.shoppingcart.service.CouponService;
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
@SessionAttributes("customerOrder")
public class ShoppingController {


    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerOrderService customerOrderService;
    private final CouponService couponService;

    public ShoppingController(OrderService orderService, ProductService productService, CustomerOrderService customerOrderService, CouponRepository couponRepository, CouponService couponService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerOrderService = customerOrderService;
        this.couponService = couponService;
    }

    @ModelAttribute("customerOrder")
    public CustomerOrder cart() {
        return new CustomerOrder();
    }


    @GetMapping("/login")
    public String loginGet(Model model) {
        List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrders();
        model.addAttribute("customerOrders",customerOrders);

        return "login";
    }

    @GetMapping("/yourcart")
    public String cartView(Model model, @ModelAttribute("customerOrder") CustomerOrder customerOrder) {
            List<OrderItem> orderList = orderService.orderlist(customerOrder.getId());
            model.addAttribute("orderlist", orderList);
            model.addAttribute("listaProductos", productService.getALL());
            model.addAttribute("cart", customerOrder);
            return "cart";

    }


    @PostMapping("/yourcart")
    public String cartGet(Model model, @RequestParam(value = "idCart", required = false) Long idCart,
                          @RequestParam(value = "idProduct", required = false) Long idProduct,
                          @RequestParam(value = "action", required = false) String action,
                          @RequestParam(value = "idAddProduct", required = false) Long idAddProduct,
                          @RequestParam(value = "quantity", required = false) BigDecimal quantity,
                          @RequestParam(value = "coupon",required = false) String coupon,
                          @ModelAttribute ("customerOrder") CustomerOrder customerOrder) {



        model.addAttribute("idCart", idCart);
        model.addAttribute("listaProductos", productService.getALL());

        customerOrder = orderService.findById(idCart);
        model.addAttribute("customerOrder", customerOrder);
        List<OrderItem> orderList = orderService.orderlist(idCart);
        List<Coupon> couponsList = couponService.couponList();

        if (customerOrder != null && customerOrder.getStatus().equalsIgnoreCase("new")) {
            model.addAttribute("cart", customerOrder);
            model.addAttribute("orderlist", orderList);
            model.addAttribute("couponList",couponsList);


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

            if(coupon != null){
                BigDecimal totalToPay= couponService.applyDiscount(coupon,orderList);
                BigDecimal discount = couponService.Discount(coupon,orderList);
                BigDecimal grossTotal = orderService.grossTotal(orderList);
                customerOrder.setGrossTotal(grossTotal);
                customerOrder.setDiscountTotal(discount);
                customerOrder.setFinalTotal(totalToPay);

                customerOrderService.updateCustomerOrder(customerOrder);


                model.addAttribute("totalToPay",totalToPay);
                model.addAttribute("discount",discount);
                model.addAttribute("grossTotal",grossTotal);


            }


            log.info("lISTA {}", orderList);
            return "cart";


        } else {
            List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrders();
            model.addAttribute("customerOrders",customerOrders);
            String mensaje = "The cart with " + idCart + " is paid for";
            model.addAttribute("mensaje", mensaje);

            return "login";

        }
    }


    @GetMapping("/checkout/step2")
    public String addressesGet(Model model, @ModelAttribute("customerOrder") CustomerOrder customerOrder) {
        model.addAttribute("customerOrder", customerOrder);
        return "step2";
    }

    @PostMapping("/checkout/step2")
    public String addressesPost(@ModelAttribute("customerOrder") CustomerOrder customerOrder,
                                @RequestParam(value = "sameAsBilling", required = false) Boolean sameAsBilling, Model model) {

        if (Boolean.TRUE.equals(sameAsBilling)) {
            customerOrder.setShippingName(customerOrder.getBillingName());
            customerOrder.setShippingStreet(customerOrder.getBillingStreet());
            customerOrder.setShippingCity(customerOrder.getBillingCity());
            customerOrder.setShippingPostalCode(customerOrder.getBillingPostalCode());
            customerOrder.setShippingCountry(customerOrder.getBillingCountry());
        }
        model.addAttribute("customerOrder", customerOrder);
        return "redirect:/cart/checkout/payment";
    }

    // Step 3 - Payment
    @GetMapping("/checkout/payment")
    public String paymentGet(Model model, @ModelAttribute("customerOrder") CustomerOrder customerOrder,
                             @RequestParam(value = "coupon", required = false) String coupon) {
        log.info("****Customer {} ******* ",customerOrder);
        List<OrderItem> orderList = orderService.orderlist(customerOrder.getId());


        model.addAttribute("orderList", orderList);
        model.addAttribute("customerOrder", customerOrder);

        return "payment";
    }

    @PostMapping("/checkout/payment")
    public String paymentPost(@ModelAttribute("customerOrder") CustomerOrder customerOrder,
                              @RequestParam("paymentMethod") String paymentMethod, Model model) {

        customerOrder.setPaymentMethod(paymentMethod);

        customerOrder.setPaymentStatus("PAID");


        customerOrder.setOrderNumber("ORD-" + System.currentTimeMillis());
        List<OrderItem> orderList = orderService.orderlist(customerOrder.getId());
        BigDecimal grossTotal = orderService.grossTotal(orderList);
        BigDecimal discountTotal = customerOrder.getDiscountTotal() != null ? customerOrder.getDiscountTotal() : BigDecimal.ZERO;
        BigDecimal finalTotal = customerOrder.getFinalTotal() != null ? customerOrder.getFinalTotal() : grossTotal;

        customerOrder.setGrossTotal(grossTotal);
        customerOrder.setDiscountTotal(discountTotal);
        customerOrder.setFinalTotal(finalTotal);
        model.addAttribute("customerOrder", customerOrder);


        return "redirect:/cart/checkout/completed";
    }


    @GetMapping("/checkout/completed")
    public String completed(Model model, @ModelAttribute("customerOrder") CustomerOrder customerOrder) {
        model.addAttribute("customerOrder", customerOrder);
        return "completed";
    }

}
