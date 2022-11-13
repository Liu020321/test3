package org.example.student;

import org.example.common.model.Format;
import org.example.common.model.Student;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * @Author www.hiai.top
 * @Email goodsking@163.com
 *        所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 *        要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(StudentInterceptor.class)
public class StudentController extends Controller {

	@Inject
	StudentService service = new StudentService();

	/**
	 * 后台主界面渲染
	 */
	// @Before(StudentValidator.class)
	public void index() {
		if (getSession().getAttribute("loginUser") == null) {
			redirect("/");
			// renderText("4111");
		} else {
			// System.out.println(getSession().getAttribute("loginUser"));
			render("student.html");
		}

	}

	/**
	 * 学生后台静态界面渲染
	 */
	public void list() {
		setAttr("list", service.paginate(getParaToInt(0, 1), 5));
		render("list.html");
	}

	/**
	 * 添加学生
	 */
	public void add() {
		render("add.html");
	}

	/**
	 * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
	 * 并要对数据进正确性进行验证，在此仅为了偷懒
	 */
	// @Before(StudentValidator.class)
	public void save() {
		String id = getPara("student.id");
		// 添加学生操作
		if (id == null) {
			getModel(Student.class).save();
			redirect("/student/list");
			// 修改学生操作
		} else {
			getModel(Student.class).update();
			redirect("/student/list");
		}
	}

	/**
	 * 渲染更新学生信息的界面
	 */
	public void edit() {
		setAttr("student", service.findById(getParaToInt()));
		render("add.html");
	}

	/**
	 * 静态表格删除学生信息
	 */
	public void delete() {
		service.deleteById(getParaToInt());
		redirect("/student/list");
	}

	/**
	 * 动态表格渲染
	 */
	public void listStu() {
		render("listStu.html");
	}

	/**
	 * 动态表格接口
	 */
	public void lists() {

		Integer page = getParaToInt("page", 1);
		Integer limit = getParaToInt("limit", 10);
		Page<Student> paginate = service.paginate(page, limit);
		renderJson(Format.layuiPage(paginate));

	}

}
