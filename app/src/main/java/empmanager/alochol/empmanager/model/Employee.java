package empmanager.alochol.empmanager.model;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
    private String id;

    private String emp_name;

    private Integer emp_gender;

    private Integer emp_age;

    private String emp_code;

    private Integer emp_salary;

    private String emp_remark;

    private String create_id;

    private Long create_date;

    private Long update_date;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name == null ? null : emp_name.trim();
    }

    public Integer getEmp_gender() {
        return emp_gender;
    }

    public void setEmp_gender(Integer emp_gender) {
        this.emp_gender = emp_gender;
    }

    public Integer getEmp_age() {
        return emp_age;
    }

    public void setEmp_age(Integer emp_age) {
        this.emp_age = emp_age;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code == null ? null : emp_code.trim();
    }

    public Integer getEmp_salary() {
        return emp_salary;
    }

    public void setEmp_salary(Integer emp_salary) {
        this.emp_salary = emp_salary;
    }

    public String getEmp_remark() {
        return emp_remark;
    }

    public void setEmp_remark(String emp_remark) {
        this.emp_remark = emp_remark == null ? null : emp_remark.trim();
    }

    public String getCreate_id() {
        return create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id == null ? null : create_id.trim();
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
        sb.append(", emp_name=").append(emp_name);
        sb.append(", emp_gender=").append(emp_gender);
        sb.append(", emp_age=").append(emp_age);
        sb.append(", emp_code=").append(emp_code);
        sb.append(", emp_salary=").append(emp_salary);
        sb.append(", emp_remark=").append(emp_remark);
        sb.append(", create_id=").append(create_id);
        sb.append(", create_date=").append(create_date);
        sb.append(", update_date=").append(update_date);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}