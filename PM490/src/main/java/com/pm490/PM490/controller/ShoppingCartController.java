package com.pm490.PM490.controller;

import com.pm490.PM490.model.ItemList;
import com.pm490.PM490.model.PurchaseStatus;
import com.pm490.PM490.model.User;
import com.pm490.PM490.repository.ItemListRepository;
import com.pm490.PM490.repository.UserRepository;
import com.pm490.PM490.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/shoppingcart")
@PreAuthorize("hasAuthority('CUSTOMER')")

public class ShoppingCartController {

    @Autowired
    ItemListRepository itemListRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getItems")
    List<ItemList> getItems() {
        UserDetailsImpl userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found - %d !"));
        return itemListRepository.findByUserAndCreated(user.getId());
    }

    @PostMapping("/add")
    List<ItemList> add(@RequestBody ItemList newItem) {
        UserDetailsImpl userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found - %d !"));

        newItem.setUser(user);
        newItem.setPurchaseStatus(PurchaseStatus.CREATED);
        itemListRepository.save(newItem);
        return itemListRepository.findByUserAndCreated(user.getId());
    }

    @PostMapping("/update/{id}")
    List<ItemList> update(@RequestBody ItemList itemParam, @PathVariable Long id) {
        UserDetailsImpl userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found - %d !"));

        return itemListRepository.findById(id)
                .map(item -> {
                    item.setQuantity(itemParam.getQuantity());
                    item.setTotal(itemParam.getQuantity() * item.getProduct().getPrice());
                    itemListRepository.save(item);
                    return itemListRepository.findByUserAndCreated(user.getId());
                })
                .orElseGet(() -> {
//                    itemParam.setId(id);
                    itemParam.setUser(user);
                    itemParam.setPurchaseStatus(PurchaseStatus.CREATED);
                    itemListRepository.save(itemParam);
                    return itemListRepository.findByUserAndCreated(user.getId());
                });
    }

    @DeleteMapping("/deleteItem/{id}")
    List<ItemList> delete(@PathVariable Long id) {

        UserDetailsImpl userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found - %d !"));

        itemListRepository.deleteById(id);
        return itemListRepository.findByUserAndCreated(user.getId());
    }
}
