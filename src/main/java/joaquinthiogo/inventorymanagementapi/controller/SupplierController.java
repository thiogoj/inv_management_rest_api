package joaquinthiogo.inventorymanagementapi.controller;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.PagingResponse;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.supplier.SupplierRequest;
import joaquinthiogo.inventorymanagementapi.model.supplier.SupplierResponse;
import joaquinthiogo.inventorymanagementapi.repository.SupplierRepository;
import joaquinthiogo.inventorymanagementapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping(path = "/api/supplier", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<SupplierResponse> insert(User user, @RequestBody SupplierRequest request) {
        SupplierResponse response = supplierService.insert(user, request);
        return WebResponse.<SupplierResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(path = "/api/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<SupplierResponse>> findAll(User user, @Param(value = "pageNumber") Integer pageNumber) {
        Page<SupplierResponse> responses = supplierService.findAll(user, pageNumber);
        return WebResponse.<List<SupplierResponse>>builder()
                .data(responses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(responses.getNumber())
                        .totalPage(responses.getTotalPages())
                        .size(responses.getSize())
                        .build())
                .build();
    }

    @GetMapping(path = "/api/supplier/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<SupplierResponse> findById(User user, @PathVariable(name = "supplierId") String supplierId) {
        SupplierResponse response = supplierService.findById(user, supplierId);
        return WebResponse.<SupplierResponse>builder()
                .data(response)
                .build();
    }

    @PutMapping(path = "/api/supplier/{supplierId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<SupplierResponse> update(User user, @PathVariable(name = "supplierId") String supplierId, @RequestBody SupplierRequest request) {
        SupplierResponse response = supplierService.update(user, supplierId, request);
        return WebResponse.<SupplierResponse>builder()
                .data(response)
                .build();
    }

    @DeleteMapping(path = "/api/supplier/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(User user, @PathVariable(name = "supplierId") String supplierId) {
        supplierService.delete(user, supplierId);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }
}
