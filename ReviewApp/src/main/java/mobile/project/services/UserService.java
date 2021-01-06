package mobile.project.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mobile.project.dtos.ChangePasswordDto;
import mobile.project.dtos.LoginReturnDto;
import mobile.project.dtos.LoginViewModel;
import mobile.project.dtos.Token;
import mobile.project.dtos.UserReturnDto;
import mobile.project.dtos.UserSignUp;
import mobile.project.exceptions.CustomException;
import mobile.project.exceptions.DataNotFoundException;
import mobile.project.models.Address;
import mobile.project.models.Roles;
import mobile.project.models.User;
import mobile.project.repositories.AddressRepository;
import mobile.project.repositories.UserRepository;
import mobile.project.springSecurity.JwtTokenProvider;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired ShopService shopService;

	@Autowired
	AddressRepository addressRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public LoginReturnDto signin(LoginViewModel loginInfo) {
		LoginReturnDto response = new LoginReturnDto();

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword()));

			Token token = new Token();
			token.setAccessToken("Bearer " + jwtTokenProvider.createToken(loginInfo.getUsername(),
					userRepository.findByUserName(loginInfo.getUsername()).getRoles()));

			User user = userRepository.findByUserName(loginInfo.getUsername());
			token.setUserId(user.getId());
			token.setUserName(user.getUserName());

			response.setData(token);
			response.setErrorCode(0);

		} catch (AuthenticationException e) {
//			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
			response.setData(null);
			response.setErrorCode(1);
			response.setErrorMessage("Invalid username/password supplied");
		}
		return response;
	}

	public Token signup(UserSignUp signUpInfo, List<Roles> roles) {
		User user = new User();

		if (!userRepository.existsByUserName(signUpInfo.getUsername())) {
			user.setUserName(signUpInfo.getUsername());
			user.setPassword(passwordEncoder.encode(signUpInfo.getPassword()));
			user.setRoles(roles);
			
			Address adr = new Address(signUpInfo.getAddressDetail(), signUpInfo.getAddressDistrict(),
					signUpInfo.getAddressCity());
			
			if(shopService.isAddressExist(adr) != null) {
				user.setAddress(adr);
			} else {
				addressRepo.save(adr);
				user.setAddress(adr);
			}

			userRepository.save(user);

			Token token = new Token();
			token.setAccessToken("Bearer " + jwtTokenProvider.createToken(user.getUserName(), user.getRoles()));
			token.setUserId(user.getId());
			token.setUserName(user.getUserName());
			return token;
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public void delete(String username) {
		if(userRepository.findByUserName(username) != null)
			userRepository.deleteByUserName(username);
	}

	public List<UserReturnDto> getAllUsers() {
		List<UserReturnDto> result = new ArrayList<>();
		List<User> users = userRepository.findAll();

		for (User u : users) {
			if(!u.getRoles().get(0).toString().equals("ROLE_ADMIN")) {
				UserReturnDto user = new UserReturnDto(u.getId(), u.getUserName(), u.getAddress().getDetail(),
						u.getAddress().getDistrict(), u.getAddress().getCity(), u.getRoles());
				result.add(user);
			}
		}
		return result;
	}

	public User search(String username) {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user;
	}

	public LoginReturnDto changePassword(Integer id, ChangePasswordDto newPassword) {
		User user = new User();

		if (id != null) {
			user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user"));
		}

		user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
		userRepository.save(user);
		
		LoginReturnDto result = new LoginReturnDto();
		result.setData(null);
		result.setErrorCode(0);
		result.setErrorMessage("");
		return result;
	}

	public UserReturnDto getUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user"));
		UserReturnDto result = new UserReturnDto(user.getId(), user.getUserName(), user.getAddress().getDetail(),
				user.getAddress().getDistrict(), user.getAddress().getCity(), user.getRoles());

		return result;
	}

	public User whoami(HttpServletRequest req) {
		return userRepository.findByUserName(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, userRepository.findByUserName(username).getRoles());
	}

}
