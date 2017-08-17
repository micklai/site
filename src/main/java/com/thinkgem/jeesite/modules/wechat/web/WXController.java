package com.thinkgem.jeesite.modules.wechat.web;
/*package com.thinkgem.jeesite.modules.wechat.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.test.basic.model.Pager;
import com.test.basic.model.SystemContext;
import com.test.basic.util.JsonUtil;
import com.test.basic.util.UUIDGenerator;
import com.test.cms.auth.AuthMethod;
import com.test.cms.dto.AjaxObj;
import com.thinkgem.jeesite.modules.wechat.dto.ProblemDto;
import com.thinkgem.jeesite.modules.wechat.dto.WeChatUser;
import com.thinkgem.jeesite.modules.wechat.dto.WeixinOauth2Token;
import com.thinkgem.jeesite.modules.wechat.model.Problem;
import com.thinkgem.jeesite.modules.wechat.model.ProblemAttachment;
import com.thinkgem.jeesite.modules.wechat.service.ProblemAttachmentService;
import com.thinkgem.jeesite.modules.wechat.service.ProblemService;
import com.thinkgem.jeesite.modules.wechat.util.Sign;
import com.thinkgem.jeesite.modules.wechat.util.WeChatUtil;

*//**
 * 微信端口相关操作
 * 
 * @author Administrator
 *
 *//*
 
@RequestMapping({ "/" })
@Controller
public class WXController {
	@Autowired
	private ProblemAttachmentService problemAttachmentService;
	
	@Autowired
	private ProblemService problemService;
	
	public final static String FILE_PATH="/resources/indexPic";
	//验证网页所需网址
	public final static String JS_SDK_URL = "http://www.cdhncy.com";
	public final static int T_W = 120;
	
	@RequestMapping(value = { "/weixin" },method ={RequestMethod.GET,RequestMethod.POST})
	public String menus(Model model, HttpServletRequest req, HttpServletResponse resp) {
		// 用户同意授权后，能获取到code
		String code = req.getParameter("code");// 用户同意授权
		// 申请的测试号appid appsecret
		System.out.println("dopost");
		System.out.println(code);
		WeChatUtil wcUtil = new WeChatUtil();
		
		
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = wcUtil.getOauth2AccessToken(WeChatUtil.appId,
					WeChatUtil.appSecret, code);
			if (null == weixinOauth2Token) {
				//返回获取access_token错误页面
				return "error";
			}
			
			System.out.println(weixinOauth2Token.getAccessToken());
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			System.out.println(accessToken);
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			WeChatUser wcu = wcUtil.getSNSUserInfo(accessToken, openId);// 设置要传递的参数
			System.out.println(wcu.getNickname());
			req.setAttribute("wcu", wcu);
		}
		System.out.println("---------------------------------------------------");
		// 跳转到index.jsp
		return "";
	}

	
	*//**
	 * 测试返回提交消息
	 * @param model
	 * @param req
	 * @return
	 *//*
	@RequestMapping(value = { "/report" }, method = { RequestMethod.GET })
	public String report( HttpServletRequest req, HttpServletResponse resp) {
		
		System.out.println("---------------------------------------------------");
		return "wechat/report";
	} 
	
	*//**
	 * 返回微信案卷列表
	 * @param model
	 * @param req
	 * @param resp
	 * @return
	 *//*
	@RequestMapping(value = { "/weixins" }, method = { RequestMethod.GET })
	public String index(Model model, HttpServletRequest req, HttpServletResponse resp) {
		SystemContext.setPageSize(Integer.valueOf(10));
		System.out.println("-------------------------进入微信案卷列表--------------------------");
		//判断是否公开显示  传递参数0表示公开
		Pager<Problem> wechatProblem= problemService.findByOvert(ProblemDto.NOTOVERT);
		SystemContext.removePageSize();
		model.addAttribute("wProblem", wechatProblem);
		return "wechat/wechat_list";
	}
	
	*//**
	 * 重定向到微信
	 * @param model
	 * @param req
	 * @param resp
	 *//*
	@RequestMapping(value="/wechat/redir/contributes",method=RequestMethod.GET)
	public void redriectContribute(HttpServletRequest req,HttpServletResponse resp){
		try {
			String redirectUrl = "http://www.cdhncy.com/contributes";
			String scope = "snsapi_userinfo";
			Long state = new Date().getTime();
			String url = WeChatUtil.WeChatRedirect(redirectUrl, scope, state);
			System.out.println(url);
			resp.sendRedirect(url);
		} catch (IOException e) {
				System.out.println("地址转换错误");
		}
	}
	
	*//**
	 * 返回我的案卷页面
	 * @return
	 *//* 
	@RequestMapping(value = { "/contributes" }, method = {RequestMethod.GET})
	public String contribute(Model model,HttpServletRequest req){
		// 用户同意授权后，能获取到code
		String code = req.getParameter("code");// 用户同意授权
		// 申请的测试号appid appsecret
		System.out.println("dopost");
		System.out.println(code);
		WeChatUtil wcUtil = new WeChatUtil();
		
		
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = wcUtil.getOauth2AccessToken(code);
			if (null == weixinOauth2Token) {
				//返回获取access_token错误页面
				return "error";
			}
			
			System.out.println(weixinOauth2Token.getAccessToken());
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			System.out.println(accessToken);
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			WeChatUser wcu = wcUtil.getSNSUserInfo(accessToken, openId);// 设置要传递的参数
			SystemContext.setPageSize(Integer.valueOf(100));
			System.out.println("-------------------------进入我的案卷列表--------------------------");
			Pager<Problem> contributeProblem=problemService.findProblemProcess(wcu.getOpenId());
			model.addAttribute("conProblem",contributeProblem);
		}else{
			System.out.println("进入失败");
		}
		
		return "wechat/wechat_contribute_list";
	}
	
	
	@RequestMapping(value = { "/pager/data" },method = {RequestMethod.POST})
	@ResponseBody
	public void pagerData(Model model, HttpServletRequest req, HttpServletResponse resp) {
			
			try {
				String offset = req.getParameter("offset");
				SystemContext.setPageOffset(Integer.valueOf(offset));
				SystemContext.setPageSize(Integer.valueOf(10));
				//判断是否公开显示  传递参数1表示公开
				Pager<Problem> wechatProblem= problemService.findByOvert(ProblemDto.OVERT);
				List<Problem> problemList = wechatProblem.getDatas();

				
				String json = "";
				//处理级联转json 数据死循环
				for (Problem problem : problemList) {
					List<ProblemAttachment> problemAttachmentList  = problem.getProblemAttachment();
					for(ProblemAttachment problemAttachment : problemAttachmentList){
						problemAttachment.setProblem(null);
					}
				}
				
				//json += JsonUtil.getInstance().obj2json(problem);
				json = JsonUtil.getInstance().obj2json(problemList);
				//总页数完了之后不进后台刷新
				System.out.println(wechatProblem.getTotal());
				SystemContext.removePageSize();
				resp.setCharacterEncoding("utf-8");
				resp.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	*//**
	 * 我的贡献列表 滚动加载
	 * @param model
	 * @param req
	 * @param resp
	 *//*
	@RequestMapping(value = { "/contribute/pager/data" }, method = { RequestMethod.POST })
	@ResponseBody
	public void contributePagerData(@PathVariable String reportId, Model model, HttpServletRequest req, HttpServletResponse resp) {
			
			try {
				
				String offset = req.getParameter("offset");
				SystemContext.setPageOffset(Integer.valueOf(offset));
				SystemContext.setPageSize(Integer.valueOf(10));
				//根据reportId查询滚动加载的举报信息
				String reportId = req.getParameter("reportId");
				Pager<Problem> contributeProblem=problemService.problemListById(reportId);
				List<Problem> problemList = contributeProblem.getDatas();
				
				String json = "";
				//处理级联转json 数据死循环
				for (Problem problem : problemList) {
					List<ProblemAttachment> problemAttachmentList  = problem.getProblemAttachment();
					for(ProblemAttachment problemAttachment : problemAttachmentList){
						problemAttachment.setProblem(null);
					}
				}
				
				//json += JsonUtil.getInstance().obj2json(problem);
				json = JsonUtil.getInstance().obj2json(problemList);
				//总页数完了之后不进后台刷新
				System.out.println(contributeProblem.getTotal());
				SystemContext.removePageSize();
				resp.setCharacterEncoding("utf-8");
				resp.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}	
	
	
	
	
	
	*//**
	 *  查询所有案卷
	 * @param model
	 *//*
	@RequestMapping("/weixin/check")
	@AuthMethod
	public String listCheck(Model model) {
		//根据时间排序
		//SystemContext.setSort("p.reportTime");
		//SystemContext.setOrder("asc");
		//Pager<Problem>  problem=  problemService.findPageProblemAll();
		//model.addAttribute("datas", problem);
		model.addAttribute("datas", problemService.findPageProblemAll());
		return "wechat/list_check";
	}
	
	*//**
	 * 更改案卷状态
	 * @param id 根据id 更改
	 * @return
	 *//*
	@RequestMapping(value="/weixin/updateStatus/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String update(@PathVariable String id ){
		try {
			problemService.updateStatus(id);
			return "success";
		} catch (Exception e) {
			return "error";
		}
		
	}
	
	*//**
	 * 删除案卷
	 * 
	 *//*
	@RequestMapping(value="/weixin/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public String deleteProblem(@PathVariable String id){
		try {
			problemService.delete(id);
			return "success";
		} catch (Exception e) {
			return "error";
		}
		
	}
	
	
	*//**
	 * 展示指定案卷
	 * @param id
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value="/findProblem/{id}",method=RequestMethod.GET)
	public String show(@PathVariable String id,Model model){
		//model.addAttribute("problem", problemService.load(id));
		Problem problem = problemService.problemByProblemId(id);
		model.addAttribute("problem", problem);
		return "wechat/show";
	}
	
--------------------微信案卷添加----------------------------------------------
	
	*//**
	 * 重定向到微信
	 * @param model
	 * @param req
	 * @param resp
	 *//*
	@RequestMapping(value="/wechat/redir/add",method=RequestMethod.GET)
	public void redriectAdd(HttpServletRequest req,HttpServletResponse resp){
		try {
			String redirectUrl = "http://www.cdhncy.com/wechat/add";
			String scope = "snsapi_userinfo";
			Long state = new Date().getTime();
			String url = WeChatUtil.WeChatRedirect(redirectUrl, scope, state);
			System.out.println(url);
			resp.sendRedirect(url);
		} catch (IOException e) {
				System.out.println("地址转换错误");
		}
	}
	
	@RequestMapping(value="/wechat/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest req){
		// 用户同意授权后，能获取到code
		String code = req.getParameter("code");// 用户同意授权
		// 申请的测试号appid appsecret
		System.out.println("dopost");
		System.out.println(code);
		WeChatUtil wcUtil = new WeChatUtil();
		
		
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = wcUtil.getOauth2AccessToken(code);
			if (null == weixinOauth2Token) {
				//返回获取access_token错误页面
				return "error";
			}
			
			System.out.println(weixinOauth2Token.getAccessToken());
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			System.out.println(accessToken);
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			WeChatUser wcu = wcUtil.getSNSUserInfo(accessToken, openId);// 设置要传递的参数
			System.out.println(wcu.getNickname());
			model.addAttribute("wcu", wcu);
			model.addAttribute("appId", WeChatUtil.appId);
		}
		return "wechat/report";
	}
	
	*//**
	 * 案卷添加
	 * @param problemDto
	 * @param br
	 * @return
	 *//*
	@RequestMapping(value="/wechat/add",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest req,HttpServletResponse resp) {
		try {
			String problem = req.getParameter("problem");
			ProblemDto problemDto = (ProblemDto) JsonUtil.getInstance().json2obj(problem, ProblemDto.class);
			problemDto.setId(UUIDGenerator.getUUID());
			problemService.add(problemDto.getProblem(),problemDto.getAid());
			resp.getWriter().write(JsonUtil.getInstance().obj2json(problemDto));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	-------------------验证参数js-sdk-----------------------------------	
	*//**
	 * 获取微信js-sdk验证所需参数
	 *//*
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/wechat/sign",method=RequestMethod.POST)
	@ResponseBody
	public void sign(HttpServletRequest req,HttpServletResponse resp){
		try {
			String jsapi_ticket = WeChatUtil.getJSapiTicket();
			//String jsapi_ticket ="jsapi_ticket";
			
	        // 注意 URL 一定要动态获取，不能 hardcode
	        String url = req.getParameter("resUrl");
	        System.out.println(url);
	        Map<String, String> result = Sign.sign(jsapi_ticket, url);
	        for (Map.Entry entry : result.entrySet()) {
	            System.out.println(entry.getKey() + ", " + entry.getValue());
	        }
			resp.getWriter().write(JsonUtil.getInstance().obj2json(result));
		} catch (IOException e) {
			
			System.out.println("获取失败");
		}
	}
	
	
	//转发到"城管概况" 页面
	@RequestMapping("/wechat/overviews")
	public String redOverviews(){
		return "wechat/wechat_overviews";
	}
	
	//转发到"公告公示"页面
	@RequestMapping("/wechat/environment")
	public String redEnvironment(){
		return "wechat/wechat_environment_manage";
	}
		
	//转发到"党风廉政"页面
	@RequestMapping("/wechat/tactics")
	public String redTactics(){
		return "wechat/wechat_tactics";
	}
		
		
	//办事指南
	@RequestMapping("/wechat/guide")
	public String redGuide(){
		return "wechat/wechat_guide_list";
	}
		
		
		
	//城管风采
	@RequestMapping("/wechat/photos")
	public String redPhotos(){
		return "wechat/wechat_photos";
	}
		
		
	//政策法规
	@RequestMapping("/wechat/regulation")
	public String redRegulation(){
		return "wechat/wechat_regulation_list";
	}
		
		
	//工作动态
	@RequestMapping("/wechat/dynamic")
	public String redDynamic(){
		return "wechat/wechat_dynamic";
	}
	
	
	--------------------------------微信图片上传 及 删除------------------------------------------
	
	@RequestMapping(value="/wechat/upload",method=RequestMethod.POST)//返回的是json类型的值，而uploadify只能接受字符串
	public void upload(@RequestParam("uploadFile") CommonsMultipartFile[] uploadFile,HttpServletRequest req ,HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain;charset=utf-8");
		System.out.println();
		
		AjaxObj[] ao = new AjaxObj[uploadFile.length+1];
		for (int i = 0; i < uploadFile.length; i++) {
			CommonsMultipartFile commonsMultipartFile = uploadFile[i];
			commonsMultipartFile.getStorageDescription(); 
			commonsMultipartFile.getOriginalFilename();
			try {
				ProblemAttachment att = new ProblemAttachment();
				String ext = FilenameUtils.getExtension(commonsMultipartFile.getOriginalFilename());
				System.out.println(ext);
				att.setCreateTime(new Date());
				att.setId(UUIDGenerator.getUUID());
				problemAttachmentService.add(att, commonsMultipartFile.getInputStream(),ext);
				ao[i] = new AjaxObj(1,null,att);
			} catch (IOException e) {
				ao[i] = new AjaxObj(0,e.getMessage());
			}
		}
		
		resp.getWriter().write(JsonUtil.getInstance().obj2json(ao));
	}
	
	//微信图片删除
	@RequestMapping(value="/wechat/delete/{id}",method=RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable String id){
		try {
			problemAttachmentService.delete(id);
			System.out.println("删除成功");
		} catch (Exception e) {
			System.out.println("删除失败");
		}
		
	}
	
	
	
}*/