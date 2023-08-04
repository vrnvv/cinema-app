package cinema.dao.impl;

import cinema.model.Movie;
import cinema.dao.AbstractDao;
import cinema.dao.MovieDao;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDaoImpl extends AbstractDao<Movie> implements MovieDao {
    public MovieDaoImpl(SessionFactory factory) {
        super(factory, Movie.class);
    }
}
