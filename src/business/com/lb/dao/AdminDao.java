package com.lb.dao;

import com.lb.bean.Ad;
import com.lb.utils.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AdminDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAdmin(String account, String password) {
        String sql = "select id,account,password from manager where account = ? and password= ?";
        return jdbcTemplate.queryForList(sql, account, password);
    }

    public int getCityCount() {
        String sql = "select count(1) from city";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getCityByPage(int pageIndex, int pageSize) {
        String sql = "select * from city order by state desc limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void addCity(String cityName, String serviceScope) {
        String sql = "insert into city (name,opentime,state,serviceScope) values (?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{cityName, DateUtil.cruTimeStr(), "开通", serviceScope});
    }

    public void changeCityState(String cityId, String state) {
        String sql;
        if ("停用".equals(state)) {
            sql = "update city set state = '停用', opentime='',stoptime = '" + DateUtil.cruTimeStr() + "' where id=" + cityId;
        } else {
            sql = "update city set state = '开通', opentime = '" + DateUtil.cruTimeStr() + "', stoptime='' where id=" + cityId;
        }
        jdbcTemplate.update(sql);
    }

    public int getAdCount() {
        String sql = "select count(*) from advertisement";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getAdByPage(int pageIndex, int pageSize) {
        String sql = "select * from advertisement order by state desc limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void changeAdState(String adId, String state) {
        String sql = " update advertisement set state = '" + state + "' where id = " + adId;
        jdbcTemplate.update(sql);
    }

    public void addAd(Ad ad) {
        String sql = "INSERT INTO advertisement (type, picName, url, BACKUP) VALUE (?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{ad.getType(), ad.getPicName(), ad.getUrl(), ad.getBackup()});
    }

    public List<Map<String, Object>> getAdById(String adId) {
        String sql = "select * from advertisement where id = " + adId;
        return jdbcTemplate.queryForList(sql);
    }

    public void updateAd(Ad ad) {
        String sql = "update advertisement set type=?,url=?,picName=?,backup=? where id=?";
        jdbcTemplate.update(sql, new Object[]{ad.getType(), ad.getUrl(), ad.getPicName(), ad.getBackup(), ad.getId()});
    }

    public void deleteAd(String adId) {
        String sql = "delete from advertisement where id = " + adId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> queryCityServiceScopeMobile(String cityName) {
        String sql = "select serviceScope from city where name = '" + cityName + "'";
        return jdbcTemplate.queryForList(sql);
    }

    public void updateCityServiceScope(String cityId, String serviceScope) {
        String sql = "update city set serviceScope =? where id = " + cityId;
        jdbcTemplate.update(sql, serviceScope);
    }
}
