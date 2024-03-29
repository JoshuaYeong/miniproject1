package vttp2022.miniproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import vttp2022.miniproject.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public boolean verifyUsername(String username, String password) {

        return 1 == userRepo.countUsersByUsername(username, password);
    }
    
    public boolean createNewUser(String username, String password) {

        try{
            userRepo.insertNewUser(username, password);
            return true;
        } catch (DuplicateKeyException dke){
            return false;
        }
    }

    public boolean deleteUser(String username) {
        
        return userRepo.deleteUser(username);
    }
}
