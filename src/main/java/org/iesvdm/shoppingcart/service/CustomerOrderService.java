package org.iesvdm.shoppingcart.service;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.repository.ShoppingRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService {
private ShoppingRepository shoppingRepository;

    public CustomerOrderService(ShoppingRepository shoppingRepository) {
        this.shoppingRepository = shoppingRepository;
    }


    public CustomerOrder findById(long id){
        return shoppingRepository.findById(id);
    }
}
