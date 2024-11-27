package Utilities.constans;

public final class APIConstans {
	
//	public final static String DESIGNER_NAME = "6453ed38ec0ccca55e8c6eae";  //Automation Designer
//	public final static String SPECIALIST_NAME = "6453eccaec0ccca55e8c6e85"; //Automation Specialist
//	public final static String MANUFACTURER_NAME = "64591a657f8f83181f2b6329"; //Automation Manufacturer
	
	
	//These designer and specialist are used to validate refinement scenario by making these users as inactive during refinement
//	public final static String DESIGNER_REF_NAME = "64351637bbd5c953c57e84dd";  //Automation Designer
//	public final static String SPECIALIST_REF_NAME = "64352387abbaa7d2f7378180"; //Automation Specialist
	
	//admin
	public final static String ADMIN_HEALTH_GET = "/health";
	//Admin/Submission
	public final static String ADMIN_SUBMISSIONS_GET = "/v1/admin/submissions?page=1&perPage=10&q=test&order=asc&status=ON_HOLD&planType=INITIAL&sort=createdAt";
	public final static String ADMIN_ANYSUBMISSIONS_GET = "/v1/admin/submissions?page=1&perPage=10&q=test&order=asc&planType=INITIAL&sort=createdAt";
	public final static String ADMIN_SUBMISSIONS_GET_APPROVED = "/v1/admin/submissions?page=1&perPage=10&q=test&order=asc&status=APPROVED&planType=INITIAL&sort=createdAt";
	public final static String ADMIN_SUBMISSIONS_PATIENTS_GET = "/v1/admin/submissions/patients/{id}";
	public final static String ADMIN_SUBMISSION_GET = "/v1/admin/submissions/{id}";
	public final static String ADMIN_SUBMISSION_ASSIGN_POST = "/v1/admin/submissions/{id}/assign";
	public final static String ADMIN_SUBMISSION_ARCHIVE_POST = "/v1/admin/submissions/archive";
	public final static String ADMIN_SUBMISSION_STATUS_PUT = "/v1/admin/submissions/{id}/status";
	public final static String ADMIN_SUBMISSION_STLS_PUT = "/v1/admin/submissions/{id}/stls";
	public final static String ADMIN_SUBMISSION_PRIORITY_PUT = "/v1/admin/submissions/{id}/priority";
	public final static String ADMIN_SUBMISSION_TIMELINES_GET = "/v1/admin/submissions/{id}/timelines";
	public final static String ADMIN_SUBMISSION_ARCHIVE_STLS_PUT = "/v1/admin/submissions/{id}/archive-stls";
	public final static String ADMIN_SUBMISSION_APPROVE_UNSUITABLE_PUT = "/v1/admin/submissions/{id}/approve-unsuitable";
	public final static String ADMIN_SUBMISSION_ORDERS_GET = "/v1/admin/submissions/{id}/orders";
	
	
	//Admin/Auth
	public final static String ADMIN_SIGNIN_POST = "/v1/admin/auth/signin";
	public final static String ADMIN_REGISTER_POST = "/v1/admin/auth/register";
	public final static String ADMIN_SIGNOUT_POST = "/v1/admin/auth/signout";
	
