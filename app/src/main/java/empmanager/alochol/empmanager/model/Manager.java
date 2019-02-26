package empmanager.alochol.empmanager.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员
 */
public class Manager implements Serializable {
    private String id;

    private String mgr_name;

    private Integer mgr_age;

    private Integer gender;

    private Integer role_id;

    private String mgr_password;

    private Integer mgr_status;

    private String mgr_remark;

    private String create_id;

    private Long create_date;

    private Long update_date;

    public Manager() {
    }

    public Manager(String mgr_name, String mgr_password) {
        this.mgr_name = mgr_name;
        this.mgr_password = mgr_password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMgr_name() {
        return mgr_name;
    }

    public void setMgr_name(String mgr_name) {
        this.mgr_name = mgr_name;
    }

    public Integer getMgr_age() {
        return mgr_age;
    }

    public void setMgr_age(Integer mgr_age) {
        this.mgr_age = mgr_age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getMgr_password() {
        return mgr_password;
    }

    public void setMgr_password(String mgr_password) {
        this.mgr_password = mgr_password;
    }

    public Integer getMgr_status() {
        return mgr_status;
    }

    public void setMgr_status(Integer mgr_status) {
        this.mgr_status = mgr_status;
    }

    public String getMgr_remark() {
        return mgr_remark;
    }

    public void setMgr_remark(String mgr_remark) {
        this.mgr_remark = mgr_remark;
    }

    public String getCreate_id() {
        return create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }

    public Long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Long create_date) {
        this.create_date = create_date;
    }

    public Long getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Long update_date) {
        this.update_date = update_date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mgr_name=").append(mgr_name);
        sb.append(", mgr_age=").append(mgr_age);
        sb.append(", gender=").append(gender);
        sb.append(", role_id=").append(role_id);
        sb.append(", mgr_password=").append(mgr_password);
        sb.append(", mgr_status=").append(mgr_status);
        sb.append(", mgr_remark=").append(mgr_remark);
        sb.append(", create_id=").append(create_id);
        sb.append(", create_date=").append(create_date);
        sb.append(", update_date=").append(update_date);
        sb.append("]");
        return sb.toString();
    }
}