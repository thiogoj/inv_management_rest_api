package joaquinthiogo.inventorymanagementapi.service;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Item;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Unit;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.item.ItemRequest;
import joaquinthiogo.inventorymanagementapi.model.item.ItemResponse;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitResponse;
import joaquinthiogo.inventorymanagementapi.repository.ItemRepository;
import joaquinthiogo.inventorymanagementapi.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ValidationService validationService;

    private ItemResponse toItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .noPart(item.getNoPart())
                .description(item.getDescription())
                .hsCode(item.getHsCode())
                .itemType(item.getItemType())
                .unit(UnitResponse.builder()
                        .id(item.getUnit().getId())
                        .name(item.getUnit().getName())
                        .build())
                .build();
    }

    @Transactional
    public ItemResponse insert(User user, ItemRequest request) {
        validationService.validate(request);

        Unit unit = unitRepository.findFirstByName(request.getUnit().getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit is not found"));

        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setNoPart(request.getNoPart());
        item.setDescription(request.getDescription());
        item.setHsCode(request.getHsCode());
        item.setItemType(request.getItemType());
        item.setUnit(unit);
        itemRepository.save(item);

        return toItemResponse(item);
    }

    @Transactional(readOnly = true)
    public Page<ItemResponse> findAll(User user, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Order.desc("createdAt")));
        Page<Item> page = itemRepository.findAll(pageable);

        return page.map(this::toItemResponse);
    }

    @Transactional(readOnly = true)
    public ItemResponse findById(User user, String itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item is not found"));
        return toItemResponse(item);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> searchByDescription(User user, String description) {
        List<Item> items = itemRepository.searchItemsUsingDescription("%" + description + "%");
        return items.stream().map(this::toItemResponse).collect(Collectors.toList());
    }

    @Transactional
    public ItemResponse update(User user, ItemRequest request, String itemId) {
        validationService.validate(request);

        Unit unit = unitRepository.findFirstByName(request.getUnit().getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit is not found"));

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item is not found"));
        item.setNoPart(request.getNoPart());
        item.setDescription(request.getDescription());
        item.setHsCode(request.getHsCode());
        item.setItemType(request.getItemType());
        item.setUnit(unit);
        itemRepository.save(item);

        return toItemResponse(item);
    }

    @Transactional
    public void delete(User user, String itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item is not found"));
        itemRepository.delete(item);
    }
}
