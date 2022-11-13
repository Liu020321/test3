package org.example.student;

import org.example.common.model.Student;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * @Author www.hiai.top
 * @Email goodsking@163.com
 *        所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 *        要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class StudentService {

	/**
	 * 所有的 dao 对象也放在 Service 中，并且声明为 private，避免 sql 满天飞
	 * sql 只放在业务层，或者放在外部 sql 模板，用模板引擎管理：
	 * http://www.jfinal.com/doc/5-13
	 */
	private Student dao = new Student().dao();

	/**
	 * 遍历学生列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Student> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from t_student order by id asc");
	}

	public Student findById(int id) {
		return dao.findById(id);
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}

	/**
	 * 验证用户登录
	 * 
	 * @param Student
	 * @return
	 */
	public Student findUserByNameAndPassword(String account) {
		return dao.findFirst("select * from t_student where account = ? ", account);
	}

	/**
	 * 验证用户邮箱
	 * 
	 * @param Student
	 * @return
	 */
	public Student findEmail(String xueEmail) {
		return dao.findFirst("select * from t_student where xueEmail = ? ", xueEmail);
	}

	/**
	 * 根据用户邮箱更改密码
	 * 
	 * @param Student
	 * @return
	 */
	public void useEmailUpdatePassword(int newPassword, String xueEmail) {
		Db.update(" update t_student set password = '" + newPassword + "' where xueEmail = ? ", xueEmail);
	}
}
