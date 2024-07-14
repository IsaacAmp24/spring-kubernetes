package org.amp.springcloud.msvc.users.application.internal.servicesImpl;

import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.amp.springcloud.msvc.users.domain.services.UserService;
import org.amp.springcloud.msvc.users.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // queries
    @Override
    @Transactional(readOnly = true) // read-only transaction because we are only reading data
    public List<Users> findAll() {
        return (List<Users>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true) // read-only transaction because we are only reading data
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    // commands
    @Override
    @Transactional
    public Users save(Users user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
