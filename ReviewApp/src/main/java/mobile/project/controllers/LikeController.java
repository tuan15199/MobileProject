package mobile.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mobile.project.dtos.LikeDto;
import mobile.project.services.LikeService;

@RestController
public class LikeController {
	@Autowired
	private LikeService service;
	
	@PostMapping(value = "/like/{userId}")
	public LikeDto addLike(@RequestBody LikeDto likeDto, @PathVariable int userId) {
		return service.addLike(likeDto, userId);
	}
	
	@DeleteMapping(value = "like/{likeId}/delete")
	public void unlike(@PathVariable int likeId) {
		service.Unlike(likeId);
	}
}
