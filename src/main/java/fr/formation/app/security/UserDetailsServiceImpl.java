package fr.formation.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.formation.app.dao.UserRepository;
import fr.formation.app.models.User;

// Cette classe agit essentiellement comme un service qui interagit avec la source de données 
// pour charger les détails de l'utilisateur lorsqu'ils sont demandés par Spring Security

// Elle est utilisée conjointement avec UserDetailsImpl pour fournir à Spring Security 
// les détails de l'utilisateur nécessaires à l'authentification et à l'autorisation

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	// Chargée de charger les détails de l'utilisateur à partir de la source de données en utilisant le nom d'utilisateur fourni
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user  = userRepository.findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("No userName " + username);
		} else {
			return new UserDetailsImpl(user);
		}
	}
}