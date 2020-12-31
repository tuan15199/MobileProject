package mobile.project.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mobile.project.dtos.ChangePasswordDto;
import mobile.project.dtos.LoginReturnDto;
import mobile.project.dtos.LoginViewModel;
import mobile.project.dtos.Token;
import mobile.project.dtos.UserReturnDto;
import mobile.project.dtos.UserSignUp;
import mobile.project.models.Roles;
import mobile.project.models.User;
import mobile.project.services.UserService;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "/all") 
	public List<UserReturnDto> getAll() {
		return userService.getAllUsers();
	}
	
	@GetMapping(value = "/get/{id}")
	public UserReturnDto getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@PutMapping(value = "/{id}/password/change")
	public void changePassword(@PathVariable Integer id, @RequestBody ChangePasswordDto newPassword) {
		userService.changePassword(id, newPassword);
	}
	
	@PostMapping("/signin")
	@ApiOperation(value = "${UserController.signin}")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public LoginReturnDto login(@RequestBody LoginViewModel user) {
		logger.info("User accessed successfully!");
		return userService.signin(user);
	}

	@PostMapping("/signup")
	@ApiOperation(value = "${UserController.signup}")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use") })
	public Token signup(@ApiParam("Signup User") @RequestBody UserSignUp user) {
		logger.info("User sign up successfully!");
		return userService.signup(modelMapper.map(user, UserSignUp.class), new ArrayList<Roles>(Arrays.asList(Roles.ROLE_CLIENT)));
	}
	
	@DeleteMapping(value = "delete/{username}")
	public void deleteUser(@PathVariable String username) {
		userService.delete(username);
	}

}
