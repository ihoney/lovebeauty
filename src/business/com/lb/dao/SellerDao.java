package com.lb.dao;

import com.lb.bean.SellerValidateInfo;
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
public class SellerDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findSeller(String account, String password) {
        String sql = "select * from seller where account = ? and password= ?";
        return jdbcTemplate.queryForList(sql, new Object[]{account, password});
    }

    public void register(String account, String password, String regIp) {
        String sql = "insert into seller (regip,regtime,account,password) values (?,?,?,?) ";
        try {
            jdbcTemplate.update(sql, new Object[]{regIp, DateUtil.cruTimeStr(), account, password});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getSellerCount() {
        String sql = "select count(*) from seller";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getSellerByPage(int pageIndex, int pageSize) {
        String sql = "SELECT *  FROM  seller limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void checkSeller(String sellerId) {
        String sql = "update seller set checked ='是', checkedtime = '" + DateUtil.cruTimeStr() + "' where id = " + sellerId;
        jdbcTemplate.update(sql);
    }

    public void updateLoginTimeAndIp(String sellerId, String logIp) {
        String sql = "UPDATE seller SET loginip = ?, logintime = '" + DateUtil.cruTimeStr() + "'  where id = ? ";
        jdbcTemplate.update(sql, new Object[]{logIp, sellerId});
    }

    public List<Map<String, Object>> getSellerAuthInfo(String sellerId) {
        String sql = "SELECT * " +
                " FROM " +
                " seller_validate_info " +
                " WHERE " +
                " sellerId = " + sellerId;
        return jdbcTemplate.queryForList(sql);
    }

    public void addAuth(SellerValidateInfo svi) {
        String sql = "insert into seller_validate_info (sellerid,name,sex,birthday,email,identify,shopname,address,cityId,payaccount,telephone," +
                "servicescope,head_img,identify_img,alipay_public_key,alipay_private_key,alipay_pid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{svi.getSellerId(), svi.getName(), svi.getSex(), svi.getBirthday(), svi.getEmail(), svi.getIdentify(), svi.getShopName(), svi.getAddress(), svi.getCityId(),
                svi.getPayAccount(),
                svi.getTelephone(), svi.getServiceScope(), svi.getHeadImgStr(), svi.getIdentifyImgStr(), svi.getAlipayPublicKey(), svi.getAlipayPrivateKey(), svi.getAlipayPid()});
    }

    public void setAuth(int sellerId) {
        String sql = " update seller set authed = '是' where id = " + sellerId;
        jdbcTemplate.update(sql);
    }

    public void updateAuth(SellerValidateInfo svi) {
        String sql = "update seller_validate_info set name = ?,sex=?,birthday = ?,email = ?,identify = ?, shopname = ?, address = ? ,cityId = ?, payaccount = ?,telephone = ?, serviceScope = ?, " +
                "  alipay_public_key=?,alipay_private_key=?,alipay_pid=?,head_img=?,identify_img=? where id=?";

        jdbcTemplate.update(sql, new Object[]{svi.getName(), svi.getSex(), svi.getBirthday(), svi.getEmail(), svi.getIdentify(), svi.getShopName(), svi.getAddress(), svi.getCityId(), svi.getPayAccount(),
                svi.getTelephone(),
                svi.getServiceScope(), svi.getAlipayPublicKey(), svi.getAlipayPrivateKey(), svi.getAlipayPid(), svi.getHeadImgStr(), svi.getIdentifyImgStr(), svi.getId()});
    }

    public void deleteSeller(String sellerId) {
        String sql = "delete from seller where id = " + sellerId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> getValidateInfo(String sellerId) {
        String sql = "select * from seller_validate_info where sellerid = " + sellerId;
        return jdbcTemplate.queryForList(sql);
    }

    public void changePwd(String sellerId, String newPassword) {
        String sql = "update seller set password = ? where id = ?";
        jdbcTemplate.update(sql, new Object[]{newPassword, sellerId});
    }

    public void forbiddenSeller(String sellerId) {
        String sql = "update seller set forbidden = '是' where id = '" + sellerId + "'";
        jdbcTemplate.update(sql);
    }

    public void reUseSeller(String sellerId) {
        String sql = "update seller set forbidden = '否' where id = '" + sellerId + "'";
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> getSingleSellerById(String sellerId) {
        String sql = "SELECT " +
                " s.account, " +
                " s.`password`, " +
                " sv.`name`, " +
                " sv.identify, " +
                " sv.shopname, " +
                " sv.address, " +
                " sv.payaccount, " +
                " sv.servicescope, " +
                " sv.telephone, " +
                " sv.sex, " +
                " sv.birthday, " +
                " sv.email, " +
                " sv.head_img, " +
                " sv.identify_img, " +
                " sv.alipay_public_key, " +
                " sv.alipay_private_key, " +
                " sv.alipay_pid " +
                "FROM " +
                " seller s " +
                "LEFT JOIN seller_validate_info sv ON sv.sellerid = s.id and s.id = " + sellerId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryAllSellers() {
        String sql = "SELECT svi.cityId, s.id, svi.shopname as shopName FROM seller s, seller_validate_info svi WHERE s.forbidden = '否' AND svi.sellerid = s.id";
        return jdbcTemplate.queryForList(sql);
    }
}
