package base;
import java.util.List;

import models.Estilo;
import models.Instrumento;
import models.dao.GenericDAO;

import org.junit.Test;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends AbstractTest{

	GenericDAO dao = new GenericDAO();
	List<Estilo> estilos;
	List<Instrumento> instrumentos;
	

    @Test
    public void renderTemplate() {
    	
    }
}
