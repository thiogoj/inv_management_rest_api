package joaquinthiogo.inventorymanagementapi.service;

import jakarta.persistence.Table;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Supplier;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.supplier.SupplierRequest;
import joaquinthiogo.inventorymanagementapi.model.supplier.SupplierResponse;
import joaquinthiogo.inventorymanagementapi.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class SupplierService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private SupplierRepository supplierRepository;

    private SupplierResponse toSupplierResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .build();
    }

    @Transactional
    public SupplierResponse insert(User user, SupplierRequest request) {
        validationService.validate(request);

        Supplier supplier = new Supplier();
        supplier.setId(UUID.randomUUID().toString());
        supplier.setName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setPhone(request.getPhone());
        supplierRepository.save(supplier);

        return toSupplierResponse(supplier);
    }

    @Transactional(readOnly = true)
    public Page<SupplierResponse> findAll(User user, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Order.asc("createdAt")));
        Page<Supplier> page = supplierRepository.findAll(pageable);

        return page.map(this::toSupplierResponse);
    }

    @Transactional(readOnly = true)
    public SupplierResponse findById(User user, String supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier is not found"));
        return toSupplierResponse(supplier);
    }

    @Transactional
    public SupplierResponse update(User user, String supplierId, SupplierRequest request) {
        validationService.validate(request);

        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier is not found"));
        supplier.setName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setPhone(request.getPhone());
        supplierRepository.save(supplier);

        return toSupplierResponse(supplier);
    }

    @Transactional
    public void delete(User user, String supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier is not found"));
        supplierRepository.delete(supplier);
    }
}
