package mobile.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mobile.project.models.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	public List<Address> findByDetail(String detail);
}
