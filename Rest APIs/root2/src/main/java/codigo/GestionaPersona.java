package codigo;

import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;

@Path("/persona")
public class GestionaPersona {

	public static Persona persona;
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Persona leer() {
		Persona p = new Persona();
		p.setCasado(false);
		p.setGenero(null);
		p.setNombre("javi");
		p.setId(0);
		return p;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public void guardar(Persona p) {
		persona=p;
	}
}
