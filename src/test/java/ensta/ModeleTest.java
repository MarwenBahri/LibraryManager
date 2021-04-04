package ensta;

import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.SUBSCRIPTION;

import java.time.LocalDate;

public class ModeleTest {
    public static void main(String[] args){
        Membre membre = new Membre(0,"bahri","marwen","91120 Palaiseau","marwen@ensta-paris.fr",
                "0749996982", SUBSCRIPTION.VIP);
        Livre livre = new Livre(0,"Berserk","Anonymous","Idonno");
        Emprunt emprunt = new Emprunt(0,membre,livre,LocalDate.now());
        System.out.println(emprunt);
    }
}