	//Admin/User
	public final static String ADMIN_USERS_GET = "/v1/admin/users?page=1&perPage=10&minSubmissions=0&maxSubmissions=20&sort=createdAt";
	public final static String ADMIN_USERS_GET_MYDENTIST = "/v1/admin/users?page=1&perPage=10&email=prakasha.shetty+1dent@32co.com&sort=createdAt";
	public final static String ADMIN_USERS_GET_DENTIST = "/v1/admin/users?page=1&perPage=10&role=DENTIST&status=LIVE&sort=createdAt";
	public final static String ADMIN_ASSIGNABLE_GET = "/v1/admin/users/assignable?page=1&perPage=20&submission={id}&role={role}";
	public final static String ADMIN_USER_GET = "/v1/admin/users/{id}";	
	public final static String ADMIN_USER_UPDATE_STATUS_PUT = "/v1/admin/users/{id}/update-status";
	public final static String ADMIN_USER_INVITE_DENTIST_PUT = "/v1/admin/users/invite-dentist";
	public final static String ADMIN_USER_SUBMISSIONS_GET = "/v1/admin/users/{id}/submissions?page=1&perPage=10&order=asc&sort=createdAt";
	public final static String ADMIN_USER_ORDERS_GET = "/v1/admin/users/{id}/orders?page=1&perPage=10&order=asc";
	public final static String ADMIN_USER_EDUCATION_CONTENT_GET = "/v1/admin/users/{id}/education-contents?page=1&perPage=10&order=asc";
	public final static String ADMIN_USER_EDUCATION_CONTENT_PUT = "/v1/admin/users/{id}/education-contents/{contentId}";
	
	//Admin/Practice
	public final static String ADMIN_PRACTICES_ASSIGN_POST = "/v1/admin/practices/assign";
	public final static String ADMIN_PRACTICES_SCANNER = "/v1/admin/practices/scanners";
	public final static String ADMIN_PRACTICES_GET = "/v1/admin/practices?page=1&perPage=10&order=asc&sort=name";
	public final static String ADMIN_PRACTICES_POST = "/v1/admin/practices";
	public final static String ADMIN_PRACTICE_GET = "/v1/admin/practices/{id}";
	public final static String ADMIN_PRACTICES_PUT = "/v1/admin/practices/{id}";
	public final static String ADMIN_PRACTICES_DELETE = "/v1/admin/practices/{id}";
	
	//Admin/release
	public final static String ADMIN_RELEASES_POST = "/v1/admin/releases";
	public final static String ADMIN_RELEASES_GET = "/v1/admin/releases?page=1&perPage=10";
	public final static String ADMIN_RELEASE_GET = "/v1/admin/releases/{id}";
	public final static String ADMIN_RELEASE_PUT = "/v1/admin/releases/{id}";
	public final static String ADMIN_RELEASE_DELETE = "/v1/admin/releases/{id}";

	//admin/groups
	public final static String ADMIN_GROUPS_POST = "/v1/admin/groups";
	public final static String ADMIN_GROUPS_GET = "/v1/admin/groups?page=1&perPage=10&status=ACTIVE";
	public final static String ADMIN_GROUPS_TAGS_GET = "/v1/admin/groups/tags";
	public final static String ADMIN_GROUP_GET = "/v1/admin/groups/{id}";
	public final static String ADMIN_GROUPS_PUT = "/v1/admin/groups/{id}";
	public final static String ADMIN_GROUPS_DELETE = "/v1/admin/groups/{id}";
	public final static String ADMIN_GROUP_MEMBER_GET = "/v1/admin/groups/{id}/members?page=1&perPage=10";
	public final static String ADMIN_GROUP_DENTISTS_GET = "/v1/admin/users?&page=1&perPage=10&role=DENTIST&notShowBlocked=true";

	
	//admin/skus
	
	public final static String ADMIN_SKUS_POST = "/v1/admin/skus";	
	public final static String ADMIN_SKUS_GET = "/v1/admin/skus?page=1&perPage=10&status=LIVE";
	public final static String ADMIN_SKUS_STATUS_PUT = "/v1/admin/skus/status";
	public final static String ADMIN_SKU_GET = "/v1/admin/skus/{id}";
	public final static String ADMIN_SKUS_PUT = "/v1/admin/skus/{id}";
	public final static String ADMIN_SKUS_DELETE = "/v1/admin/skus/{id}";
	public final static String ADMIN_SKUS_GET_MANUFACTURER = "/v1/admin/skus?manufacturer={id}&page=1&perPage=10&status=LIVE";
	
