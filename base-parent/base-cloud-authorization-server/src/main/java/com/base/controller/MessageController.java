package com.base.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.ajax.AjaxJson;

@RequestMapping("/message")
@RestController
public class MessageController {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 统计消息总数
	 * 
	 * @return
	 */
	@GetMapping("/count")
	public AjaxJson count() {

		return AjaxJson.success("count", new Random().nextInt(1000));
	}

	/**
	 * 初始化消息
	 * 
	 * @return
	 */
	@GetMapping("/init")
	public AjaxJson init() {

		List<Map<String, Object>> readed = new ArrayList<Map<String, Object>>();

		Map<String, Object> readedMap1 = new HashMap<String, Object>();

		readedMap1.put("create_time", sdf.format(new Date()));

		readedMap1.put("title", "已经读过的消息1");
		readedMap1.put("msg_id", "1");

		readedMap1.put("loading", false);

		readed.add(readedMap1);

		List<Map<String, Object>> trash = new ArrayList<Map<String, Object>>();

		Map<String, Object> trashMap1 = new HashMap<String, Object>();

		trashMap1.put("create_time", sdf.format(new Date()));

		trashMap1.put("title", "垃圾箱的消息1");
		trashMap1.put("msg_id", "11");

		trashMap1.put("loading", false);
		trash.add(trashMap1);

		List<Map<String, Object>> unread = new ArrayList<Map<String, Object>>();

		Map<String, Object> unreadMap1 = new HashMap<String, Object>();

		unreadMap1.put("create_time", sdf.format(new Date()));

		unreadMap1.put("title", "没有读过的消息1");
		unreadMap1.put("msg_id", "22");

		unread.add(unreadMap1);

		AjaxJson j = new AjaxJson();
		j.setSuccess(true);

		j.put("readed", readed);
		j.put("trash", trash);
		j.put("unread", unread);

		return j;
	}

	@GetMapping("/content")
	public AjaxJson content(String msg_id) {
		
		return AjaxJson.success("content", "<divcourier new',=\"\" monospace;font-weight:=\"\" normal;font-size:=\"\" 12px;line-height:=\"\" 18px;white-space:=\"\" pre;\"=\"\"><div>&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: medium;\">这是消息内容，这个内容是使用<span style=\"color: rgb(255, 255, 255); background-color: rgb(28, 72, 127);\">富文本编辑器</span>编辑的，所以你可以看到一些<span style=\"text-decoration-line: underline; font-style: italic; color: rgb(194, 79, 74);\">格式</span></span></div><ol><li>你可以查看Mock返回的数据格式，和api请求的接口，来确定你的后端接口的开发</li><li>使用你的真实接口后，前端页面基本不需要修改即可满足基本需求</li><li>快来试试吧</li></ol><p>把车色想门转后器习应号光圆做边更况出及油史员切家技信龙劳并处型带先划热王必应集组才南写主切感十前运程前般并用准关放务根相做员院家油期总接间应再消质所平委好众办接现育电层及持水交亲格设直认志定决容门七效之设问属飞持头过之要型团单山化过压是再活气子号但最已关江。</p></divcourier>");
	}
	
	@GetMapping("/hasRead")
	public AjaxJson hasRead(String msg_id) {
		
		return AjaxJson.success("ok",msg_id);
	}
	
	
	
}
