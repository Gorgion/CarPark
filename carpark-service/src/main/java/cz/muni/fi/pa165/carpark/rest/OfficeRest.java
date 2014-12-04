/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.rest;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.CarService;
import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.UserService;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Tomáš Vašíček REST API for office
 */
@Path("office")
public class OfficeRest {

    @Autowired

    private OfficeService officeService;
    @Autowired

    private CarService carService;

    @Autowired
    private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(OfficeDto office) {
        officeService.addOffice(office);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        try {
            OfficeDto officeDto = officeService.getOffice(id);
            return Response.status(Response.Status.OK).entity(officeDto).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") Long id, OfficeDto office) {
        try {
            OfficeDto persistedOffice = officeService.getOffice(id);
            office.setID(persistedOffice.getID());
            officeService.editOffice(office);
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
            OfficeDto office = officeService.getOffice(id);
            officeService.deleteOffice(office);
            return Response.status(Response.Status.OK).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<OfficeDto> officeDtoList = officeService.getAllOffices();
        return Response.status(Response.Status.OK).entity(officeDtoList).build();
    }

    @GET
    @Path("{id}/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@PathParam("id") Long id) {
        try {
            OfficeDto office = officeService.getOffice(id);
            List<UserDto> userDtoList = officeService.getEmployees(office);
            return Response.status(Response.Status.OK).entity(userDtoList).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("{id/addUser}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserToOffice(@PathParam("id") Long id, UserDto user) {
        try {
            OfficeDto office = officeService.getOffice(id);
            officeService.addEmployeeToOffice(office, user);
            return Response.status(Response.Status.OK).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id/deleteUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserFromOffice(@PathParam("id") Long id, @QueryParam("userId") Long userId) {
        try {
            OfficeDto office = officeService.getOffice(id);
            UserDto user = userService.get(userId);
            officeService.deleteEmployeeFromOffice(office, user);
            return Response.status(Response.Status.OK).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{id}/cars")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars(@PathParam("id") Long id) {
        try {
            OfficeDto office = officeService.getOffice(id);
            List<CarDto> carDtoList = officeService.getOfficeCars(office);
            return Response.status(Response.Status.OK).entity(carDtoList).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("{id/addCar}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCarToOffice(@PathParam("id") Long id, CarDto car) {
        try {
            OfficeDto office = officeService.getOffice(id);
            officeService.addCarToOffice(office, car);
            return Response.status(Response.Status.OK).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id/deleteCar}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCarFromOffice(@PathParam("id") Long id, @QueryParam("carId") Long carId) {
        try {
            OfficeDto office = officeService.getOffice(id);
            CarDto car = carService.getCar(carId);
            officeService.deleteCarFromOffice(office, car);
            return Response.status(Response.Status.OK).build();
        } catch (DataAccessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
