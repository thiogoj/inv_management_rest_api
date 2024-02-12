package joaquinthiogo.inventorymanagementapi.service;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Currency;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.currency.CurrencyRequest;
import joaquinthiogo.inventorymanagementapi.model.currency.CurrencyResponse;
import joaquinthiogo.inventorymanagementapi.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ValidationService validationService;

    private CurrencyResponse toCurrencyResponse(Currency currency) {
        return CurrencyResponse.builder()
                .id(currency.getId())
                .currency(currency.getCurrency())
                .rate(currency.getRate())
                .remark(currency.getRemark())
                .lastUpdatedAt(currency.getLastUpdatedAt())
                .build();
    }

    @Transactional
    public CurrencyResponse insert(User user, CurrencyRequest request) {
        validationService.validate(request);

        Currency currency = new Currency();
        currency.setCurrency(request.getCurrency());
        currency.setRate(request.getRate());
        currency.setRemark(request.getRemark());
        currencyRepository.save(currency);

        return toCurrencyResponse(currency);
    }

    @Transactional(readOnly = true)
    public Page<CurrencyResponse> findAll(User user, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);

        return currencyRepository.findAll(pageable).map(this::toCurrencyResponse);
    }

    @Transactional(readOnly = true)
    public CurrencyResponse findById(User user, Integer currencyId) {
        Currency currency = currencyRepository.findById(currencyId.toString()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency is not found"));
        return toCurrencyResponse(currency);
    }

    @Transactional
    public CurrencyResponse update(User user, CurrencyRequest request, Integer currencyId) {
        validationService.validate(request);

        Currency currency = currencyRepository.findById(currencyId.toString()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency is not found"));

        currency.setCurrency(request.getCurrency());
        currency.setRate(request.getRate());
        currency.setRemark(request.getRemark());
        currencyRepository.save(currency);

        return toCurrencyResponse(currency);
    }

    @Transactional
    public void delete(User user, Integer currencyId) {
        Currency currency = currencyRepository.findById(currencyId.toString()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency is not found"));
        currencyRepository.delete(currency);
    }

}
