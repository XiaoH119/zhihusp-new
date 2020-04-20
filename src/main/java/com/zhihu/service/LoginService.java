package com.zhihu.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihu.common.bean.Integral;
import com.zhihu.common.bean.LoginBean;
import com.zhihu.common.bean.UserInfo;
import com.zhihu.global.bean.Response;
import com.zhihu.mapper.LoginMapper;

@Service
public class LoginService {

	static Logger logger = LoggerFactory.getLogger(LoginService.class);
	@Autowired
	private LoginMapper loginMapper;

	public Response login(LoginBean lb) throws Exception {
		try {
			LoginBean lbdb = loginMapper.getUser(lb);
			Response res = new Response();
			logger.info("lbdb is null ===" + (lbdb == null));
			if (lbdb == null) {
				res.setResultError("未查询到登录用户！");
				return res;
			} else {
				int i = loginMapper.checkUser(lb);
				if (i == 0) {
					res.setResultError("密码不正确，请重新输入!");
					return res;
				}
			}
			res.setData(lbdb);
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 注册
	 * 
	 * @param lb
	 * @return
	 */
	public Response reg(LoginBean lb) throws Exception {
		try {
			int i = loginMapper.checkUser(lb);
			LoginBean lbdb = loginMapper.getUser(lb);
			Response res = new Response();
			if (i > 0 || lbdb != null) {
				res.setResultError("该手机号已经注册过，请直接登录或更换手机号！");
				return res;
			} else {
				// 注册赠送50积分
				lb.setIntegral("50");
				loginMapper.regUser(lb);
			}
			res.setResultRight("注册成功！");
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * 修改密码
	 * 
	 * @param lb
	 * @return
	 */
	public Response modifyPassword(LoginBean lb) throws Exception {
		Response res = new Response();
		try {
			loginMapper.modifyPassword(lb);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("密码修改成功");
		return res;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfo getUserInfo(String username, String password) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setPassword(password);
		return loginMapper.getUserInfo(userInfo);
	}

	/**
	 * 查询用户信息
	 * 
	 * @param userinfo
	 * @return
	 * @throws Exception
	 */
	public Response getUserInfo(UserInfo userinfo) throws Exception {
		Response res = new Response();
		try {
			UserInfo userInfo = loginMapper.getUserInfo(userinfo);
			if (userInfo == null) {
				res.setResultError("未查询到用户信息！");
				return res;
			}
			res.setData(userInfo);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	/**
	 * 更新个人主页
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response updateUserInfoExt(UserInfo userInfo) throws Exception {
		Response res = new Response();
		try {

			UserInfo userext = loginMapper.getUserInfoExt(userInfo);
			if (userext == null) {
				String userid = loginMapper.selectUserInfoExtByPTID(userInfo.getPtid());
				if (userid != null) {
					res.setResultError("您的主页链接已经在其他用户使用过啦，请更换其他主页链接");
					return res;
				}
				res.setResultRight("保存成功，请尽快完成账号测试以方便您接单~");
				loginMapper.insertUserInfoExt(userInfo);
			} else {
				// 判断新的ptid与原ptid是否一致，如果不一致，说明平台主页改了，需要判断新ptid在系统是中否存在，如果存在，则返回错误信息，
				// 如果不存在，则更新原护展信息
				res.setResultRight("信息同步完成");
				if (!userInfo.getPtid().equals(userext.getPtid())) {
					String userid = loginMapper.selectUserInfoExtByPTID(userInfo.getPtid());
					if (userid != null) {
						if (userid.equals(String.valueOf(userInfo.getUserid()))) {
							res.setResultError("您的主页链接已经在其他用户使用过啦，请更换其他主页链接");
							return res;
						}
					}
					res.setResultRight("您的主页链接已更换，请重新做账号测试然后再接单哦");
					loginMapper.deleteTakeUser(userInfo);
				}
				loginMapper.updateUserInfoExt(userInfo);
			}

		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Response updateUserInfo(UserInfo userInfo) throws Exception {
		Response res = new Response();
		try {
			loginMapper.updateUserInfo(userInfo);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("更新完成");
		return res;
	}

	/**
	 * 获取用户扩展信息
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Response getUserInfoExt(UserInfo userInfo) throws Exception {
		Response res = new Response();
		try {
			UserInfo userext = loginMapper.getUserInfoExt(userInfo);
			res.setData(userext);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	public LoginBean findUserById(String userid) throws Exception {
		return loginMapper.findUserById(userid);
	}

	/**
	 * 积分兑换现金
	 * 
	 * @param integral
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response exchange(Integral integral) throws Exception {
		Response res = new Response();
		try {
			// 添加兑换记录，更新积分信息
			loginMapper.insertIntegralLog(integral);
//			String s = null;
//			if (s.equals("1")) {
//				s = "";
//			}
//			UserInfo userInfo = new UserInfo();
//			userInfo.setUserid(Integer.parseInt(integral.getUserid()));
			integral.setIntegral(integral.getIntegral().multiply(new BigDecimal(-1)));
			loginMapper.updateIntegral(integral);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("更新完成");
		return res;
	}

	/**
	 * 开通放单权限
	 * 
	 * @param integral
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response grantExtPermit(UserInfo userinfo) throws Exception {
		Response res = new Response();
		try {
			// 判断用户是否为管理员
			UserInfo user = new UserInfo();
			user.setUserid(userinfo.getUserid());
			UserInfo userdb = loginMapper.getUserInfo(user);
			if (userdb == null) {
				res.setResultError("不好意思，小智开小差了，请您重新登录一下吧~");
				return res;
			}
			logger.info("userdb.getIsadmin()====" + userdb.getIsadmin());
			if (!"1".equals(userdb.getIsadmin())) {
				res.setResultError("您没有权限做此操作哦");
				return res;
			}
			// 查询需要加权限的用户是否存在
			LoginBean lb = new LoginBean();
			lb.setUsername(userinfo.getUsername());
			LoginBean lbdb = loginMapper.getUser(lb);
			if (lbdb == null) {
				res.setResultError("该手机号未注册哦");
				return res;
			}
			String userid = lbdb.getUserid();

			loginMapper.deleteExtUser(userid);
			loginMapper.insertExtUser(userid);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("授权完成");
		return res;
	}

	/**
	 * 开通接反对单权限
	 * 
	 * @param integral
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response grantOpposePermit(UserInfo userinfo) throws Exception {
		Response res = new Response();
		try {
			// 判断用户是否为管理员
			UserInfo user = new UserInfo();
			user.setUserid(userinfo.getUserid());
			UserInfo userdb = loginMapper.getUserInfo(user);
			if (userdb == null) {
				res.setResultError("不好意思，小智开小差了，请您重新登录一下吧~");
				return res;
			}
			if (!"1".equals(userdb.getIsadmin())) {
				res.setResultError("您没有权限做此操作哦");
				return res;
			}
			// 查询需要加权限的用户是否存在
			LoginBean lb = new LoginBean();
			lb.setUsername(userinfo.getUsername());
			LoginBean lbdb = loginMapper.getUser(lb);
			if (lbdb == null) {
				res.setResultError("该手机号未注册哦");
				return res;
			}
			String userid = lbdb.getUserid();

			loginMapper.deleteOpposeUser(userid);
			loginMapper.insertOpposeUser(userid);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("授权完成");
		return res;
	}

	/**
	 * 更新积分
	 * 
	 * @param integral
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response updateIntegral(UserInfo userinfo) throws Exception {
		Response res = new Response();
		try {
			// 判断用户是否为管理员
			UserInfo user = new UserInfo();
			user.setUserid(userinfo.getUserid());
			UserInfo userdb = loginMapper.getUserInfo(user);
			if (userdb == null) {
				res.setResultError("不好意思，小智开小差了，请您重新登录一下吧~");
				return res;
			}
			if (!"1".equals(userdb.getIsadmin())) {
				res.setResultError("您没有权限做此操作哦");
				return res;
			}
			// 查询需要充值的用户是否存在
			LoginBean lb = new LoginBean();
			lb.setUsername(userinfo.getUsername());
			LoginBean lbdb = loginMapper.getUser(lb);
			if (lbdb == null) {
				res.setResultError("该手机号未注册哦");
				return res;
			}
			String userid = lbdb.getUserid();
			Integral integral = new Integral();
			integral.setUserid(userid);
			integral.setIntegral(userinfo.getIntegral());
			loginMapper.updateIntegral(integral);
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("充值完成，请确认充值后金额是否正确！");
		return res;
	}

	/**
	 * 查询积分
	 * 
	 * @param userinfo
	 * @return
	 * @throws Exception
	 */
	public Response getIntegral(UserInfo userinfo) throws Exception {
		Response res = new Response();
		try {
			// 查询需要充值的用户是否存在
			LoginBean lb = new LoginBean();
			lb.setUsername(userinfo.getUsername());
			LoginBean lbdb = loginMapper.getUser(lb);
			if (lbdb == null) {
				res.setResultError("该手机号未注册哦");
				return res;
			}
//			userinfo.setIntegral(new BigDecimal(lbdb.getIntegral()));
			res.setData(lbdb.getIntegral());
		} catch (Exception e) {
			throw new Exception(e);
		}
//		res.setResultRight("充值完成，请确认充值后金额是否正确！");
		return res;
	}

	/**
	 * 开通接反对单权限
	 * 
	 * @param integral
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public Response grantTakePermit(UserInfo userinfo) throws Exception {
		Response res = new Response();
		try {
			// 判断用户是否为管理员
			UserInfo user = new UserInfo();
			user.setUserid(userinfo.getUserid());
			UserInfo userdb = loginMapper.getUserInfo(user);
			if (userdb == null) {
				res.setResultError("不好意思，小智开小差了，请您重新登录一下吧~");
				return res;
			}
			if (!"1".equals(userdb.getIsadmin())) {
				res.setResultError("您没有权限做此操作哦");
				return res;
			}
			// 查询需要加权限的用户是否存在
			LoginBean lb = new LoginBean();
			lb.setUsername(userinfo.getUsername());
			LoginBean lbdb = loginMapper.getUser(lb);
			if (lbdb == null) {
				res.setResultError("该手机号未注册哦");
				return res;
			}
			String userid = lbdb.getUserid();
			UserInfo takeuser = new UserInfo();
			takeuser.setUserid(Integer.parseInt(userid));
			int cnt = loginMapper.selectTakeUser(takeuser);
			if (cnt > 0) {
				res.setResultRight("授权完成");
				return res;
			} else {
				loginMapper.insertTakeUser(takeuser);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		res.setResultRight("授权完成");
		return res;
	}
}
