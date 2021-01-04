package mobile.project.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import mobile.project.dtos.ShopDto;
import mobile.project.models.Shop;
import mobile.project.services.FileStorageService;
import mobile.project.services.ShopService;

@RestController
public class ShopController {
	@Autowired
	ShopService service;
	
	@Autowired
	private FileStorageService fileStorageService;

	// get all shops
	@GetMapping(value = "/shops")
	public List<ShopDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        	System.out.println(ex);
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);	
    }

	// get shop by id
	@GetMapping(value = "/shops/{id}")
	public ShopDto getById(@PathVariable int id) {
		return service.getById(id);
	}

	// get shop by type
	@GetMapping(value = "shops/type/{type}")
	public Shop getShopByType(@PathVariable int type) {
		return service.getShopByType(type);
	}

	// create shop
	@PostMapping(value = "shops")
	public Shop createShop(@RequestParam("files") MultipartFile[] files, @RequestParam("shop") String shopDtoStr)
			throws JsonMappingException, JsonProcessingException {
		return service.createShop(shopDtoStr, files);
	}

	// update shop
	@PutMapping(value = "shops/{id}")
	public Shop updateShop(@PathVariable int id, @RequestParam("files") MultipartFile[] files, @RequestParam("shop") String shopDtoStr) throws JsonMappingException, JsonProcessingException {
		return service.updateShop(id, shopDtoStr, files);
	}

	// delete shop
	@DeleteMapping(value = "shops/{id}")
	public void deleteShop(@PathVariable int id) {
		service.delete(id);
	}
	
}
