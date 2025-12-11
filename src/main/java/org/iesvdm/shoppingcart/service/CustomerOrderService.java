package org.iesvdm.shoppingcart.service;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.model.OrderItem;
import org.iesvdm.shoppingcart.repository.ShoppingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderService {
    private ShoppingRepository shoppingRepository;

    public CustomerOrderService(ShoppingRepository shoppingRepository) {
        this.shoppingRepository = shoppingRepository;
    }


    public CustomerOrder findById(long id){
        return shoppingRepository.findById(id);
    }
    public List<OrderItem> orderlist(long id){
        return shoppingRepository.getAllOrders(id);
    }

    public void update(OrderItem orderItem,long id){
        shoppingRepository.update(orderItem,id);
    }

    public OrderItem findOrderById(long id){
       return shoppingRepository.findOrderItemById(id);
    }

    public void delete(long id){
        shoppingRepository.delete(id);
    }

    public OrderItem createOrderItem(OrderItem orderItem){
        return shoppingRepository.createOrderItem(orderItem);
    }
}


