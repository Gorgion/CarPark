/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.rest;

import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.UserService;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Tomáš Vašíček 
 * REST API for user
 */
@Path("users")
public class UserRest {

    @Autowired
    private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UserDto user) {
        userService.add(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        try {
            UserDto userDto = userService.get(id);
            return Response.status(Response.Status.OK).entity(userDto).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") Long id, UserDto user) {
        try {
            UserDto persistedUser = userService.get(id);
            user.setId(persistedUser.getId());
            userService.edit(user);
            return Response.status(Response.Status.OK).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        try {
            UserDto user = userService.get(id);

            userService.delete(user);
            return Response.status(Response.Status.OK).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<UserDto> userDtoList = userService.getAll();
        return Response.status(Response.Status.OK).entity(userDtoList).build();
    }

    @GET @Path("withRent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWithRent() {
        List<UserDto> userDtoList = userService.getAllWithRent();
        return Response.status(Response.Status.OK).entity(userDtoList).build();
    }

    @GET @Path("withoutRent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWithoutRent() {
        List<UserDto> userDtoList = userService.getAllWithoutRent();
        return Response.status(Response.Status.OK).entity(userDtoList).build();
    }
}
