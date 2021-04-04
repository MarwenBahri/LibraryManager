package ensta;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;

import java.util.List;

public class ServiceTest {
    public static void main(String[] args){
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        try{
            int id = membreService.create("mahdi","marwen","palaiseau","az@emai.com"
                    ,"24288486");
            System.out.println("+"+id);
            List<Membre> l = membreService.getList();
            System.out.println(l);
            int cnt = membreService.count();
            System.out.println(cnt);
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            cnt = empruntService.count();
            System.out.println(cnt);
            List<Emprunt> empruntList = empruntService.getList();
            System.out.println(empruntList.size());
            Emprunt emprunt = empruntService.getById(6);
            System.out.println(emprunt);
            LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            livreService.delete(8);
        }catch (ServiceException e){
            System.out.println("error");
        }
    }
}
