package mobile.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mobile.project.dtos.CommentDto;
import mobile.project.services.CommentService;

@RestController
public class CommentController {
	
	@Autowired CommentService service;
	
	@PostMapping(value = "/comment")
	public CommentDto addComment(@RequestBody CommentDto comment) {
		return service.addComment(comment);
	}
	
}
