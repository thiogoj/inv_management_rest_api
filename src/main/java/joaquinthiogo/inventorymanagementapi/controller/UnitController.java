package joaquinthiogo.inventorymanagementapi.controller;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.PagingResponse;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitRequest;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitResponse;
import joaquinthiogo.inventorymanagementapi.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UnitController {

    @Autowired
    private UnitService unitService;

    @PostMapping(path = "/api/unit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UnitResponse> insert(User user, @RequestBody UnitRequest request) {
        UnitResponse response = unitService.insert(user, request);
        return WebResponse.<UnitResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(path = "/api/unit", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<UnitResponse>> findAll(User user, @Param(value = "pageNumber") Integer pageNumber) {
        Page<UnitResponse> unitResponses = unitService.findAll(user, pageNumber);
        return WebResponse.<List<UnitResponse>>builder()
                .data(unitResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(unitResponses.getNumber())
                        .totalPage(unitResponses.getTotalPages())
                        .size(unitResponses.getSize())
                        .build())
                .build();
    }

    @GetMapping(path = "/api/unit/{unitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UnitResponse> findById(User user, @PathVariable(name = "unitId") Integer unitId) {
        UnitResponse response = unitService.findById(user, unitId);
        return WebResponse.<UnitResponse>builder()
                .data(response)
                .build();
    }

    @PutMapping(path = "/api/unit/{unitId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UnitResponse> update(User user, @RequestBody UnitRequest request, @PathVariable(value = "unitId") Integer unitId) {
        UnitResponse response = unitService.update(user, request, unitId);
        return WebResponse.<UnitResponse>builder()
                .data(response)
                .build();
    }

    @DeleteMapping(path = "/api/unit/{unitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(User user, @PathVariable(value = "unitId") Integer unitId) {
        unitService.delete(user, unitId);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }
}
