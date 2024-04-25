package br.com.plusfit.service;

import br.com.plusfit.controller.request.AddressRequestDto;
import br.com.plusfit.controller.request.CustomerRequestDto;
import br.com.plusfit.model.Address;
import br.com.plusfit.model.Customer;
import br.com.plusfit.model.mappers.AddressMapper;
import br.com.plusfit.model.mappers.CustomerMapper;
import br.com.plusfit.repository.AddressRepository;
import br.com.plusfit.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

     @Autowired
    private CustomerMapper customerMapper;

     @Autowired
     private AddressMapper addressMapper;

    public Customer save(final Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer update(final CustomerRequestDto customerFromRequest, Long customerId) {
        Customer customerFromDatabase = customerRepository.findByCustomerId(customerId);

        Customer updatedCustomer = this.customerMapper.toEntity(customerFromRequest);

        updatedCustomer.setCustomerId(customerFromDatabase.getCustomerId());
        updatedCustomer.setCustomerCode(customerFromDatabase.getCustomerCode());
        updatedCustomer.setUpdateDate(LocalDateTime.now());
//        updatedCustomer.set
        // Passando as informacoes novas do fromrequest para o customerfromdatabase (informacoes velhas)
        return this.customerRepository.save(updatedCustomer);
    }

    public Address update(final AddressRequestDto addressRequestDto, Long addressId) {
        Address address = addressRepository.findByAddressId(addressId);

        address.setAddressNumber(addressRequestDto.getAddressNumber());
        address.setCity(addressRequestDto.getCity());

//        updatedCustomer.set
        // Passando as informacoes novas do fromrequest para o customerfromdatabase (informacoes velhas)
        return this.addressRepository.save(address);
    }

    public List<Customer> findAll() {

        return this.customerRepository.findAll();
    }

    public Customer findById(Long customerId) {
        Optional<Customer> customerOptional = this.customerRepository.findById(customerId);
        return customerOptional.orElse(null);
    }

    public void inactive(final Long customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        customer.setActive(false);
        this.customerRepository.save(customer);
    }
}
