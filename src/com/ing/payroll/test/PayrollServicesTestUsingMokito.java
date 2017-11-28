package com.ing.payroll.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.ing.payroll.beans.Associate;
import com.ing.payroll.beans.BankDetails;
import com.ing.payroll.beans.Salary;
import com.ing.payroll.daoservices.PayrollDAOServices;
import com.ing.payroll.exception.AssociateDetailsNotFoundException;
import com.ing.payroll.exception.PayrollServicesDownException;
import com.ing.payroll.services.PayrollServices;
import com.ing.payroll.services.PayrollServicesImpl;



public class PayrollServicesTestUsingMokito {

	private static PayrollServices payrollServices;
	private static PayrollDAOServices mockDaoServices;
	ArrayList<Associate> associatesList = null;
	private Associate associate3 =null;

	@BeforeClass
	public static void setUpPayrollServices() {
		mockDaoServices = Mockito.mock(PayrollDAOServices.class);
		payrollServices = new PayrollServicesImpl(mockDaoServices);
	}

	
	@Before
	public void setUpTestData() throws SQLException {
		
		Associate associate1 = new Associate(1000, 78000, "Satish", "Mahajan", "Training", "Manager", "DTDYF736",
				"satish.mahajan@capgemini.com", new Salary(35000, 1800, 1800),
				new BankDetails(12345, "HDFC", "HDFC0097"));
		
		Associate associate2 =new Associate(1001, 87372, "Ayush", "Mahajan", "Training", "Manager", "YCTCC727",
				"ayush.mahajan@capgemini.com", new Salary(87738, 1800, 1800),
				new BankDetails(12345, "HDFC", "HDFC0097"));
		associatesList = new ArrayList<>();
		associatesList.add(associate1);
		associatesList.add(associate2);
		/*Associate associate3 =new Associate(65440, "Mayur", "Patil", "ADC", "Trainee", "CYYJUUF887",
				"mayur.patil@capgemini.com", new Salary(86222, 1800, 1800),
				new BankDetails(123738, "HDFC", "HDFC0097"));*/
		associate3 = new Associate(120020, "Kumar", "Raj", "Training", "Sr Con", "HDUDUI37", 
				"kumar.raj.@capgemini.com", new Salary(35000, 2000, 2000), new BankDetails(12344, "HSBC", "hsbc293"));
		
		Mockito.when(mockDaoServices.getAssociate(1000)).thenReturn(associate1);
		Mockito.when(mockDaoServices.getAssociate(1001)).thenReturn(associate2);
		Mockito.when(mockDaoServices.getAssociate(1234)).thenReturn(null);
		Mockito.when(mockDaoServices.getAssociates()).thenReturn(associatesList);
		Mockito.when(mockDaoServices.insertAssociate(associate3)).thenReturn(1002);
		
		
	}

	@Test(expected = AssociateDetailsNotFoundException.class)
	public void testGetAssociateDataForInvalidAssociateId()
			throws PayrollServicesDownException, AssociateDetailsNotFoundException {
		payrollServices.getAssociateDetails(1234);
	}

	@Test
	public void testGetAssociateDataForValidAssociateId()
			throws PayrollServicesDownException, AssociateDetailsNotFoundException, SQLException {
		Associate expectedAssociate = new Associate(1000, 78000, "Satish", "Mahajan", "Training", "Manager", "DTDYF736",
				"satish.mahajan@capgemini.com", new Salary(35000, 1800, 1800),
				new BankDetails(12345, "HDFC", "HDFC0097"));
		Associate actualAssociate = payrollServices.getAssociateDetails(1000);
		
		Mockito.verify(mockDaoServices).getAssociate(1000);
		assertEquals(expectedAssociate, actualAssociate);
	}

	@Test
	public void testAcceptAssociateDetailsForValidData() throws PayrollServicesDownException, SQLException {
		int expectedAssociateId = 1002;
		int actualAssociateId = payrollServices.acceptAssociateDetails(associate3.getFirstName(), associate3.getLastName(), associate3.getEmailId(), associate3.getDepartment(), associate3.getDesignation(), associate3.getPancard(), associate3.getYearlyInvestmentUnder80C(), associate3.getSalary().getBasicSalary(), associate3.getSalary().getCompanyPf(), associate3.getSalary().getEpf(), associate3.getBankDetails().getAccountNumber(), associate3.getBankDetails().getBankName(), associate3.getBankDetails().getIfscCode());
		Mockito.verify(mockDaoServices).insertAssociate(associate3);
		assertEquals(expectedAssociateId, actualAssociateId);
	}

	@Test(expected = AssociateDetailsNotFoundException.class)
	public void testCalculateNetSalaryForInvalidAssociateId()
			throws PayrollServicesDownException, AssociateDetailsNotFoundException, SQLException {
		payrollServices.getAssociateDetails(63363);
		Mockito.verify(mockDaoServices).getAssociate(63363);
	}

	@Test
	public void testCalculateNetSalaryForValidAssociateId() {
		fail();
	}

	@Test
	public void testGetAllAssociatesDetails() throws PayrollServicesDownException, SQLException {
		List<Associate> expectedAssociateList = associatesList;
		List<Associate> actualAssociateList = payrollServices.getAllAssociatesDetails();
		Mockito.verify(mockDaoServices).getAssociates();
		assertEquals(expectedAssociateList, actualAssociateList);
	}

	@After
	public void tearDownTestData() {
		
	}

	@AfterClass
	public static void tearDownPayrollServicesq() {
		mockDaoServices = null;
		payrollServices = null;
	}

}
