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
	 * ��������
	 */
	@Test
	public void deployProcess() {

		// Ĭ��ȥ����resource�µ�activiti.cfg.xml�����ļ������Բ鿴Դ��
		// ͨ��Ĭ�ϵķ���������������:������Ҫ�����ݿ������
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

		// ���洢����
		RepositoryService repositoryService = engine.getRepositoryService();

		// ���������ļ����ļ����ݺ�����ͼ�洢�����ݱ�act_ge_bytearray����
		repositoryService.createDeployment().addClasspathResource("MyProcess.bpmn").deploy();
	}

	/**
	 * ��������ʵ��
	 */
	@Test
	public void startProcess() {
		// ͨ��Ĭ�ϵķ���������������:������Ҫ�����ݿ������
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// ������ʱ����
		RuntimeService runService = engine.getRuntimeService();
		// ���������
		TaskService taskService = engine.getTaskService();
		
		// ��������
		ProcessInstance processInstance = runService.startProcessInstanceByKey("myProcess");

		// ��ͨԱ�������ٵ�����
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("��ǰ���̽ڵ㣺" + task.getName());
		taskService.complete(task.getId());

		// �����������
		task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("��ǰ���̽ڵ㣺" + task.getName());
		taskService.complete(task.getId());

		task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("���̽�����" + task);

		engine.close();
		System.exit(0);
	}

}
