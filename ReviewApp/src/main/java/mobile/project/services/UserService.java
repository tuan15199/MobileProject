package mobile.project.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mobile.project.dtos.LoginViewModel;
import mobile.project.dtos.Token;
import mobile.project.dtos.UserSignUp;
import mobile.project.exceptions.CustomException;
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
	
	@Autowired AddressRepository addressRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Token signin(LoginViewModel loginInfo) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword()));
			Token token = new Token();
			token.setAccessToken("Bearer " + jwtTokenProvider.createToken(loginInfo.getUsername(),
					userRepository.findByUserName(loginInfo.getUsername()).getRoles()));
			
			User user = userRepository.findByUserName(loginInfo.getUsername());
			token.setUserId(user.getId());
			token.setUserName(user.getUserName());
			return token;
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public Token signup(UserSignUp signUpInfo, List<Roles> roles) {
		User user = new User();
		
		if (!userRepository.existsByUserName(signUpInfo.getUsername())) {
			user.setUserName(signUpInfo.getUsername());
			user.setPassword(passwordEncoder.encode(signUpInfo.getPassword()));
			user.setRoles(roles);
			user.setAddress(addressRepo.save(new Address(signUpInfo.getAddressDetail(), signUpInfo.getAddressDistrict(), signUpInfo.getAddressCity())));
			userRepository.save(user);
			
			Token token = new Token();
			token.setAccessToken("Bearer" + jwtTokenProvider.createToken(user.getUserName(), user.getRoles()));
			token.setUserId(user.getId());
			token.setUserName(user.getUserName());
			return token;
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public void delete(String username) {
		userRepository.deleteByUserName(username);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User search(String username) {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user;
	}

	public User whoami(HttpServletRequest req) {
		return userRepository.findByUserName(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, userRepository.findByUserName(username).getRoles());
	}

}
