package restpackage;

import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

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
        return personas;
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
        return perso;
        
    }
}
