package joaquinthiogo.inventorymanagementapi.service;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Customer;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.customer.CustomerRequest;
import joaquinthiogo.inventorymanagementapi.model.customer.CustomerResponse;
import joaquinthiogo.inventorymanagementapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ValidationService validationService;

    private CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .build();
    }

    @Transactional
    public CustomerResponse insert(User user, CustomerRequest request) {
        validationService.validate(request);

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
        return toCustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponse> findAll(User user, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);

        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage.map(this::toCustomerResponse);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(User user, String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer is not found"));
        return toCustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse update(User user, String customerId, CustomerRequest request) {
        validationService.validate(request);

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer is not found"));
        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);

        return toCustomerResponse(customer);
    }

    @Transactional
    public void delete(User user, String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer is not found"));
        customerRepository.delete(customer);
    }


}
