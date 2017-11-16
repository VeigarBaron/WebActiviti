package cn.itcast.ssh.web.action;

import cn.itcast.ssh.service.ILeaveBillService;
import cn.itcast.ssh.service.IWorkflowService;
import cn.itcast.ssh.utils.ValueContext;
import cn.itcast.ssh.web.form.WorkflowBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class WorkflowAction extends ActionSupport implements ModelDriven<WorkflowBean> {

	private WorkflowBean workflowBean = new WorkflowBean();
	
	@Override
	public WorkflowBean getModel() {
		return workflowBean;
	}
	
	private IWorkflowService workflowService;
	
	private ILeaveBillService leaveBillService;

	public void setLeaveBillService(ILeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	public void setWorkflowService(IWorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	/**
	 * 部署管理首页显示
	 * @return
	 */
	public String deployHome(){
		//查询部署对象信息
		List<Deployment> deplist = workflowService.findDeploymentList();
		//查询流程定义的信息
		List<ProcessDefinition> pdlist = workflowService.findProcessDefinitionList();
		//放置到上下文对象中
		ValueContext.putValueContext("deplist", deplist);
		ValueContext.putValueContext("pdlist", pdlist);
		return "deployHome";
	}
	
	/**
	 * 发布流程
	 * @return
	 */
	public String newdeploy(){
		//获取页面传递的值
		//获取页面传递的zip格式的文件，格式是file类型
		File file = workflowBean.getFile();
		//文件名称
		String filename = workflowBean.getFilename();
		//完成部署
		workflowService.saveNewDeploye(file,filename);
		
		return "list";
	}
	
	/**
	 * 删除部署信息
	 */
	public String delDeployment(){
		//获取部署ID
		String deploymentId = workflowBean.getDeploymentId();
		//通过部署对象ID  删除流程定义
		workflowService.deleteProcessDefinitionByDeployment(deploymentId);
		return "list";
	}
	
	/**
	 * 查看流程图
	 * @throws IOException 
	 */
	public String viewImage() throws IOException{
		//1.通过页面获取
		//流程部署ID
		String deploymentId = workflowBean.getDeploymentId();
		//流程图名称
		String imageName = workflowBean.getImageName();
		
		//2.获取资源文件表（act_ge_bytearray）中资源图片输入流 input stream
		InputStream in = workflowService.findImageOutputStream(deploymentId,imageName);
		//3.从response对象获取输出流
		OutputStream out = ServletActionContext.getResponse().getOutputStream();
		//4.将输入流读出来  写到输出流里面
		for(int b=-1;(b=in.read())!=-1;) {
			out.write(b);
		}
		out.close();
		in.close();
		
		//将图写到页面上  ，用输出流
		return null;
	}
	
	// 启动流程
	public String startProcess(){
		return "listTask";
	}
	
	
	
	/**
	 * 任务管理首页显示
	 * @return
	 */
	public String listTask(){
		return "task";
	}
	
	/**
	 * 打开任务表单
	 */
	public String viewTaskForm(){
		return "viewTaskForm";
	}
	
	// 准备表单数据
	public String audit(){
		return "taskForm";
	}
	
	/**
	 * 提交任务
	 */
	public String submitTask(){
		return "listTask";
	}
	
	/**
	 * 查看当前流程图（查看当前活动节点，并使用红色的框标注）
	 */
	public String viewCurrentImage(){
		return "image";
	}
	
	// 查看历史的批注信息
	public String viewHisComment(){
		return "viewHisComment";
	}
}
