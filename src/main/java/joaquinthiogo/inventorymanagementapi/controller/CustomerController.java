package joaquinthiogo.inventorymanagementapi.controller;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.PagingResponse;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.customer.CustomerRequest;
import joaquinthiogo.inventorymanagementapi.model.customer.CustomerResponse;
import joaquinthiogo.inventorymanagementapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/api/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CustomerResponse> insert(User user, @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.insert(user, request);
        return WebResponse.<CustomerResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(path = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<CustomerResponse>> findAll(User user, @Param(value = "pageNumber") Integer pageNumber) {
        Page<CustomerResponse> responses = customerService.findAll(user, pageNumber);
        return WebResponse.<List<CustomerResponse>>builder()
                .data(responses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(responses.getNumber())
                        .totalPage(responses.getTotalPages())
                        .size(responses.getSize())
                        .build())
                .build();
    }

    @GetMapping(path = "/api/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CustomerResponse> findById(User user, @PathVariable(value = "customerId") String customerId) {
        CustomerResponse response = customerService.findById(user, customerId);
        return WebResponse.<CustomerResponse>builder()
                .data(response)
                .build();
    }

    @PutMapping(path = "/api/customer/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CustomerResponse> update(User user, @PathVariable(value = "customerId") String customerId, @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.update(user, customerId, request);
        return WebResponse.<CustomerResponse>builder()
                .data(response)
                .build();
    }

    @DeleteMapping(path = "/api/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(User user, @PathVariable(value = "customerId") String customerId) {
        customerService.delete(user, customerId);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }
}
