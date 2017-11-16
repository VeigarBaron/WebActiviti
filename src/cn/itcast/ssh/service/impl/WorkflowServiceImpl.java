package cn.itcast.ssh.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import cn.itcast.ssh.dao.ILeaveBillDao;
import cn.itcast.ssh.service.IWorkflowService;

public class WorkflowServiceImpl implements IWorkflowService {
	/**请假申请Dao*/
	private ILeaveBillDao leaveBillDao;
	
	private RepositoryService repositoryService;
	
	private RuntimeService runtimeService;
	
	private TaskService taskService;
	
	private FormService formService;
	
	private HistoryService historyService;
	
	public void setLeaveBillDao(ILeaveBillDao leaveBillDao) {
		this.leaveBillDao = leaveBillDao;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	//部署流程定义
	@Override
	public void saveNewDeploye(File file, String filename) {

		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			repositoryService.createDeployment()		//创建部署对象
							.name(filename)				//添加部署名称
							.addZipInputStream(zipInputStream)	//
							.deploy();					//完成部署
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//流程部署对象查询
	@Override
	public List<Deployment> findDeploymentList() {

		List<Deployment> list = repositoryService.createDeploymentQuery()
				.orderByDeploymenTime().asc().list();//集合
		return list;
	}

	//查询流程定义
	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
		.orderByProcessDefinitionVersion().asc().list();
		
		return list;
	}

	//部署流程的  流程图查看，通过获取部署Id和流程图名称
	@Override
	public InputStream findImageOutputStream(String deploymentId, String imageName) {

		//repositoryService.getResourceAsStream(deploymentId, imageName);
		
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}
	//使用部署对象ID，删除流程定义
	@Override
	public void deleteProcessDefinitionByDeployment(String deploymentId) {

		repositoryService.deleteDeployment(deploymentId,true);
	}
	
}
