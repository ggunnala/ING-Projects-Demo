package com.ing.payroll.services;
import java.sql.SQLException;
import java.util.List;

import com.ing.payroll.beans.Associate;
import com.ing.payroll.beans.BankDetails;
import com.ing.payroll.beans.Salary;
import com.ing.payroll.daoservices.PayrollDAOServices;
import com.ing.payroll.daoservices.PayrollDAOServicesImpl;
import com.ing.payroll.exception.AssociateDetailsNotFoundException;
import com.ing.payroll.exception.PayrollServicesDownException;
public class PayrollServicesImpl implements PayrollServices{
	
	private PayrollDAOServices payrollDAOServices ;
	
	public PayrollServicesImpl() throws PayrollServicesDownException {
		payrollDAOServices = new PayrollDAOServicesImpl();
	
	}
	
	public PayrollServicesImpl(PayrollDAOServices payrollDAOServices) {
		this.payrollDAOServices=payrollDAOServices;
	}
	
	@Override
	public int acceptAssociateDetails(String firstName, String lastName, String emailId, String department,
			String designation, String pancard, int yearlyInvestmentUnder80C, int basicSalary, int companyPf, int epf,
			int accountNumber, String bankName, String ifscCode) throws PayrollServicesDownException {
		Associate associate = new Associate(yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId, new Salary(basicSalary, epf, companyPf), new BankDetails(accountNumber, bankName, ifscCode));
		try {
			return payrollDAOServices.insertAssociate(associate);
		} catch (SQLException e) {
			throw new PayrollServicesDownException("PayrollServices are down, try again later",e);
		}
	}

	@Override
	public int calculateNetSalary(int associateId)
			throws AssociateDetailsNotFoundException, PayrollServicesDownException {
		return 0;
	}

	@Override
	public Associate getAssociateDetails(int associateId)
			throws PayrollServicesDownException, AssociateDetailsNotFoundException {
		try {
			Associate associate =payrollDAOServices.getAssociate(associateId);
			if(associate==null) throw new AssociateDetailsNotFoundException("Associate details with Id "+associateId+" not found");
			return associate;
		} catch (SQLException e) {
			throw new PayrollServicesDownException("PayrollServices are down, try again later",e);
		}
	}

	@Override
	public List<Associate> getAllAssociatesDetails() throws PayrollServicesDownException {
		try {
			return  payrollDAOServices.getAssociates();
		} catch (SQLException e) {
			throw new PayrollServicesDownException("PayrollServices are down, try again later",e);
		}
	}

	@Override
	public boolean removeAssociate(int associateId) throws PayrollServicesDownException {
		
		return false;
	}
	
	
}
