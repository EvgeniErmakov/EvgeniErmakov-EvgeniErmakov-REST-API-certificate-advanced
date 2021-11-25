package com.epam.esm.controller;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    private static final int MIN_ID = 1;

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public UserDTO findById(@PathVariable @Min(MIN_ID) Long id) {
        return userService.findById(id);
    }

    /**
     * @param page
     * @return
     */
    @GetMapping
    public List<UserDTO> findAll(@Valid Page page) {
        return ResponseAssembler.assembleUsers(userService.findAll(page));
    }

    /**
     * @param id
     * @param page
     * @return
     */
    @GetMapping(value = "/{id}/orders")
    public List<OrderDTO> findUserOrders(@PathVariable @Min(MIN_ID) Long id, @Valid Page page) {
        return ResponseAssembler.assembleOrders(orderService.findAllOrdersByUserId(id, page));
    }
}
