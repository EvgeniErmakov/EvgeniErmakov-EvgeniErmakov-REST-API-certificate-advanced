package com.epam.esm.service;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.User;
import com.epam.esm.util.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.epam.esm.service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    public OrderDAO orderDAO;

    @Mock
    public UserDAO userDAO;

    @Mock
    public MapperDTO mapperDTO;

    private static OrderDTO orderDTO;
    private static Order order;

    @BeforeEach
    public void initOrder() {
        order = Order.builder()
                .cost(new BigDecimal("100"))
                .build();
        orderDTO = OrderDTO.builder()
                .userId(1L)
                .certificateId(new ArrayList<>())
                .cost(new BigDecimal("100"))
                .build();
    }

    @Test
    void findOrderById() {
        Optional<Order> optionalOrder = Optional.of(order);
        Long id = 1L;
        Mockito.when(orderDAO.findById(id)).thenReturn(optionalOrder);
        Mockito.when(mapperDTO.convertOrderToDTO(order)).thenReturn(orderDTO);
        OrderDTO actual = orderService.findById(id);
        verify(orderDAO).findById(id);
        verifyNoMoreInteractions(orderDAO);
        Assertions.assertEquals(orderDTO, actual);
    }

    @Test
    void findAllOrder() {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> expected = new ArrayList<>();
        Mockito.when(mapperDTO.convertOrderToDTO(order)).thenReturn(orderDTO);
        Mockito.when(orderDAO.findAll(new Page())).thenReturn(orders);
        orders.add(order);
        expected.add(orderDTO);
        List<OrderDTO> actual = orderService.findAll(new Page());
        verify(orderDAO).findAll(new Page());
        verifyNoMoreInteractions(orderDAO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createOrder() {
        Mockito.when(userDAO.findById(1L)).thenReturn(Optional.of(new User()));
        Mockito.when(mapperDTO.convertDTOToOrder(orderDTO)).thenReturn(order);
        Mockito.when(orderDAO.create(order)).thenReturn(order);
        Mockito.when(mapperDTO.convertOrderToDTO(order)).thenReturn(orderDTO);
        OrderDTO actual = orderService.create(orderDTO);
        verify(orderDAO).create(order);
        verifyNoMoreInteractions(orderDAO);
        Assertions.assertEquals(orderDTO, actual);
    }

    @Test
    void findAllOrdersByUserId() {
        Long id = 1L;
        User user = User.builder().orders(new HashSet<>()).build();
        Mockito.when(userDAO.findById(id)).thenReturn(Optional.of(user));
        List<OrderDTO> actual = orderService.findAllOrdersByUserId(id, new Page());
        Assertions.assertTrue(actual.isEmpty());
    }
}
