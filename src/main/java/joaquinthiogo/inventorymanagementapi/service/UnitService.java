package joaquinthiogo.inventorymanagementapi.service;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Currency;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Item;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Unit;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.currency.CurrencyResponse;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitRequest;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitResponse;
import joaquinthiogo.inventorymanagementapi.repository.ItemRepository;
import joaquinthiogo.inventorymanagementapi.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ValidationService validationService;

    public UnitResponse toUnitResponse(Unit unit) {
        return UnitResponse.builder()
                .id(unit.getId())
                .name(unit.getName())
                .build();
    }

    @Transactional
    public UnitResponse insert(User user, UnitRequest request) {
        validationService.validate(request);

        unitRepository.findFirstByName(request.getName()).ifPresent(existingUnit -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit name is already exist");
        });

        Unit unit = new Unit();
        unit.setName(request.getName());
        unitRepository.save(unit);

        return toUnitResponse(unit);
    }

    @Transactional(readOnly = true)
    public Page<UnitResponse> findAll(User user, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        return unitRepository.findAll(pageable).map(this::toUnitResponse);
    }

    @Transactional(readOnly = true)
    public UnitResponse findById(User user, Integer unitId) {
        Unit unit = unitRepository.findById(unitId.toString()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit is not found"));
        return toUnitResponse(unit);
    }

    @Transactional
    public UnitResponse update(User user, UnitRequest request, Integer unitId) {
        validationService.validate(request);

        unitRepository.findFirstByName(request.getName()).ifPresent(unit -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit name is already exist");
        });

        Unit unit = unitRepository.findById(unitId.toString()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit is not found"));
        unit.setName(request.getName());
        unitRepository.save(unit);

        return toUnitResponse(unit);
    }

    @Transactional
    public void delete(User user, Integer unitId) {
        List<Item> itemList = itemRepository.findAllByUnitId(unitId);

        if (itemList.isEmpty()) {
            Unit unit = unitRepository.findById(unitId.toString()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit is not found"));
            unitRepository.delete(unit);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Items with this unit must be empty");
        }

    }

}
