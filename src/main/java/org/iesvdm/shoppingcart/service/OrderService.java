package org.iesvdm.shoppingcart.service;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.model.OrderItem;
import org.iesvdm.shoppingcart.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrdersRepository ordersRepository;

    public OrderService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }


    public CustomerOrder findById(long id){
        return ordersRepository.findById(id);
    }
    public List<OrderItem> orderlist(long id){
        return ordersRepository.getAllOrders(id);
    }

    public void update(OrderItem orderItem,long id){
        ordersRepository.update(orderItem,id);
    }

    public OrderItem findOrderById(long id){
       return ordersRepository.findOrderItemById(id);
    }

    public void delete(long id){
        ordersRepository.delete(id);
    }

    public OrderItem createOrderItem(OrderItem orderItem){
        return ordersRepository.createOrderItem(orderItem);
    }
}