	//admin/forms
	public final static String ADMIN_FORMS_OPTIONS_GET = "/v1/admin/forms/options?formName=solo"; 
	public final static String ADMIN_FORMS_COUNTRIES_GET = "/v1/admin/forms/countries";
	public final static String ADMIN_FORMS_NOTIONS_GET = "/v1/admin/forms/notions?url=https%3A%2F%2Fwww.notion.so%2Fcd0890571f084806a84beef641f3d587%3Fp%3D61eee17c9da9401eb6bea8e21f1083b2%26amp%3Bpm%3Ds";
	public final static String ADMIN_FORMS_NOTIONS_INVALIDATE_PUT = "/v1/admin/forms/notions/invalidate";
	
	//Admin/TreatmentDesign
	
	public final static String ADMIN_TREATMENTDEISGNS_GET = "/v1/admin/treatment-designs";
	public final static String ADMIN_TREATMENTDEISGNS_LATEST_GET = "/v1/admin/treatment-designs/latest";
	public final static String ADMIN_TREATMENTDEISGNS_APPROVE_STLS_PUT = "/v1/admin/treatment-designs/{id}/approve-stls";
	public final static String ADMIN_TREATMENTDEISGNS_APPROVE_PUT = "/v1/admin/treatment-designs/{id}/approve";
	public final static String ADMIN_TREATMENTDEISGN_GET = "/v1/admin/treatment-designs/{id}";
	public final static String ADMIN_TREATMENTDEISGNS_PUT = "/v1/admin/treatment-designs/{id}";
	
	//Admin/Education Content
	public final static String ADMIN_EDU_CONTENT_POST = "/v1/admin/education-contents";
	public final static String ADMIN_EDU_CONTENTS_GET = "/v1/admin/education-contents?page=1&perPage=10&order=asc&sort=createdAt&difficultyLevel=GENERAL";
	public final static String ADMIN_EDU_CONTENT_TAGS_GET = "/v1/admin/education-contents/tags";
	public final static String ADMIN_EDU_CONTENT_GET = "/v1/admin/education-contents/{id}";
	public final static String ADMIN_EDU_CONTENT_PUT = "/v1/admin/education-contents/{id}";
	public final static String ADMIN_EDU_CONTENT_DELETE = "/v1/admin/education-contents/{id}";
	public final static String ADMIN_EDU_CONTENT_LIKE_POST = "/v1/admin/education-contents/{id}/like";
	public final static String ADMIN_EDU_CONTENT_RESTORE_PUT = "/v1/admin/education-contents/{id}/restore";
	public final static String ADMIN_EDU_CONTENT_ASSIGN_POST = "/v1/admin/education-contents/{id}/assign";
	
	//Admin/File
	public final static String ADMIN_FILE_SIGNED_URL_POST= "/v1/admin/files/signed-url";

	//Admin/Dashboard
	public final static String ADMIN_DASHBOARD_SUBMISSION_COUNT_GET= "/v1/admin/dashboard/submissions/count";
	public final static String ADMIN_DASHBOARD_SUBMISSIONS_GET= "/v1/admin/dashboard/submissions?page=1&perPage=10&target=NOT_ACCEPTED";
	//public final static String ADMIN_DASHBOARD_CONFIRM_SHIPPING= "/v1/admin/submissions/{submissionid}/shipping";
	public final static String ADMIN_DASHBOARD_CONFIRM_SHIPPING= "/v1/admin/submissions/shipping";
	public final static String ADMIN_DASHBOARD_MARK_MATERIAL_RECIEVED= "https://api-alpha.32co.com/v1/admin/submissions/{submissionid}/status";
	
	//admin/manufacturer
	public final static String ADMIN_SEND_TO_MANUFACTURER_PUT= "/v1/admin/treatment-designs/{designid}/approve-stls";
	
	
	//Admin/Payment
	public final static String ADMIN_PAYMENTS_INBOUND= "/v1/admin/payments/inbound?page=1&perPage=10&order=asc&sort=createdAt";
	public final static String ADMIN_PAYMENTS_OUTBOUND= "/v1/admin/payments/outbound?page=1&perPage=10&order=asc&sort=createdAt";
	
