package org.iesvdm.shoppingcart.service;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderService {
private CustomerOrderRepository customerOrderRepository;

    public CustomerOrderService(CustomerOrderService customerOrderService, CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    public CustomerOrder findById(long id){
        return customerOrderRepository.findById(id);
    }
}
