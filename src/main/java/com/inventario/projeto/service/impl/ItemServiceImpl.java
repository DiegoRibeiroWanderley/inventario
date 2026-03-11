package com.inventario.projeto.service.impl;

import com.inventario.projeto.DTOs.ItemDTO;
import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.mapper.ItemMapper;
import com.inventario.projeto.model.Item;
import com.inventario.projeto.repositories.ItemRepository;
import com.inventario.projeto.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<ItemDTO> findAll() {
        List<Item> items = itemRepository.findAll();
        return itemMapper.toItemDTOs(items);
    }

    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {
        Item item = itemMapper.toItem(itemDTO);
        item = itemRepository.save(item);
        return itemMapper.toItemDTO(item);
    }

    @Override
    public ItemDTO updateItem(Integer id, ItemDTO itemDTO) {
        Item itemFromDB = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item", id));
        Item itemToBeUpdated = itemMapper.toItem(itemDTO);

        itemToBeUpdated.setId(itemFromDB.getId());
        Item itemUpdated = itemRepository.save(itemToBeUpdated);
        return itemMapper.toItemDTO(itemUpdated);
    }
}
