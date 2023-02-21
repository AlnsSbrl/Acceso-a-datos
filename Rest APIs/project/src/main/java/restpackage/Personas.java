package restpackage;

import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/personas")
public class Personas {
    
    public static ArrayList<Persona> personas= new ArrayList<>();

    @POST
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void guardar(Persona persona){
        personas.add(persona);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Persona> listar(){
        return personas;
    }

    @GET
    @Path("/personas/{nombre}")
    public Persona ver(@PathParam("nombre") String nombre){
        
        for (Persona persona : personas) {
            if (persona.getNombre().equals(nombre)){
                return persona;
            }
        }
        return null;
    }
}
