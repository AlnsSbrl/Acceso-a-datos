package codigo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/a")
public class Prueba {

	
	 @GET
	 
		@Produces(MediaType.TEXT_PLAIN)
		public String textHola() {
			return "Hola Rest Soy un texto";
		}
}
