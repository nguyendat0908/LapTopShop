package com.DatLeo.LapTopShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.DatLeo.LapTopShop.domain.Order;
import com.DatLeo.LapTopShop.domain.OrderDetail;
import com.DatLeo.LapTopShop.domain.User;
import com.DatLeo.LapTopShop.repository.OrderDetailRepository;
import com.DatLeo.LapTopShop.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public Page<Order> getAllOrderPage(Pageable pageable){
        return this.orderRepository.findAll(pageable);
    }

    public Optional<Order> getOrderById(long id){
        return this.orderRepository.findById(id);
    }

    public void deleteOrderById(long id){

        // Delete OrderDetail
        Optional<Order> order = this.getOrderById(id);

        if (order.isPresent()) {
            Order currentOrder = order.get();

            List<OrderDetail> orderDetails = currentOrder.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }
        this.orderRepository.deleteById(id);
    }

    public void updateOrder(Order order){

        Optional<Order> orderOptional = this.getOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();

            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public List<Order> getOrderByUser(User user){
        return this.orderRepository.findByUser(user);
    }
    
}
