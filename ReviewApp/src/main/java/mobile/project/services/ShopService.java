package mobile.project.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mobile.project.dtos.ShopDto;
import mobile.project.exceptions.DataAlreadyExistException;
import mobile.project.exceptions.DataNotFoundException;
import mobile.project.models.Address;
import mobile.project.models.Shop;
import mobile.project.repositories.AddressRepository;
import mobile.project.repositories.ShopRepository;

@Service
@Transactional
public class ShopService {
	@Autowired
	private ShopRepository repo;
	@Autowired
	private AddressRepository addressRepo;

	public ShopDto convertShopToDto(Shop shop) {
		ShopDto result = new ShopDto(shop.getId(), shop.getName(), shop.getType(), shop.getOpenTime(),
				shop.getCloseTime(), shop.getMinPrice(), shop.getMaxPrice(), shop.getStar(), shop.getAddress().getDetail(),
				shop.getAddress().getDistrict(), shop.getAddress().getCity());
		return result;
	}

	// get all shops
	public List<ShopDto> getAll() {
		List<Shop> shops = repo.findAll();
		List<ShopDto> result = new ArrayList<>();
		for(Shop e: shops) {
			result.add(convertShopToDto(e));
		}
		return result;
	}

	// get shop by id
	public ShopDto getById(int id) {
		Shop shop = repo.findById(id).orElseThrow(() -> new DataNotFoundException("shop"));
		return convertShopToDto(shop);
	}

	// get shop by type
	public Shop getShopByType(int type) {
		return repo.getShopByType(type);
	}

	public Address isAddressExist(Address adr) {
		if (addressRepo.findByDetail(adr.getDetail()) != null) {
			List<Address> listAdr = addressRepo.findByDetail(adr.getDetail());
			for (Address address : listAdr) {
				if (address.getDistrict().equalsIgnoreCase(adr.getDistrict())
						&& address.getCity().equalsIgnoreCase(adr.getCity()))
					return address;
			}
		}
		return null;
	}

	public boolean isShopExisted(Address address) {
		for (Shop shop : repo.findAll()) {
			if (isAddressExist(address) != null) {
				if(shop.getAddress() == isAddressExist(address))
					return true;
			}
		}
		return false;
	}

	// create shop
	public Shop createShop(ShopDto shopDto) {
		Shop shop = new Shop();
		Address address = new Address(shopDto.getAddressDetail(), shopDto.getAddressDistrict(),
				shopDto.getAddressCity());

		if (!isShopExisted(address)) {
			shop.setName(shopDto.getName());
			shop.setType(shopDto.getType());
			shop.setOpenTime(shopDto.getOpenTime());
			shop.setCloseTime(shopDto.getCloseTime());
			shop.setMinPrice(shopDto.getMinPrice());
			shop.setMaxPrice(shopDto.getMaxPrice());
			shop.setStar(0);

			if (isAddressExist(address) != null) {
				shop.setAddress(isAddressExist(address));
			} else {
				addressRepo.save(address);
				shop.setAddress(address);
			}
			return repo.save(shop);
		} else
			throw new DataAlreadyExistException("shop");
	}

	// edit shop
	public Shop updateShop(int id, ShopDto shopDto) {
		Shop shop = repo.findById(id).orElseThrow(() -> new DataNotFoundException("shop"));
		shop.setName(shopDto.getName());
		shop.setType(shopDto.getType());
		shop.setOpenTime(shopDto.getOpenTime());
		shop.setCloseTime(shopDto.getCloseTime());
		shop.setMinPrice(shopDto.getMinPrice());
		shop.setMaxPrice(shopDto.getMaxPrice());

		Address address = new Address(shopDto.getAddressDetail(), shopDto.getAddressDistrict(),
				shopDto.getAddressCity());
		if (isAddressExist(address) != null) {
			shop.setAddress(isAddressExist(address));
		} else {
			addressRepo.save(address);
			shop.setAddress(address);
		}
		return repo.save(shop);
	}

	// delete shop
	public void delete(int id) {
		repo.deleteById(id);
	}
}
