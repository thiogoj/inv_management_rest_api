package joaquinthiogo.inventorymanagementapi.controller;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.PagingResponse;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.item.ItemRequest;
import joaquinthiogo.inventorymanagementapi.model.item.ItemResponse;
import joaquinthiogo.inventorymanagementapi.repository.ItemRepository;
import joaquinthiogo.inventorymanagementapi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(path = "/api/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ItemResponse> insert(User user, @RequestBody ItemRequest request) {
        ItemResponse response = itemService.insert(user, request);
        return WebResponse.<ItemResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(path = "/api/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ItemResponse>> findAll(User user, @Param(value = "pageNumber") Integer pageNumber) {
        Page<ItemResponse> responsePage = itemService.findAll(user, pageNumber);

        return WebResponse.<List<ItemResponse>>builder()
                .data(responsePage.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(responsePage.getNumber())
                        .totalPage(responsePage.getTotalPages())
                        .size(responsePage.getSize())
                        .build())
                .build();
    }

    @GetMapping(path = "/api/search/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ItemResponse>> searchByDescription(User user, @Param(value = "description") String description) {
        List<ItemResponse> itemResponses = itemService.searchByDescription(user, description);
        return WebResponse.<List<ItemResponse>>builder()
                .data(itemResponses)
                .build();
    }

    @GetMapping(path = "/api/item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ItemResponse> findById(User user, @PathVariable(name = "itemId") String itemId) {
        ItemResponse response = itemService.findById(user, itemId);
        return WebResponse.<ItemResponse>builder()
                .data(response)
                .build();
    }

    @PutMapping(path = "/api/item/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ItemResponse> update(User user, @RequestBody ItemRequest request, @PathVariable(value = "itemId") String itemId) {
        ItemResponse response = itemService.update(user, request, itemId);
        return WebResponse.<ItemResponse>builder()
                .data(response)
                .build();
    }

    @DeleteMapping(path = "/api/item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(User user, @PathVariable(value = "itemId") String itemId) {
        itemService.delete(user, itemId);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }

}

