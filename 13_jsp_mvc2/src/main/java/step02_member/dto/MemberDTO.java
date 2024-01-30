package step02_member.dto;

import java.util.Date;

public class MemberDTO {

	private String memberId;
	private String memberNm;
	private String passwd;
	private String profile;
	private String profileUUID;
	private String sex;
	private String birthAt;
	private String hp;
	private String smsRecvAgreeYn;
	private String email;
	private String emailRecvAgreeYn;
	private String zipcode;
	private String roadAddress;
	private String jibunAddress;
	private String namujiAddress;
	private Date joinAt;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getProfileUUID() {
		return profileUUID;
	}
	public void setProfileUUID(String profileUUID) {
		this.profileUUID = profileUUID;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthAt() {
		return birthAt;
	}
	public void setBirthAt(String birthAt) {
		this.birthAt = birthAt;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getSmsRecvAgreeYn() {
		return smsRecvAgreeYn;
	}
	public void setSmsRecvAgreeYn(String smsRecvAgreeYn) {
		this.smsRecvAgreeYn = smsRecvAgreeYn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailRecvAgreeYn() {
		return emailRecvAgreeYn;
	}
	public void setEmailRecvAgreeYn(String emailRecvAgreeYn) {
		this.emailRecvAgreeYn = emailRecvAgreeYn;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getRoadAddress() {
		return roadAddress;
	}
	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}
	public String getJibunAddress() {
		return jibunAddress;
	}
	public void setJibunAddress(String jibunAddress) {
		this.jibunAddress = jibunAddress;
	}
	public String getNamujiAddress() {
		return namujiAddress;
	}
	public void setNamujiAddress(String namujiAddress) {
		this.namujiAddress = namujiAddress;
	}
	public Date getJoinAt() {
		return joinAt;
	}
	public void setJoinAt(Date joinAt) {
		this.joinAt = joinAt;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [memberId=" + memberId + ", memberNm=" + memberNm + ", passwd=" + passwd + ", profile="
				+ profile + ", profileUUID=" + profileUUID + ", sex=" + sex + ", birthAt=" + birthAt + ", hp=" + hp
				+ ", smsRecvAgreeYn=" + smsRecvAgreeYn + ", email=" + email + ", emailRecvAgreeYn=" + emailRecvAgreeYn
				+ ", zipcode=" + zipcode + ", roadAddress=" + roadAddress + ", jibunAddress=" + jibunAddress
				+ ", namujiAddress=" + namujiAddress + ", joinAt=" + joinAt + "]";
	}
    
}
