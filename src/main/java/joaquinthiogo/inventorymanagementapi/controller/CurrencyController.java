package joaquinthiogo.inventorymanagementapi.controller;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.PagingResponse;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.currency.CurrencyRequest;
import joaquinthiogo.inventorymanagementapi.model.currency.CurrencyResponse;
import joaquinthiogo.inventorymanagementapi.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping(path = "/api/currency", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CurrencyResponse> insert(User user, @RequestBody CurrencyRequest request) {
        CurrencyResponse response = currencyService.insert(user, request);
        return WebResponse.<CurrencyResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(path = "/api/currency", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<CurrencyResponse>> findAll(User user, @Param(value = "pageNumber") Integer pageNumber) {
        Page<CurrencyResponse> responsePage = currencyService.findAll(user, pageNumber);

        return WebResponse.<List<CurrencyResponse>>builder()
                .data(responsePage.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(responsePage.getNumber())
                        .totalPage(responsePage.getTotalPages())
                        .size(responsePage.getSize())
                        .build())
                .build();
    }

    @GetMapping(path = "/api/currency/{currencyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CurrencyResponse> findById(User user, @PathVariable(name = "currencyId") Integer currencyId) {
        CurrencyResponse response = currencyService.findById(user, currencyId);
        return WebResponse.<CurrencyResponse>builder()
                .data(response)
                .build();
    }

    @PutMapping(path = "/api/currency/{currencyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CurrencyResponse> update(User user, @RequestBody CurrencyRequest request, @PathVariable(name = "currencyId") Integer currencyId) {
        CurrencyResponse response = currencyService.update(user, request, currencyId);
        return WebResponse.<CurrencyResponse>builder()
                .data(response)
                .build();
    }

    @DeleteMapping(path = "/api/currency/{currencyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(User user, @PathVariable(name = "currencyId") Integer currencyId) {
        currencyService.delete(user, currencyId);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }

}
