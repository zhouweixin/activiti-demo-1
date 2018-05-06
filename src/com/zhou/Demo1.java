package com.zhou;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class Demo1 {

	/**
	 * 部署流程
	 */
	@Test
	public void deployProcess() {

		// 默认去加载resource下的activiti.cfg.xml配置文件，可以查看源码
		// 通过默认的方法创建流程引擎:这里主要是数据库的配置
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

		// 【存储服务】
		RepositoryService repositoryService = engine.getRepositoryService();

		// 部署流程文件：文件内容和流程图存储到数据表【act_ge_bytearray】中
		repositoryService.createDeployment().addClasspathResource("MyProcess.bpmn").deploy();
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcess() {
		// 通过默认的方法创建流程引擎:这里主要是数据库的配置
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 【运行时服务】
		RuntimeService runService = engine.getRuntimeService();
		// 【任务服务】
		TaskService taskService = engine.getTaskService();
		
		// 启动流程
		ProcessInstance processInstance = runService.startProcessInstanceByKey("myProcess");

		// 普通员工完成请假的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("当前流程节点：" + task.getName());
		taskService.complete(task.getId());

		// 经理审核任务
		task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("当前流程节点：" + task.getName());
		taskService.complete(task.getId());

		task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("流程结束后：" + task);

		engine.close();
		System.exit(0);
	}

}
