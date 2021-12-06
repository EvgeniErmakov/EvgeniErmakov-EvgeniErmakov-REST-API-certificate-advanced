package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.CertificateNotFoundException;
import com.epam.esm.model.exception.OrderNotFoundException;
import com.epam.esm.model.exception.UserNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final CertificateDAO certificateDAO;
    private final UserDAO userDAO;
    private final MapperDTO mapperDTO;


    @Override
    public List<OrderDTO> findAll(Page page) {
        return orderDAO.findAll(page)
            .stream()
            .map(mapperDTO::convertOrderToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findById(Long id) {
        return mapperDTO.convertOrderToDTO(orderDAO.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id.toString())));
    }

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        orderDTO.setCost(new BigDecimal(0));
        Order order = mapperDTO.convertDTOToOrder(orderDTO);
        User user = userDAO.findById(orderDTO.getUserId())
            .orElseThrow(() -> new UserNotFoundException(orderDTO.getUserId().toString()));
        order.setUser(user);
        order.setCost(orderDTO.getCost());
        order.setCertificates(getCertificates(orderDTO));
        order = orderDAO.create(order);
        return mapperDTO.convertOrderToDTO(order);
    }

    private List<Certificate> getCertificates(OrderDTO orderDTO) {
        List<Certificate> certificates = new ArrayList<>();
        orderDTO.getCertificateId().forEach(id -> {
            Certificate certificate = certificateDAO.findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id.toString()));
            if (!certificate.isActive()) {
                throw new CertificateNotFoundException(id.toString());
            }
            certificates.add(certificate);
            orderDTO.setCost(orderDTO.getCost().add(certificate.getPrice()));
        });
        return certificates;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<OrderDTO> findAllOrdersByUserId(Long id, Page page) {
        return userDAO
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString()))
            .getOrders()
            .stream()
            .distinct()
            .skip(((long) page.getPage() * page.getSize()) - page.getSize())
            .limit(page.getSize())
            .map(mapperDTO::convertOrderToDTO)
            .collect(Collectors.toList());
    }
}
