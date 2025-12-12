package org.iesvdm.shoppingcart.service;


import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerOrderService {
    private CustomerOrderRepository customerOrderRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    public List<CustomerOrder> getAllCustomerOrders(){
        return customerOrderRepository.getAllCustomerOrders();
    }

    public void updateCustomerOrder(CustomerOrder customerOrder){
        customerOrderRepository.updateCustomerOrder(customerOrder);
    }
}
