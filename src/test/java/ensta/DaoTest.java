package ensta;

import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;

import java.util.List;

public class DaoTest {
    public static void main(String[] args){
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        try{
            int id = membreDao.create("mahdi","marwen","palaiseau","az@emai.com"
                    ,"24288486");
            System.out.println("+"+id);
            List<Membre> l = membreDao.getList();
            System.out.println(l);
            int cnt = membreDao.count();
            System.out.println(cnt);
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            cnt = empruntDao.count();
            System.out.println(cnt);
            List<Emprunt> empruntList = empruntDao.getList();
            System.out.println(empruntList.size());
            Emprunt emprunt = empruntDao.getById(1);
            System.out.println(emprunt);
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            livreDao.delete(8);
        }catch (DaoException e){
            System.out.println("error");
        }
    }
}
