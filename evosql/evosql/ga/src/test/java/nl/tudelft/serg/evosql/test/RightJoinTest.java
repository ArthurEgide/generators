package nl.tudelft.serg.evosql.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RightJoinTest extends TestBase {
	
	/**
	 * Right join with WHERE on the first table to be a value, which is not possible in our GA
	 * However this could accidentally be solved, so the test is switched off.
	 */
	/*
	@Test
	public void test1() {
		assertFalse(testExecutePath("SELECT * FROM Products t1 RIGHT JOIN PRODUCT_DETAIL t2 ON t1.ID = t2.ID WHERE t1.Price > 10"));
	}
	*/
	
	/**
	 * Right join with WHERE on the first table to be null
	 */
	@Test
	public void test2() {
		assertTrue(testExecutePath("SELECT * FROM PRODUCTS T1 RIGHT JOIN PRODUCT_DETAIL T2 ON T1.ID = T2.ID WHERE T1.PRICE IS NULL"));
	}
	
	/**
	 * Right join with WHERE on the joined table
	 */
	@Test
	public void test3() {
		assertTrue(testExecutePath("SELECT * FROM PRODUCTS T1 RIGHT JOIN PRODUCT_DETAIL T2 ON T1.ID = T2.ID WHERE T2.TYPE = 10"));
	}
	
	/**
	 * Right join with WHERE on the joined table
	 */
	@Test
	public void test4() {
		assertTrue(testExecutePath("SELECT * FROM PRODUCTS T1 RIGHT JOIN PRODUCT_DETAIL T3 ON T1.ID = T3.ID - 1 RIGHT JOIN PRODUCT_DETAIL T2 ON T1.ID = T2.ID WHERE T2.TYPE = 10"));
	}
	
	/**
	 * Right join after LEFT JOIN
	 */
	@Test
	public void test5() {
		assertTrue(testExecutePath("SELECT * FROM PRODUCTS T1 LEFT JOIN PRODUCT_DETAIL T3 ON T1.ID = T3.ID RIGHT JOIN PRODUCT_DETAIL T2 ON T1.ID = T2.ID WHERE T2.TYPE = 10"));
	}
	
	/**
	 * INNER JOIN after RIGHT JOIN
	 */
	@Test
	public void test6() {
		assertTrue(testExecutePath("SELECT * FROM PRODUCTS T1 RIGHT JOIN PRODUCT_DETAIL T2 ON T1.ID = T2.ID INNER JOIN EXTRA_PRODUCT T3 ON T2.TYPE = T3.ID WHERE T1.ID IS NULL AND T2.ID IS NULL"));
	}
	
	@Test
	public void testRightJoinWithNullsBasedOnEspocrmQ10P6() {
		String sql = "SELECT * FROM STRINGS RIGHT JOIN STRINGS2 ON STRINGS.C1 = STRINGS2.C1 AND STRINGS2.C2 = 'TESTPORTALID' AND STRINGS2.C3 = '1' WHERE ( STRINGS.C1 IS NULL ) AND ( STRINGS2.C1 IS NULL ) AND ( STRINGS2.C2 IS NULL ) AND ( STRINGS2.C3 IS NULL ) ";
		
		assertTrue(testExecutePath(sql));
	}
	
	@Test
	public void testRightJoinWithNulls2BasedOnEspocrmQ21P15() {
		
		createTable("PORTAL", "CREATE TABLE PORTAL ("+
		        "  ID VARCHAR(24),"+
		        "  NAME VARCHAR(100) ,"+
		        "  DELETED INT,"+
		        "  CUSTOM_ID VARCHAR(36) ,"+
		        "  IS_ACTIVE INT,"+
		        "  TAB_LIST VARCHAR(255),"+
		        "  QUICK_CREATE_LIST VARCHAR(255),"+
		        "  THEME VARCHAR(255) ,"+
		        "  LANGUAGE VARCHAR(255) ,"+
		        "  TIME_ZONE VARCHAR(255) ,"+
		        "  DATE_FORMAT VARCHAR(255) ,"+
		        "  TIME_FORMAT VARCHAR(255) ,"+
		        "  WEEK_START INT,"+
		        "  DEFAULT_CURRENCY VARCHAR(255) ,"+
		        "  DASHBOARD_LAYOUT VARCHAR(255),"+
		        "  DASHLETS_OPTIONS VARCHAR(255),"+
		        "  CUSTOM_URL VARCHAR(255),"+
		        "  MODIFIED_AT DATETIME ,"+
		        "  CREATED_AT DATETIME ,"+
		        "  MODIFIED_BY_ID VARCHAR(24) ,"+
		        "  CREATED_BY_ID VARCHAR(24) ,"+
		        "  LOGO_ID VARCHAR(24) ,"+
		        "  COMPANY_LOGO_ID VARCHAR(24) "+
		        ")");
		
		createTable("USER", "CREATE TABLE USER ("+
				"  ID VARCHAR(24) ,"+
				"  DELETED INT ,"+
				"  IS_ADMIN INT  ,"+
				"  USER_NAME VARCHAR(50) ,"+
				"  PASSWORD VARCHAR(150) ,"+
				"  SALUTATION_NAME VARCHAR(255) ,"+
				"  FIRST_NAME VARCHAR(100) DEFAULT '',"+
				"  LAST_NAME VARCHAR(100) DEFAULT '',"+
				"  IS_ACTIVE INT  ,"+
				"  IS_PORTAL_USER INT  ,"+
				"  IS_SUPER_ADMIN INT  ,"+
				"  TITLE VARCHAR(100) ,"+
				"  GENDER VARCHAR(255) DEFAULT '',"+
				"  CREATED_AT DATETIME ,"+
				"  DEFAULT_TEAM_ID VARCHAR(24) ,"+
				"  CONTACT_ID VARCHAR(24) ,"+
				"  AVATAR_ID VARCHAR(24) ,"+
				")");
		
		createTable("ATTACHMENT", "CREATE TABLE ATTACHMENT ("+
				"  ID VARCHAR(24) ,"+
				"  NAME VARCHAR(255) ,"+
				"  DELETED INT ,"+
				"  TYPE VARCHAR(100) ,"+
				"  SIZE INT ,"+
				"  SOURCE_ID VARCHAR(36) ,"+
				"  CREATED_AT DATETIME ,"+
				"  ROLE VARCHAR(36) ,"+
				"  STORAGE VARCHAR(24) ,"+
				"  GLOBAL INT  ,"+
				"  PARENT_ID VARCHAR(24) ,"+
				"  PARENT_TYPE VARCHAR(100) ,"+
				"  RELATED_ID VARCHAR(24) ,"+
				"  RELATED_TYPE VARCHAR(100) ,"+
				"  CREATED_BY_ID VARCHAR(24) ,"+
				")");
		
		String sql = "SELECT PORTAL.ID AS ID, PORTAL.NAME AS NAME, PORTAL.DELETED AS DELETED, PORTAL.CUSTOM_ID AS CUSTOMID, PORTAL.IS_ACTIVE AS ISACTIVE, PORTAL.TAB_LIST AS TABLIST, PORTAL.QUICK_CREATE_LIST AS QUICKCREATELIST, PORTAL.THEME AS THEME, PORTAL.LANGUAGE AS LANGUAGE, PORTAL.TIME_ZONE AS TIMEZONE, PORTAL.DATE_FORMAT AS DATEFORMAT, PORTAL.TIME_FORMAT AS TIMEFORMAT, PORTAL.WEEK_START AS WEEKSTART, PORTAL.DEFAULT_CURRENCY AS DEFAULTCURRENCY, PORTAL.DASHBOARD_LAYOUT AS DASHBOARDLAYOUT, PORTAL.DASHLETS_OPTIONS AS DASHLETSOPTIONS, PORTAL.CUSTOM_URL AS CUSTOMURL, PORTAL.MODIFIED_AT AS MODIFIEDAT, PORTAL.CREATED_AT AS CREATEDAT, PORTAL.MODIFIED_BY_ID AS MODIFIEDBYID, TRIM(CONCAT(MODIFIEDBY.FIRST_NAME, ' ', MODIFIEDBY.LAST_NAME)) AS MODIFIEDBYNAME, PORTAL.CREATED_BY_ID AS CREATEDBYID, TRIM(CONCAT(CREATEDBY.FIRST_NAME, ' ', CREATEDBY.LAST_NAME)) AS CREATEDBYNAME, LOGO.NAME AS LOGONAME, PORTAL.LOGO_ID AS LOGOID, COMPANYLOGO.NAME AS COMPANYLOGONAME, PORTAL.COMPANY_LOGO_ID AS COMPANYLOGOID "
				+ "FROM PORTAL INNER JOIN "
				+ "USER AS MODIFIEDBY ON PORTAL.MODIFIED_BY_ID = MODIFIEDBY.ID "
				+ "INNER JOIN USER AS CREATEDBY ON PORTAL.CREATED_BY_ID = CREATEDBY.ID "
				+ "INNER JOIN ATTACHMENT AS LOGO ON PORTAL.LOGO_ID = LOGO.ID "
				+ "LEFT JOIN ATTACHMENT AS COMPANYLOGO ON PORTAL.COMPANY_LOGO_ID = COMPANYLOGO.ID "
				+ "WHERE ( "
				+ "( COMPANYLOGO.ID IS NULL ) AND "
				+ "( PORTAL.COMPANY_LOGO_ID IS NULL ) "
				+ ") AND "
				+ "( PORTAL.ID = 'testPortalId' AND PORTAL.DELETED = '0' )";
		
		assertTrue(testExecutePath(sql));
	}

}