/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;


import cz.muni.fi.pa165.carpark.dao.RentalDao;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Tomáš Vašíček
 */
@RunWith(MockitoJUnitRunner.class)
public class RentalServiceImplTest {
    
    @InjectMocks
    private RentalService rentalService = new RentalServiceImpl();

    @Mock
    private RentalDao mockedRentalDao;
    
    
}
