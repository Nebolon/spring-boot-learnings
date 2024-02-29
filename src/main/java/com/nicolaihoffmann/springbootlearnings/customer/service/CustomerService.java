package com.nicolaihoffmann.springbootlearnings.customer.service;

import com.nicolaihoffmann.springbootlearnings.customer.dto.CustomerDTO;
import com.nicolaihoffmann.springbootlearnings.customer.entity.Customer;
import com.nicolaihoffmann.springbootlearnings.customer.repository.CustomerRepository;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public List<CustomerDTO> getAllCustomer() {
        return customerRepository.findAll().stream().map(e -> modelMapper.map(e, CustomerDTO.class)).collect(Collectors.toUnmodifiableList());
    }

    public CustomerDTO getCustomerById(UUID externalId) {
        return modelMapper.map(customerRepository.findByExternalId(externalId), CustomerDTO.class);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerRepository.save(convertToEntity(customerDTO)), CustomerDTO.class);
    }

    public CustomerDTO updateCustomer(UUID externalId, CustomerDTO newCustomerDTO) {
        Customer customer = convertToEntity(newCustomerDTO);
        customer.setId(customerRepository.findByExternalId(externalId).getId());
        return modelMapper.map(customerRepository.save(customer), CustomerDTO.class);
    }

    public void deleteCustomer(UUID externalId) {
        customerRepository.deleteByExternalId(externalId);
    }

    private Customer convertToEntity(CustomerDTO newCustomerDTO) {
        modelMapper.addConverter(new ExternalIdConverter());
        return modelMapper.map(newCustomerDTO, Customer.class);
    }

    // Hier können weitere CRM-spezifische Methoden implementiert werden
    public class ExternalIdConverter extends AbstractConverter<UUID, Long> {
        @Override
        protected Long convert(UUID source) {
            if (source == null) {
                return null;
            }
            // Hier kannst du die Logik für die Konvertierung von UUID zu Long implementieren.
            // Zum Beispiel kannst du die most significant bits und least significant bits kombinieren.
            // Hier ist ein einfaches Beispiel:
            return source.getMostSignificantBits() ^ source.getLeastSignificantBits();
        }
    }

}
