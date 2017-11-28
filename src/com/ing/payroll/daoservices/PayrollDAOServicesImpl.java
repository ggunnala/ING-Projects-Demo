package com.ing.payroll.daoservices;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import com.ing.payroll.beans.Associate;
import com.ing.payroll.exception.PayrollServicesDownException;
import com.ing.payroll.provider.ServiceProvider;

public class PayrollDAOServicesImpl implements PayrollDAOServices {
	
	public PayrollDAOServicesImpl() throws PayrollServicesDownException{
		conn = ServiceProvider.getDBConnection();
	}
	
	public  static HashMap<Integer, Associate> associates = new HashMap<>();
	public static int ASSOCIATE_ID_COUNTER =1000;
	private static Connection conn;

	@Override
	public int insertAssociate(Associate associate) throws SQLException {
		try {
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement("insert into associate (yearlyInvestmentUnder8oC, firstName, lastName , department, designation , pancard , emailId ) values(?,?,?,?,?,?,?)") ;
		pstmt.setInt(1,  associate.getYearlyInvestmentUnder80C());
		pstmt.setString(2,associate.getFirstName());
		pstmt.setString(3,associate.getLastName());
		pstmt.setString(4,associate.getDepartment());
		pstmt.setString(5,associate.getDesignation());
		pstmt.setString(6,associate.getPancard());
		pstmt.setString(7,associate.getEmailId());
		pstmt.execute();
		
		ResultSet rs = conn.prepareStatement("select max(associateId) from associate").executeQuery();
		rs.next();
		int associateId = rs.getInt(1);
		
		pstmt = conn.prepareStatement("insert into salary(associateId,basicSalary,epf,companyPf) values(?,?,?,?)");
		pstmt.setInt(1,associateId);
		pstmt.setInt(2,associate.getSalary().getBasicSalary());
		pstmt.setInt(3,associate.getSalary().getEpf());
		pstmt.setInt(4,associate.getSalary().getCompanyPf());
		pstmt.executeUpdate();
		
		
		pstmt = conn.prepareStatement("insert into bankdetails(associateId,accountNo,bankName,ifscCode) values(?,?,?,?)");
		pstmt.setInt(1,associateId);
		pstmt.setInt(2,associate.getBankDetails().getAccountNumber());
		pstmt.setString(3,associate.getBankDetails().getBankName());
		pstmt.setString(4,associate.getBankDetails().getIfscCode());
		pstmt.executeUpdate();
		conn.commit();
		
		return associateId;
		}
		catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			throw e;
			
		}
		
	}

	@Override
	public boolean updateAssociate(Associate associate) throws SQLException {
	
		return false;
	}

	@Override
	public boolean deleteAssciate(int associateId) throws SQLException {
		
		return false;
	}

	@Override
	public Associate getAssociate(int associateId) throws SQLException {
		
		return null;
	}

	@Override
	public List<Associate> getAssociates() throws SQLException {
		
		return null;
	}
}
