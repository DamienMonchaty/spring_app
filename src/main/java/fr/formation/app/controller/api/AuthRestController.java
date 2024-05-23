package fr.formation.app.controller.api;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.app.dao.RoleRepository;
import fr.formation.app.dao.UserRepository;
import fr.formation.app.dto.SignInDTO;
import fr.formation.app.dto.SignUpDTO;
import fr.formation.app.models.Role;
import fr.formation.app.models.RoleName;
import fr.formation.app.models.User;
import fr.formation.app.response.JwtResponse;
import fr.formation.app.security.jwt.JwtProvider;
import jakarta.validation.Valid;

// Ce contrôleur fournit des API pour les actions d'enregistrement et de connexion

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
	@Autowired
    private AuthenticationManager authenticationManager;
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtProvider jwtProvider;
    
    // – /api/auth/signin
  	//
  	//	- authentifier via { nom d'utilisateur, mot de passe }
  	//	- mettre à jour le SecurityContext à l'aide de l'objet Authentication
  	//	- générer le JWT
  	//	- obtenir les détails de l'utilisateur
  	//	- retourner dans la reponse le jwt
	@PostMapping("/signin")
	public ResponseEntity<?> loginUser(@Valid @RequestBody SignInDTO signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, user.getUsername(), user.getAuthorities()));
	}
	
	// – /api/auth/signup
	//
	//	- vérifier le nom d'utilisateur / e-mail existant A faire
	//	- créer un nouvel utilisateur (avec ROLE_USER si le rôle n'est pas spécifié)
	//	- enregistrer l'utilisateur dans la base de données à l'aide de UserRepository
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDTO signUpRequest) {
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        
        Set<Role> roles = new HashSet<>();
        
        if (strRoles == null) {
            Role userRole = roleRepository.findByTitle(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause : User role not find"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByTitle(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause : User role not find"));
                        roles.add(adminRole);
                    default:
                        Role userRole = roleRepository.findByTitle(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause : User role not find"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>("User Registered successfully", HttpStatus.OK);
	}
}