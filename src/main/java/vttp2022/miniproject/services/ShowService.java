package vttp2022.miniproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.miniproject.models.Show;
import vttp2022.miniproject.repositories.ShowRepository;
import vttp2022.miniproject.repositories.UserRepository;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public boolean saveShow(String username, String password, Show show) {
        
        Integer exists = userRepo.countUsersByUsername(username, password);
        if (exists == 0) {
            throw new IllegalArgumentException();
        }

        boolean success = showRepo.insertShow(username, show);
        if (success == false) {
            throw new IllegalArgumentException();
        }

        return true;
    }

    public List<Show> getShowsByUsername(String username) {
        return showRepo.selectAllShowsByUsername(username);
    }
    
}
