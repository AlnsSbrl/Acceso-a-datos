package codigo;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/personas")
public class Personas {

    public static ArrayList<Persona> personas = new ArrayList<>();

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void guardar(Persona persona) {
        personas.add(persona);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Persona> listar() {
        Persona p = new Persona();
        p.setCasado(true);
        p.setGenero("sex");
        p.setNombre("pedr");
        p.setId(12);
        personas.add(p);
        return personas;
    }

    @GET
    @Path("/a")
	@Produces(MediaType.TEXT_PLAIN)
	public String textHola() {
		return "Hola Rest Soy un texto";
	}

    @GET
    @Path("/personas/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ver(@PathParam("nombre") String nombre) {

        for (Persona persona : personas) {
            if (persona.getNombre().equals(nombre)) {
                return Response.ok(persona).build();
            }
        }
        return Response.status(Status.BAD_REQUEST).entity("error").type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/buscar/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Ver(@QueryParam("nombre") String nombre){
        ArrayList<Persona> personasEncontradas= new ArrayList<>();
        for(Persona p:personas){
            if(p.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                personasEncontradas.add(p);
            }
        }
        if(personasEncontradas.size()>0){
            return Response.ok(personasEncontradas).build();
        }else{
            return Response.status(Status.NOT_FOUND).entity("error").type(MediaType.APPLICATION_JSON).build();
        }
    }

    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Persona insertaPersona(@FormParam("nombre") String nombre,@FormParam("genero") String genero,@FormParam("casado") boolean casado,@FormParam("id") int id){
        Persona perso = new Persona();
        perso.setNombre(nombre);
        perso.setGenero(genero);
        perso.setCasado(casado);
        perso.setId(id);
        personas.add(perso);
        System.out.println("añadido");
        return perso;
    }
    
}
