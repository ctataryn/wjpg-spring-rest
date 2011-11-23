package ca.wfpg.controller;

import ca.wfpg.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@Transactional
public class UsersController {

    @PersistenceContext
    private EntityManager em;

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    /**
     * Get one user by their id
     * @param userId
     * @return
     */
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public @ResponseBody User get(@PathVariable Long userId) {
        logger.info("Fetching user with id: {}", userId);
        User user = em.find(User.class, userId);
        logger.info("Found user: {}", user);
        return user;
    }

    /**
     * Get all users
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<User> getAll() {
        logger.info("Fetching all users");
        List<User> users = em.createQuery("select u from User u", User.class).getResultList();
        logger.info("Found users: {}", users);
        return users;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public @ResponseBody User create(User user) {
        logger.info("Creating new user: {}", user);
        user = em.merge(user);
        logger.info("Created user: {}", user);
        return user;
    }

}
