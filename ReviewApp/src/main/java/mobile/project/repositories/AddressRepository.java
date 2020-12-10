package mobile.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mobile.project.models.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
