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
    public boolean saveShow(String username, Show show) {

        boolean exists = userRepo.selectUserByUsername(username);
        if (exists == false) {
            throw new IllegalArgumentException("Username not found");
        }

        boolean exist = showRepo.selectShowByUsername(username);
        if (exist == true) {
            throw new IllegalArgumentException("Show already exist");
        }

        boolean success = showRepo.insertShow(username, show);
        if (success == false) {
            throw new IllegalArgumentException("Insert show failed");
        }

        return true;
    }

    public List<Show> getAllShowsByUsername(String username) {

        return showRepo.selectAllShowsByUsername(username);
    }

    @Transactional
    public boolean deleteShow(String username, Integer titleId) { 

        boolean exists = userRepo.selectUserByUsername(username);
        if (exists == false) {
            throw new IllegalArgumentException("Username not found");
        }

        boolean remove = showRepo.deleteShow(username, titleId);
        if (remove == false) {
            throw new IllegalArgumentException("Delete show not successful");
        }

        return true;
    }
    
}