	//Admin/Notes
	public final static String ADMIN_NOTES_GET= "/v1/admin/notes?submission={submissionid}";
	public final static String ADMIN_NOTES_POST= "/v1/admin/notes";
	public final static String ADMIN_NOTES_PUT= "/v1/admin/notes/{id}";
	
	//User/Auth
	public final static String USER_AUTH_SIGNIN= "/v1/user/auth/signin";
	public final static String USER_AUTH_REGISTER_DENTIST= "/v1/user/auth/register/dentist";
	public final static String USER_AUTH_REGISTER_DESIGNER= "/v1/user/auth/register/designer";
	public final static String USER_AUTH_REGISTER_SPECIALIST= "/v1/user/auth/register/specialist";
	public final static String USER_AUTH_REGISTER_MANUFACTURER= "/v1/user/auth/register/manufacturer";
	public final static String USER_AUTH_SIGNOUT= "/v1/user/auth/signout";
	public final static String USER_AUTH_VERIFY= "/v1/user/auth/verify";
	public final static String USER_AUTH_PROFILE= "/v1/user/auth/profile";
	public final static String USER_AUTH_PASSWORD_RESET_REQUEST= "/v1/user/auth/password/reset/request";
	public final static String USER_AUTH_PASSWORD_RESET_VERIFY = "/v1/user/auth/password/reset/verify";
	public final static String USER_AUTH_PASSWORD_RESET= "/v1/user/auth/password/reset";
	
	
	//Dentist

	public final static String DENTIST_SUBM_POST_INITIAL_SOLO = "/v1/dentist/submissions/initial/solo";
	public final static String DENTIST_SUBM_POST_INITIAL_DUO = "/v1/dentist/submissions/initial/duo";	
	public final static String DENTIST_SUBM_POST_REFINEMENT_SOLO = "/v1/dentist/submissions/refinement/solo";	
	public final static String DENTIST_SUBM_POST_REFINEMENT_DUO = "/v1/dentist/submissions/refinement/duo";
	public final static String DENTIST_PATIENT_PROPOSAL_POST = "/v1/dentist/patient-proposals";	
	public final static String DENTIST_ORDERS_POST = "/v1/dentist/orders";
	
	//Dentist New End Points
	public final static String DENTIST_SUBM_POST_INITIAL_SOLO_1 = "/v1/dentist/submissions/initial/solo/";

	
	//desginer
	
	public final static String DESIGNER_PATEINTS_SUBMIT_INVITATIONS_PUT = "/v1/designer/submission-invitations/{submissionid}";
	public final static String DESIGNER_UPLOAD_STLS_PUT = "/v1/designer/treatment-designs/{designid}/upload-stls";
	public final static String DESIGNER_SUBMISSION_ANIMATION_PUT = "/v1/designer/submissions/{submissionid}/animation";
	public final static String DESIGNER_PATEINTS_TREATMENT_DESGIN_PUT = "/v1/designer/treatment-designs";
	
	
	//manufacturer
	public final static String MANUFACTURER_MOVE_TO_INPROGRESS_PUT = "/v1/manufacturer/submissions/{submissionid}";
	public final static String MANUFACTURER_ADD_EDIT_SHIPPING_LINK_PUT = "/v1/manufacturer/submissions/{submissionid}/shipping-link";
	public final static String MANUFACTURER_ALL_SKU_DETAILS_GET = "/v1/admin/skus?manufacturer={id}&page=1&perPage=10";
	public final static String MANUFACTURER_SKU_DETAIL_GET = "/v1/admin/skus/{skuid}";
	public final static String MANUFACTURER_CREATE_SKU_POST = "/v1/admin/skus";

	//specialist
	public final static String SPECIALIST_PATEINTS_SUBMIT_INVITATIONS_PUT = "/v1/specialist/submission-invitations/{submissionid}";
	public final static String SPECIALIST_PATEINTS_INSTRUCTIONS_PUT = "/v1/specialist/instructions";
	public final static String SPECIALIST_PATEINTS_ADVICES_PUT = "/v1/specialist/advices";
	
}
