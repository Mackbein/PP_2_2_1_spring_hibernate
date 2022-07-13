package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByModelAndSeries(String model, int series) {
      Session session = sessionFactory.getCurrentSession();
      String sql = "select * from CARS where model=:model and series=:series";
      NativeQuery query = session.createSQLQuery(sql)
              .setParameter("model", model)
              .setParameter("series", series)
              .addEntity(Car.class);
      Car car = (Car) query.getSingleResult();
      return car.getUser();
   }

}
