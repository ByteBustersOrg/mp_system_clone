package org.vyatsu.localApiModule.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.vyatsu.localApiModule.dto.request.api.ItemReqDto;
import org.vyatsu.localApiModule.dto.response.api.item.ItemDto;
import org.vyatsu.localApiModule.entity.item.Item;
import org.vyatsu.localApiModule.entity.user.User;
import org.vyatsu.localApiModule.mapper.ItemMapper;
import org.vyatsu.localApiModule.mapper.UserMapper;
import org.vyatsu.localApiModule.repository.ItemRepository;
import org.vyatsu.localApiModule.security.JwtAuthenticationService;
import org.vyatsu.localApiModule.service.ItemService;
import org.vyatsu.localApiModule.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final JwtAuthenticationService jwtAuthenticationService;
    private final UserService userService;

    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    @Override
    public List<ItemDto> getItemsByUser(HttpServletRequest request) {
        User user = getUserByReq(request);
        List<ItemDto> userItemsDto = new ArrayList<>();
        if (user != null) {
            List<Item> userItems = itemRepository.findByUserId(user.getId());
            userItemsDto = itemMapper.toDtoList(userItems);
        }
        return userItemsDto;
    }

    @Override
    public ItemDto createItem(HttpServletRequest request, @RequestBody ItemDto itemDto){
        User user = getUserByReq(request);

        itemDto.setUser(userMapper.toSimpleUserDto(user));
        itemDto.setIsActive(true);
        itemDto.setCreatedAt(LocalDateTime.now());

        Item createdItem = itemRepository.save(itemMapper.toEntity(itemDto));
        return itemMapper.toDto(createdItem);
    }

    @Override
    public List<String> getCategoryByUser(HttpServletRequest req){
        User user = getUserByReq(req);
        List<String> categories = new ArrayList<>();
        if(user != null) categories = itemRepository.findUniqueCategoryByUserId(user.getId());

        return categories;
    }

    @Override
    public void deleteUserItemById(HttpServletRequest req, ItemReqDto itemReqDto) {
        User user = getUserByReq(req);
        Optional<Item> item = itemRepository.findById(itemReqDto.getId());
        if(user != null && item.isPresent() && item.get().getUser() == user){
            itemRepository.delete(item.get());
        }
    }

    @Override
    public ItemDto getUserItemById(HttpServletRequest req, Long id) {
        User user = getUserByReq(req);
        Optional<Item> item = itemRepository.findById(id);
        ItemDto itemDto = null;
        if(item.isPresent()){
            if(Objects.equals(user, item.get().getUser())){
                itemDto = itemMapper.toDto(item.get());
            }
        }
        return itemDto;
    }

    @Override
    public ItemDto editUserItem(HttpServletRequest request, ItemDto itemDto) {
        User user = getUserByReq(request);
        Optional<Item> item = itemRepository.findById(itemDto.getId());
        Item editedItem = null;
        if(item.isPresent()){
            if(Objects.equals(user, item.get().getUser())){
                editedItem = itemRepository.save(itemMapper.toEntity(itemDto));
            }
        }
        return itemMapper.toDto(editedItem);
    }

    private User getUserByReq(HttpServletRequest req){
        User user = null;
        final String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;

        jwt = authHeader.substring(7);
        String email = jwtAuthenticationService.extractUsername(jwt);
        if(email != null) user = userService.getUserByEmail(email);
        return user;
    }

}
