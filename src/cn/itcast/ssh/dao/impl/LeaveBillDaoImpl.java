package cn.itcast.ssh.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.ssh.dao.ILeaveBillDao;
import cn.itcast.ssh.domain.LeaveBill;

public class LeaveBillDaoImpl extends HibernateDaoSupport implements ILeaveBillDao {

	@Override
	public List<LeaveBill> findLeaveBillList() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
