package egain.ws.v12.authoring.fixtures;

import java.util.ArrayList;
import java.util.List;

import com.egain.platform.client.businessclients.LicenseManagerBC;
import com.egain.platform.common.CallerContext;
import com.egain.platform.framework.dataaccess.dataobject.SimpleDataObject;
import com.egain.platform.module.user.User;
import com.egain.platform.module.user.UserConstants;
import com.egain.platform.module.user.dataobject.UserData;

import egain.ws.exception.BadRequestException;

/**
 * This class is used to create users for API testing purposes, it is called through fitnesse scripts.
 * It currently supports user creation with minumum roles and licences required for API testing.
 * @author vsingla
 *
 */
public class CreateUser {
	
	private String userName;
	private String password;
	private int partitionId;
	private long partitionUserId;
	private long deptId;
	private int contentLanguageId;
	private String egainSessionId;
	private String roles;
	private String licences;
	private String installDir;
	
	public static final String AUTHOR = "author";
	public static final String AGENT = "agent";
	public static final long AUTHOR_ROLE_ID = 999;
	public static final long AGENT_ROLE_ID = 995;
	
	public CreateUser(){
		
	}	
	
	/**
	 * 
	 * @param userName - name of the user to be created
	 * @param password - password of the user
	 * @param partitionId - partition id where user is being created
	 * @param partitionUserId - UserId which is creating this user.
	 */
	public CreateUser(String userName,String password, int partitionId, long partitionUserId){
		this.userName = userName;
		this.password = password;
		this.partitionId = partitionId;
		this.partitionUserId = partitionUserId;
	}
	
	/**
	 * This method creates a new user and returns the user Id for that.
	 * @return
	 */
	public long getUserId(){
		CallerContext callerContext = new CallerContext(partitionId, partitionUserId); 
		SimpleDataObject sdo = buildDataObject();
		return create(callerContext,sdo);
	}
	
	public static void main(String args[]){
		
		CreateUser create = new CreateUser("kbagent13","egain123",1,12);
		create.setInstallDir("\\\\v28w14\\egain\\eService");
		create.setDeptId(999);
		create.getUserId();
	}
	
	
	public SimpleDataObject buildDataObject(){
		
		SimpleDataObject simpleDataObject = new SimpleDataObject();
		
		UserData userData = new UserData();
		userData.setUserName(userName);
		userData.setPassword(password);
		userData.setDefaultContentLangId(contentLanguageId);//Default lang=1;
		userData.setHomeDepartmentId(deptId);
		simpleDataObject.setAttribute(UserConstants.USER_DATA, userData);
		
		long[] listAddUserGroup = null; // None by default, added for future ref
		simpleDataObject.setAttribute(UserConstants.ASSIGNABLE_GROUP_IDS, listAddUserGroup);
		
		long[] listAddUserRoles = getUserRoles(); //Agent and Author by default
		simpleDataObject.setAttribute(UserConstants.ASSIGNABLE_ROLE_IDS, listAddUserRoles);
		
		long[] listAddUserSkills = null;//None by default, added for future ref
		simpleDataObject.setAttribute(UserConstants.ASSIGNABLE_SKILL_IDS, listAddUserSkills);
		
		ArrayList proficiencyLevel = new ArrayList();
		proficiencyLevel.add("0"); //Default 0 added
		simpleDataObject.setAttribute(UserConstants.SKILL_PROFICIENCY_LEVEL, proficiencyLevel);
		
		long[] listAddUserActions = null; //None by default, added for future ref
		simpleDataObject.setAttribute(UserConstants.ASSIGNABLE_PERMISSION_IDS, listAddUserActions);
		
		return simpleDataObject;
	}
	
	
	public long create(CallerContext callerContext, SimpleDataObject dataObject){
		long userId = -1l;
		try {
			userId = User.create(callerContext, dataObject);
			
			List<Integer> addUserLicenses = new ArrayList<>();
			String[] licensearr = this.licences.split(",");
			for(String lic : licensearr)
				addUserLicenses.add(Integer.parseInt(lic));		//  Add Knowledge Licences	
			LicenseManagerBC.assignUserLicenses(callerContext, userId, addUserLicenses);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	private long[] getUserRoles(){
		if(roles==null)
			return new long[]{995,999};
		
		String[] role = roles.split(",");
		long[] rolearr = new long[role.length];
		for(int i = 0;i<role.length;i++){
		if(AGENT.equalsIgnoreCase(role[i]))
			rolearr[i]=AGENT_ROLE_ID;
		else if(AUTHOR.equalsIgnoreCase(role[i]))
			rolearr[i] = AUTHOR_ROLE_ID;
		else
			throw new BadRequestException("Invalid User Role");
		}
		
		return rolearr;
	}


	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}


	public int getContentLanguageId() {
		return contentLanguageId;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setContentLanguageId(int contentLanguageId) {
		this.contentLanguageId = contentLanguageId;
	}


	public String getEgainSessionId() {
		return egainSessionId;
	}


	public void setEgainSessionId(String egainSessionId) {
		this.egainSessionId = egainSessionId;
	}


	public String getRoles() {
		return roles;
	}


	public String getLicences() {
		return licences;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public void setLicences(String licences) {
		this.licences = licences;
	}

	public String getInstallDir() {
		return installDir;
	}

	public void setInstallDir(String installDir) {
		this.installDir = installDir;
		System.setProperty("INSTALL_DIR",installDir);
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	

}
