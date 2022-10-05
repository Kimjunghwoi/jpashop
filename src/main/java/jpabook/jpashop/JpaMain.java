package jpabook.jpashop;

import jpabook.jpashop.domain.test.Child;
import jpabook.jpashop.domain.test.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            //... parent 위주로 짜면서 persist를 시키다보니 귀찮음.. 이럴때 CASCADE를 사용
//            사용 전
//            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);
            /*===============================================================*/
//            사용 후
            em.persist(parent);
            /*===============================================================*/
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }



    }

}
