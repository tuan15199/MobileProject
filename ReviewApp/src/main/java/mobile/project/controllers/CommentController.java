package mobile.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mobile.project.dtos.CommentDto;
import mobile.project.models.Comment;
import mobile.project.services.CommentService;

@RestController
public class CommentController {
	
	@Autowired CommentService service;
	
	@GetMapping(value = "/comments")
	public List<CommentDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "comment/shop/{shopId}")
	public List<CommentDto> getCommentByShop(@PathVariable int shopId) {
		return service.getCommentByShop(shopId);
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PostMapping(value = "/comment")
	public CommentDto addComment(@RequestBody CommentDto comment) {
		return service.addComment(comment);
	}
	
}
