package com.epam.esm.service;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.Page;

import com.epam.esm.model.entity.User;
import com.epam.esm.security.jwt.JwtUser;
import java.util.List;

public interface OrderService {

    List<OrderDTO> findAllOrdersByUserId(Long id, Page page);

    OrderDTO create(OrderDTO orderDTO, JwtUser jwtUser);

    List<OrderDTO> findAll(Page page);

    OrderDTO findById(Long id);

}
